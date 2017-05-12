package rest.service;

import rest.repo.ApplicationRepo;
import rest.repo.CompanyRepo;
import rest.repo.JobSeekerRepo;
import rest.repo.PositionRepo;

import java.util.HashSet;
import java.util.Set;

import rest.module.Application;
import rest.module.Position;
public class RestPosition {
	// When a job is updated, all the current applicants 
	//(applications in terminal states are not considered) are notified about the change.

	private JobSeekerRepo repo_jobseeker;
	private CompanyRepo repo_company;
	private ApplicationRepo repo_application;
	private PositionRepo repo_position;
	
	public RestPosition(JobSeekerRepo repo_jobseeker, CompanyRepo repo_company, ApplicationRepo repo_application, PositionRepo repo_position){
		this.repo_jobseeker = repo_jobseeker;
		this.repo_company = repo_company;
		this.repo_application = repo_application;
		this.repo_position = repo_position;
	} 	
	 Position createPostion(String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary) {
		// with initial status + new generate pID
		Position position = new Position( pTitle, pDesciption, responsibility, officeLocation, salary);
		
		return repo_position.save(position);
	}
	public Position getPosition(Long pID){
		return repo_position.findOne(pID);
	}
	public Position updatePosition(Long pID,String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary,String status){
		//Notification all the seeker and Same return with create.
		Position position = repo_position.findOne(pID);
		if(position!=null){
			if(pTitle!=null){
				position.setTitle(pTitle);
			}
			if(pDesciption!=null){
				position.setDescription(pDesciption);
			}
			if(officeLocation!=null){
				position.setOfficeLocation(officeLocation);
			}
			if(salary!=null){
				position.setSalary(salary);
			}
			if(status!=null){
				position.setStatus(status);
				if(position.getStatus().equals("Open")==false){
					//When a job is filled or cancelled, 
					//all related applications in non terminal state get cancelled
					Set<Long> cancelledCandidates = new HashSet<Long>();
					for(Application application: repo_application.findAll()){
						if(application.getPosition().equals(position)){
							cancelledCandidates.add(application.getaID());
						}
					}
					//including notify job seekers in application list
					RestApplication restapplication = new RestApplication(this.repo_jobseeker, this.repo_company, this.repo_application, this.repo_position);
					restapplication.updateApplications(cancelledCandidates);
					//TODO
					//If a position gets filled or cancelled by the company, 
					//it would be removed from applicant’s interesting list automatically
				}else{
					//no influence to application, just notify job seekers
					notificationSeeker();//parameter
				}
				
			}
			repo_position.save(position);
		}
		return position;
	}
	public boolean deletePosition(Long pID){ 
		Position position = repo_position.findOne(pID);
		if(position!=null){
			try{
				repo_position.delete(position);
			}catch(Exception e){
				// fail delete, MySql throw exception
				return false;
			}
			// success delete: true
			notificationSeeker();//including applications and interests
			return true;
		}
	
		//not find pID or fail delete : false
		return false;
	}
	public void notificationSeeker(){
		//listen function, listen to the changes of itself and notify its seekerSet
		//TODO
	}
	public Set<Position> searchPositions(String mixTexts){
		/*5. Job seekers can search for open job positions
		Search by text the user types in, which can be a job title, 
		a company name, a skill, a mix of them, or basically, any arbitrary piece of text. 
		A word in the text can be matched against any part of a job posting.
		
		Search by filters
		Company name (allow multiple)
		Location (city names, allow multiple)
		Salary range (can be a single value, an open range, or a close range)
		You can add more filters if you prefer. 
		 */
		Set<Position> searchResult = new HashSet<Position>();
		
		return searchResult;
	}

}
