package rest.service;

import rest.repo.ApplicationRepo;
import rest.repo.CompanyRepo;
import rest.repo.JobSeekerRepo;
import rest.repo.PositionRepo;

import rest.module.*;

public class RestCompany {
	private JobSeekerRepo repo_jobseeker;
	private CompanyRepo repo_company;
	private ApplicationRepo repo_application;
	private PositionRepo repo_position;
	
	public RestCompany(JobSeekerRepo repo_jobseeker, CompanyRepo repo_company, ApplicationRepo repo_application, PositionRepo repo_position){
		this.repo_jobseeker = repo_jobseeker;
		this.repo_company = repo_company;
		this.repo_application = repo_application;
		this.repo_position = repo_position;
	} 	
	
	public String create_company(){
		
	}
	
	public String read_company(){
		
	}
	
	public String update_company(){
		
	}
	
	public delete_company(){
		
	}
	
	
	
	
}
