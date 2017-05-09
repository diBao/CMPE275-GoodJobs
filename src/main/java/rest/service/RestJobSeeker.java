package rest.service;

import org.json.JSONException;

import rest.repo.*;
import rest.module.*;



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
		
		try{
			repo_jobseeker.save(jobseeker);
			return jobseeker.getJSON().toString();
		}
		catch(Exception e){
			//TODO error message
			return "create JobSeeker failed";
		}

	}
	
	public String read_jobseeker(Long id){
		
		JobSeeker jobseeker = repo_jobseeker.findById(id);
		if(jobseeker != null){
			try {
				return jobseeker.getJSON().toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			//TODO error message 
			return "No result found";
		}
		return "No result found";
	}
	
	public String update_jobseeker(){
		return "";
	}
	
	public String delete_jobseeker(){
		return "";
	}
	
	public String mark_interest(Long id, String mark){
		return null;
		
	}
	
	public String unmark_interest(Long id, String unmark) {
		return null;
	}
	
	public String apply_position(Long id, String position){
		
		
		
		return "";
	}
}
