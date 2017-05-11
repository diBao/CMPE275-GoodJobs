package rest.service;

import org.json.JSONException;

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
	
	public String create_company(String name, String website, String logoImageUrl, 
			String address, String email, String description, String password){
		Company company = new Company(name, website, logoImageUrl, address, email, description, password);
		try{
			repo_company.save(company);
			return company.getJSON();
		}
		catch(Exception e){
			//TODO error message
			return "create Company failed";
		}
	}
	
	public String read_company(Long id){
		Company company = repo_company.findById(id);
		if(company != null){
			return company.getJSON();
		}
		else{
			//TODO error message 
			return "No result found";
		}		
	}
	
	public String update_company(Long id, String name, String website, String logoImageUrl, String address, String email, String description, String password){
		
		return "";
	}
	
	public String search_company(Long id, String filter){
		return "";
	}
	
	
	public String delete_company(){
		return "";
	}
}
