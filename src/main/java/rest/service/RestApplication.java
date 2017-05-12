package rest.service;
import java.util.HashSet;
import java.util.Set;

import rest. module.*;
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
		
		notificationSeeker(jobSeeker, application.getStatus());
		
		return repo_application.save(application);// with new generate aID.
	}
	
	 private boolean generateAuthorization(JobSeeker jobSeeker, Position position) {
		// TODO Auto-generated method stub
		if(jobSeeker.getApplicationSet().size() > 5){
			return false;
		}
		Set<String> nonTerminalStates = new HashSet<String>();
		//"pending", "Offered"
		nonTerminalStates.add("pending");
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
	 void updateApplications(Set<Long> aIDs){
		//status==cancel
		//seeker cancel applications,Set<Application>  user can cancel one or more applications
		for(Long aID : aIDs){
			Application application = repo_application.findOne(aID);
			application.setStatus("Cancelled");
			repo_application.save(application);
			//TODO email update the application's jobseeker
			notificationSeeker(application.getJobSeeker(), "Cancelled");
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
	public void notificationSeeker(JobSeeker jobSeeker, String status){
		//listen function, listen to the changes of itself and notify its seekerSet
		//TODO
	}

}
