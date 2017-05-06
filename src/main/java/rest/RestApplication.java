package rest;
import java.util.HashSet;
import java.util.Set;

import module.*;
import repo.ApplicationRepo;

public  class RestApplication {
	//For any status change of an application, the job seeker receives an email update as well
	static private ApplicationRepo applicationRepo;
	static Application createApplication(JobSeeker jobSeeker,String sEmail,String sFirstName,String sLastName, Position position, String resumeURL){
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
		application = new Application();
		application.setJobSeeker(jobSeeker);
		application.setEmail(sEmail);
		application.setFirstName(sFirstName);
		application.setLastName(sLastName);
		application.setPosition(position);
		application.setStatus("pending");//initial status(pending)
		
		notificationSeeker(jobSeeker, application.getStatus());
		
		application.setResumeUrl(resumeURL);
		return applicationRepo.save(application);// with new generate aID.
	}
	
	static private boolean generateAuthorization(JobSeeker jobSeeker, Position position) {
		// TODO Auto-generated method stub
		if(jobSeeker.getApplicationSet().size() > 5){
			return false;
		}
		Set<String> nonTerminalStates = new HashSet<String>();
		//"pending", "Offered"
		nonTerminalStates.add("pending");
		nonTerminalStates.add("Offered");
		for(Application application: applicationRepo.findAll()){
			//find application with same jobSeeker & position
			if(application.getJobSeeker().equals(jobSeeker)&& application.getPosition().equals(position)){
				if(nonTerminalStates.contains(application.getStatus())){
					return false;
				}
			}
		}
		//all previous same application exams are passed
		return true;
	}

	static Application getApplication(Long aID){
		Application application = applicationRepo.findOne(aID);
		return application;
	}
	static Application updateApplication(Long aID, String newStatus){
		//to change application status
		Application application = applicationRepo.findOne(aID);
		if(newStatus.equals("Cancelled")){
			if(application.getStatus().equals("OfferAccepted")){
				//12.ii
				System.out.println("don't allow change the status for a offer accepted application");
				return application;
			}
		}
		application.setStatus(newStatus);
		//email update
		notificationSeeker(application.getJobSeeker(), newStatus);
		return application;
		
	}
	static void updateApplications(Set<Long> aIDs){
		//status==cancel
		//seeker cancel applications,Set<Application>  user can cancel one or more applications
		for(Long aID : aIDs){
			Application application = applicationRepo.findOne(aID);
			application.setStatus("Cancelled");
			applicationRepo.save(application);
			//TODO email update the application's jobseeker
			notificationSeeker(application.getJobSeeker(), "Cancelled");
		}
		//cancel more than one application, no returns
	}
	static Boolean deleteApplication(Long aID){
		Application application = applicationRepo.findOne(aID);
		if(application!=null){
			try{
				applicationRepo.delete(application);
			}catch(Exception e){
				//fail delete
				return false;
			}
			return true;
		}
		return false;
	}
	static void notificationSeeker(JobSeeker jobSeeker, String status){
		//listen function, listen to the changes of itself and notify its seekerSet
		//TODO
	}

}
