package rest.service;

import org.json.JSONException;
import org.json.JSONObject;

import rest.repo.*;
import rest.module.*;

import java.util.Set;
import java.util.HashSet;


public class RestJobSeeker {
	
	private JobSeekerRepo repo_jobseeker;
	private CompanyRepo repo_company;
	private ApplicationRepo repo_application;
	private PositionRepo repo_position;
	    
	public RestJobSeeker(JobSeekerRepo repo_jobseeker, CompanyRepo repo_company, ApplicationRepo repo_application, PositionRepo repo_position){
		this.repo_jobseeker = repo_jobseeker;
		this.repo_company = repo_company;
		this.repo_application = repo_application;
		this.repo_position = repo_position;
	} 	
	
	public String create_jobseeker(String firstName, String lastName, String picture, String selfIntroduction, 
			String workExperience, String education, String skills, String email, String password){
		JobSeeker jobseeker = new JobSeeker(firstName, lastName, picture, selfIntroduction, workExperience, education, skills, email, password);
		System.out.println(jobseeker.getsID());
		try{
			repo_jobseeker.save(jobseeker);
			return jobseeker.getJSON();
		}
		catch(Exception e){
			//TODO error message
			return "create JobSeeker failed";
		}

	}
	
	public String read_jobseeker(String email){
		
		JobSeeker jobseeker = repo_jobseeker.findByemail(email);
		if(jobseeker != null){
			return jobseeker.getJSON();
		}
		else{
			//TODO error message 
			return "No result found";
		}
	}
	
	public String update_jobseeker(String oldEmail, String firstName, String lastName, String picture, 
			String selfIntroduction, String workExperience, String education, String skills, String email, String password){
		
		JobSeeker jobseeker = repo_jobseeker.findByemail(oldEmail);
		if(jobseeker == null){
			return "No Jobseeker found";
		}
		
		if(firstName != null){
			jobseeker.setFirstName(firstName);
		}
		if(lastName != null){
			jobseeker.setLastName(lastName);
		}
		if(picture != null){
			jobseeker.setPicture(picture);
		}
		if(selfIntroduction != null){
			jobseeker.setIntroduction(selfIntroduction);
		}
		if(workExperience != null){
			jobseeker.setExperience(workExperience);
		}
		if(education != null){
			jobseeker.setEducation(education);
		}
		if(skills != null){
			jobseeker.setSkills(skills);
		}
		if(email != null){
			jobseeker.setEmail(email);
		}
		if(password != null){
			jobseeker.setPassword(password);
		}
		
		try {
        	repo_jobseeker.save(jobseeker);
        } catch (Exception e) {
        	return "Jobseeker update failed";
        }
		
		return jobseeker.getJSON();		
	}
	
	public String delete_jobseeker(String email){
		
		JobSeeker jobseeker = repo_jobseeker.findByemail(email);
		if(jobseeker == null){
			return "No result found";
		}
		
		for(Position p : jobseeker.getInterestSet()){
			p.getApplicationSet().remove(jobseeker);
		}
		
		for(Application a : jobseeker.getApplicationSet()){
			
		}
		
		return "";
	}
	
	public String mark_interest(String email, Long mark){
		JobSeeker jobseeker = repo_jobseeker.findByemail(email);
		Position position = repo_position.findBypID(mark);
		if(jobseeker == null || position == null){
			return "Input error";
		}
		if(jobseeker != null && position != null){
			Set<Position> set_p = jobseeker.getInterestSet();
			set_p.add(position);
			jobseeker.setInterestSet(set_p);
			
			Set<JobSeeker> set_j = position.getInterestSet();
			set_j.add(jobseeker);
			position.setInterestSet(set_j);

			repo_jobseeker.save(jobseeker);
			repo_position.save(position);
			return jobseeker.getJSON();
	
		}
		else{
			return "Marking failed";
		}
		
	}
	
	public String unmark_interest(String email, Long unmark) {
		JobSeeker jobseeker = repo_jobseeker.findByemail(email);
		Position position = repo_position.findBypID(unmark);
		if(jobseeker == null || position == null){
			return "Input error";
		}
		if(jobseeker != null && position != null){
			Set<Position> set_p = jobseeker.getInterestSet();
			set_p.remove(position);
			jobseeker.setInterestSet(set_p);
			
			Set<JobSeeker> set_j = position.getInterestSet();
			set_j.remove(jobseeker);
			position.setInterestSet(set_j);

			repo_jobseeker.save(jobseeker);
			repo_position.save(position);
			return jobseeker.getJSON();

		}
		else{
			return "Unmarking failed";
		}
	}
	
	//Done by george 2
	public String retrieve_all_applications(String email){
		JobSeeker jobseeker = repo_jobseeker.findByemail(email);
		Set<Application> applications = jobseeker.getApplicationSet();
		return getApplicationsJSON("applications", applications);
	}
	public String getApplicationsJSON(String header, Set<Application> applications){
    	try{
			JSONObject result = new JSONObject();
			JSONObject[] jsonArray = new JSONObject[applications.size()];
			int i = 0;
			for(Application application: applications){
				JSONObject applicationJson = new JSONObject();
				applicationJson.put("aid", application.getaID());
				applicationJson.put("email", application.getEmail());
				applicationJson.put("firstName", application.getFirstName());
				applicationJson.put("lastName", application.getLastName());
				applicationJson.put("resumeUrl", application.getResumeUrl());
				applicationJson.put("status", application.getStatus());
				//result.put("status", application.getStatus());
				jsonArray[i++] = applicationJson;
			}
			result.put(header, jsonArray);		
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}
    }
	
//	//DONE in the rest service controller by george 1
//	public String retrieve_all_interests(String email){
//		JobSeeker jobseeker = repo_jobseeker.findByemail(email);
//		Set<Position> positions = jobseeker.getInterestSet();
//		return getgetPositionsJSON("interestPositions");
//	}
//	
//	public String getPositionsJSON(String header, Set<Position> positions){
//    	try{
//			JSONObject result = new JSONObject();
//			JSONObject[] jsonArray = new JSONObject[positions.size()];
//			int i = 0;
//			for(Position position:positions){
//				JSONObject positionJson = new JSONObject();
//				positionJson.put("pid", position.getpID());
//				positionJson.put("title", position.getTitle());
//				positionJson.put("description", position.getDescription());
//				positionJson.put("responsibility", position.getResponsibility());
//				positionJson.put("office_location", position.getOfficeLocation());
//				positionJson.put("salary", position.getSalary());
//				result.put("status", position.getStatus());
//				jsonArray[i++] = positionJson;
//			}
//			result.put(header, jsonArray);		
//			return result.toString();
//		}
//		catch(JSONException e){
//			return e.toString();
//		}
//    }
}
