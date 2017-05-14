package rest.service;

import org.json.JSONException;

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
	
	public String retrieve_all_applications(String email){
		
		
		return "";
	}
	public String retrieve_all_positions(String email){
		
		return "";
	}
}
