package rest.service;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import rest.module.*;
import rest.repo.ApplicationRepo;
import rest.repo.CompanyRepo;
import rest.repo.JobSeekerRepo;
import rest.repo.PositionRepo;

public  class RestApplication {
	//For any status change of an application, the job seeker receives an email update as well
	private JobSeekerRepo repo_jobseeker;
	private CompanyRepo repo_company;
	private ApplicationRepo repo_application;
	private PositionRepo repo_position;
	
	public RestApplication(JobSeekerRepo repo_jobseeker, CompanyRepo repo_company, ApplicationRepo repo_application, PositionRepo repo_position){
		this.repo_jobseeker = repo_jobseeker;
		this.repo_company = repo_company;
		this.repo_application = repo_application;
		this.repo_position = repo_position;
	} 	
	Application createApplication(JobSeeker jobSeeker,String sEmail,String sFirstName,String sLastName, Position position, String resumeURL){
		/*para: 
		sID:   job seeker id
		sEmail: email same with sID
		sFirstName: first name same with sID
		sLastName: first name same with sID
		pID:  job position id
		*/
		Application application = null;
		/* judgement of application: 
		1.A user cannot have more than 5 pending applications. 
		2.A user cannot apply for the same position again if the previous application is not in a terminal state
		*/
		if(generateAuthorization(jobSeeker, position)==false){
			//authorization result for create new application is: not allowed
			return application;
		}
		
		application = new Application(jobSeeker, sEmail, sFirstName, sLastName, position, resumeURL);
		position.getApplicationSet().add(application);
		jobSeeker.getApplicationSet().add(application);
		String[] jobseekerEmail = new String[1];
		jobseekerEmail[0] = jobSeeker.getEmail();
		Company company = application.getPosition().getCompany();
		notificationSeeker(company.getEmail(),company.getPassword(), jobseekerEmail, application.getStatus());
		return repo_application.save(application);// with new generate aID.
	}
	
	 private boolean generateAuthorization(JobSeeker jobSeeker, Position position) {
		// TODO Auto-generated method stub
		 int pendingApplicationNum = 0;
		for(Application application:jobSeeker.getApplicationSet() ){
			if(application.getStatus().equals("Pending")){
				pendingApplicationNum++;
			}
		}
		if(pendingApplicationNum>=5){
			return false;
		}
//		if(jobSeeker.getApplicationSet().size() >= 5){
//			return false;
//		}
		Set<String> nonTerminalStates = new HashSet<String>();
		//"pending", "Offered"
		nonTerminalStates.add("Pending");
		nonTerminalStates.add("Offered");
		/*original find version
		 * for(Application application: repo_application.findAll()){
			//find application with same jobSeeker & position
			if(application.getJobSeeker().equals(jobSeeker)&& application.getPosition().equals(position)){
				if(nonTerminalStates.contains(application.getStatus())){
					return false;
				}
			}
		}*/
		//find version two
		for(Application application:repo_application.findApplicationByJobSeekerAndPosition(jobSeeker, position)){
			if(nonTerminalStates.contains(application.getStatus())){
				return false;
			}
		}
		//all previous same application exams are passed
		return true;
	}

	 Application getApplication(Long aID){
		Application application = repo_application.findOne(aID);
		return application;
	}
	 Application updateApplication(Long aID, String newStatus){
		//to change application status
		Application application = repo_application.findOne(aID);
		//System.out.println("222222222222222222222222222222222");
		if(newStatus.equals("Cancelled")){
			//System.out.println(newStatus+"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"+application.getStatus());
			if(application.getStatus().equals("OfferAccepted")){
				//12.ii
				//System.out.println("don't allow change the status for a offer accepted application");
				return null;
			}
		}
		application.setStatus(newStatus);
		repo_application.save(application);
		//email update
		String[] jobseekerEmail = new String[1];
		jobseekerEmail[1] = application.getJobSeeker().getEmail();
		Company company = application.getPosition().getCompany();
		notificationSeeker(company.getEmail(),company.getPassword(), jobseekerEmail, application.getStatus());
		return application;
		
	}
	 void updateApplications(Set<Long> aIDs){
		//status==cancel
		//seeker cancel applications,Set<Application>  user can cancel one or more applications
		for(Long aID : aIDs){
			updateApplication(aID, "Cancelled");
//			Application application = repo_application.findOne(aID);
//			application.setStatus("Cancelled");
//			repo_application.save(application);
//			//TODO email update the application's jobseeker
//			String[] jobseekerEmail = new String[1];
//			jobseekerEmail[1] = application.getJobSeeker().getEmail();
//			Company company = application.getPosition().getCompany();
//			notificationSeeker(company.getEmail(),company.getPassword(), jobseekerEmail,"Cancelled");

			//notificationSeeker(application.getJobSeeker(), "Cancelled");
		}
		//cancel more than one application, no returns
	}
	public Boolean deleteApplication(Long aID){
		Application application = repo_application.findOne(aID);
		if(application!=null){
			try{
				repo_application.delete(application);
			}catch(Exception e){
				//fail delete
				return false;
			}
			return true;
		}
		return false;
	}
	public void notificationSeeker(String from,String password, String[] to, String status){
		//listen function, listen to the changes of itself and notify its seekerSet
		//TODO
        String subject = "GoodJobs notification";
        String body ="Your application changes to"+ status;
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
//            InternetAddress toAddress = new InternetAddress(to);     
//            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
	}


}
