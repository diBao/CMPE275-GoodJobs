package rest.service;
import java.util.Set;

import rest.module.*;
import rest.repo.ApplicationRepo;

public class RestApplication {
	private ApplicationRepo applicationRepo;
	Application createApplication(JobSeeker jobSeeker,String sEmail,String sFirstName,String sLastName, Position position, String resumeURL){
		/*para: 
		sID:   job seeker id
		sEmail: email same with sID
		sFirstName: first name same with sID
		sLastName: first name same with sID
		pID:  job position id
		*/
		//judgement of application: 1.A user cannot have more than 5 pending applications. 2.A user cannot apply for the same position again if the previous application is not in a terminal state
		Application application = new Application();
		return applicationRepo.save(application);// with initial status(pending) + new generate aID.
	}
	Application getApplication(Long aID){
		
		Application application = applicationRepo.findOne(aID);
		return application;
	}
	Application updateApplication(Long aID, String status){
	//to change application status
		Application application = applicationRepo.findOne(aID);
		application.setStatus(status);
		return application;
	//email update
	}
	void updateApplications(Set<Long> aIDs){
		//status==cancel
		//seeker cancel applications,Set<Application>  user can cancel one or more applications
		for(Long aID : aIDs){
			Application application = applicationRepo.findOne(aID);
			application.setStatus("Cancelled");
			applicationRepo.save(application);
			//TODO email update the application's jobseeker
		}
		
	}
	Boolean deleteApplication(Long aID){
		Application application = applicationRepo.findOne(aID);
		if(application!=null){
			applicationRepo.delete(application);
			return true;
		}
		return false;
	}


}
