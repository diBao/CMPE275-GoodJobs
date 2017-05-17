package rest.service;

import rest.repo.ApplicationRepo;
import rest.repo.CompanyRepo;
import rest.repo.JobSeekerRepo;
import rest.repo.PositionRepo;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.json.JSONException;
import org.json.JSONObject;

import rest.module.Application;
import rest.module.Company;
import rest.module.JobSeeker;
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
	public Position createPosition(String cEmail, String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary) {
		// with initial status + new generate pID
		Company company = this.repo_company.findByemail(cEmail);
		
		Position position = new Position(company, pTitle, pDesciption, responsibility, officeLocation, salary);
		//System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		//System.out.println("SIZE IS: " + company.getPositionSet().size());
		Set<Position> position_set = company.getPositionSet();
		position_set.add(position);
		//System.out.print("SIZE IS: " + company.getPositionSet().size());
		company.setPositionSet(position_set);
		repo_company.save(company);
		return repo_position.save(position);
	}
	public Position getPosition(Long pID){
		return repo_position.findBypID(pID);
	}
	public Position updatePosition(Long pID,String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary,String status){
		//Notification all the seeker and Same return with create.
		Position position = repo_position.findOne(pID);
		if(position==null){ return position;}
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
			if(responsibility != null){
				position.setResponsibility(responsibility);
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
					for(Application application: repo_application.findByPosition(position)){
						cancelledCandidates.add(application.getaID());
					}
					
					//including notify job seekers in application list
					RestApplication restapplication = new RestApplication(this.repo_jobseeker, this.repo_company, this.repo_application, this.repo_position);
					restapplication.updateApplications(cancelledCandidates);//already notify seeker in this function
					//If a position gets filled or cancelled by the company, 
					//it would be removed from applicant�s interesting list automatically
					for(JobSeeker jobSeeker: position.getInterestSet()){
						jobSeeker.getInterestSet().remove(position);
						//sposition.getInterestSet().remove(jobSeeker);
					}
				}else{
					//no influence to application, just notify job seekers
					String[] jobseekerEmail = new String[position.getApplicationSet().size()];
					int i = 0;
					for(Application application:position.getApplicationSet()){
						jobseekerEmail[i++] = application.getJobSeeker().getEmail();
					}
					Company company = position.getCompany();
					notificationSeeker(company.getEmail(),company.getPassword(), jobseekerEmail, position.getStatus());
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
			String[] jobseekerEmail = new String[position.getApplicationSet().size()+position.getInterestSet().size()];
			int i = 0;
			for(Application application:position.getApplicationSet()){
				jobseekerEmail[i++] = application.getJobSeeker().getEmail();
			}
			for(JobSeeker jobSeeker:position.getInterestSet()){
				jobseekerEmail[i++] = jobSeeker.getEmail();
			}
			Company company = position.getCompany();
			notificationSeeker(company.getEmail(),company.getPassword(), jobseekerEmail, "Deleted");

			//notificationSeeker();//including applications and interests
			return true;
		}
	
		//not find pID or fail delete : false
		return false;
	}
	public void notificationSeeker(String from,String password, String[] to, String status){
		//listen function, listen to the changes of itself and notify its seekerSet
		//TODO
        String subject = "GoodJobs notification";
        String body ="Position status changes to"+ status;
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
	
	public Set<Position> searchPositions(String[] titles,String[] companyNames,String[] skills,Long salaryStart,Long salaryEnd,String[] locations){
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
		boolean isMatched = false;
		Set<Position> searchResult = new HashSet<Position>();
		Set<Position> tmpResult = new HashSet<Position>();
		
		Set<Position> searchTitles = new HashSet<Position>();
		boolean titleMatch = true;
		if(titles!=null&&titles.length!=0){
			for(String title:titles){
				searchTitles.addAll(repo_position.findByTitle(title));
			}
		}else{
			titleMatch = false;
		}
		
		if(titleMatch){
			isMatched = true;
			searchResult.addAll(searchTitles);
		}
		
		Set<Position> searchCompanys = new HashSet<Position>();
		boolean companyMatch = true;
		if(companyNames!=null&&companyNames.length!=0){
			for(String companyName:companyNames){
				for(Company company: repo_company.findByCompanyName(companyName)){
					searchCompanys.addAll(company.getPositionSet());
				}
			}
		}else{
			companyMatch = false;
		}
		if(companyMatch){
			if(isMatched){
				for(Position position:searchResult){
					if(searchCompanys.contains(position)==true){
						tmpResult.add(position);
					}
				}
				searchResult.clear();
				searchResult.addAll(tmpResult);
				tmpResult.clear();
			}else{
				searchResult.addAll(searchCompanys);
			}
			isMatched = true;
			
		}
		Set<Position> searchSkills = new HashSet<Position>();
		boolean skillMatch = true;
		if(skills!=null&&skills.length!=0){
			for(Position position:repo_position.findAll()){
				for(String skill:skills){
					if(position.getDescription().contains(skill)||position.getResponsibility().contains(skill)){
						searchSkills.add(position);
						break;
					}
				}
			}
		}else{
			skillMatch = false;
		}
		
		if(skillMatch){
			if(isMatched){
				for(Position position:searchResult){
					if(searchSkills.contains(position)==true){
						tmpResult.add(position);
					}
				}
				searchResult.clear();
				searchResult.addAll(tmpResult);
				tmpResult.clear();
			}else{
				searchResult.addAll(searchSkills);
			}
			isMatched = true;
			
		}
		Set<Position> searchLocations = new HashSet<Position>();
		boolean locationMatch = true;
		if(locations!=null&&locations.length!=0){
			for(String location:locations){
				searchLocations.addAll(repo_position.findByOfficeLocation(location));
			}
		}else{
			locationMatch = false;
		}
		if(locationMatch){
			if(isMatched){
				for(Position position:searchResult){
					if(searchLocations.contains(position)==true){
						tmpResult.add(position);
					}
				}
				searchResult.clear();
				searchResult.addAll(tmpResult);
				tmpResult.clear();
			}else{
				searchResult.addAll(searchLocations);
			}
			isMatched = true;
		}
		Set<Position> searchSalary = new HashSet<Position>();
		boolean salaryMatch = true;
		if(salaryStart==null&&salaryEnd==null){
			salaryMatch = false;
		}
 		if(salaryStart==null&&salaryEnd!=null){
			for(Position position:repo_position.findAll()){
				if(position.getSalary() < salaryEnd){
 					searchSalary.add(position);
				}
			}
		}
		if(salaryEnd==null&&salaryStart!=null){
			for(Position position:repo_position.findAll()){
				if(position.getSalary() > salaryStart){
 					searchSalary.add(position);
				}
			}
		}
		if(salaryStart!=null&&salaryEnd!=null){
 			if(salaryStart.equals(salaryEnd)){
				for(Position position:repo_position.findAll()){
					if(position.getSalary() == salaryEnd){
						searchSalary.add(position);
					}
				}
			}else{
				for(Position position:repo_position.findAll()){
					Long salary = position.getSalary();
					if( salary > salaryStart && salary < salaryEnd){
						searchSalary.add(position);
					}
				}
			}
		}
		if(salaryMatch){
			if(isMatched){
				for(Position position:searchResult){
					if(searchSalary.contains(position)==true){
						tmpResult.add(position);
					}
				}
				searchResult.clear();
				searchResult.addAll(tmpResult);
				tmpResult.clear();
			}else{
				searchResult.addAll(searchSalary);
			}
		}
		
		return searchResult;
	}	

	public String getGlobalPositionsJSON(String header, Set<Position> positions){
    	try{
			JSONObject result = new JSONObject();
			JSONObject[] jsonArray = new JSONObject[positions.size()];
			int i = 0;
			for(Position position:positions){
				JSONObject positionJson = new JSONObject();
				Company company = position.getCompany();
				positionJson.put("company_email", company.getEmail());
				positionJson.put("company_name", company.getCompanyName());
				positionJson.put("pid", position.getpID());
				positionJson.put("title", position.getTitle());
				positionJson.put("description", position.getDescription());
				positionJson.put("responsibility", position.getResponsibility());
				positionJson.put("office_location", position.getOfficeLocation());
				positionJson.put("salary", position.getSalary());
				
				jsonArray[i++] = positionJson;
			}
			result.put(header, jsonArray);		
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}
    	
    }    
    
	public String getGlobalPositions(){
		Set<Position> result = new HashSet<Position>();
		Set<Company> companies = repo_company.findAll();
		for(Company i : companies){
			Set<Position> positions = i.getPositionSet();
			for(Position j : positions){
				result.add(j);
			}			
		}		
		return getGlobalPositionsJSON("AllPositions", result);
	}
	
	//DONE by george 3
	public String retrieve_all_applications(Long id){
		Position position = repo_position.findBypID(id);
		Set<Application> applications = position.getApplicationSet();
		return getApplicationsJSON("Applicaitons", applications);
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
	
	//DONE by george 4
	public String retrieve_all_interests(Long id){ //position id
		Position position = repo_position.findBypID(id);
		Set<JobSeeker> interests = position.getInterestSet();
		return getInterestSeekerJSON("interestApplicants", interests);
	}
	
	public String getInterestSeekerJSON(String header, Set<JobSeeker> jobSeekers){
    	try{
			JSONObject result = new JSONObject();
			JSONObject[] jsonArray = new JSONObject[jobSeekers.size()];
			int i = 0;
			for(JobSeeker jobSeeker: jobSeekers){
				JSONObject jobSeekerJson = new JSONObject();
				jobSeekerJson.put("sid", jobSeeker.getsID());
				jobSeekerJson.put("education", jobSeeker.getEducation());
				jobSeekerJson.put("email", jobSeeker.getEmail());
				jobSeekerJson.put("experience", jobSeeker.getExperience());
				jobSeekerJson.put("firstName", jobSeeker.getFirstName());
				jobSeekerJson.put("introduction", jobSeeker.getIntroduction());
				jobSeekerJson.put("lastName", jobSeeker.getLastName());
				jobSeekerJson.put("picture", jobSeeker.getPicture());
				jobSeekerJson.put("skills", jobSeeker.getSkills());
				jsonArray[i++] = jobSeekerJson;
			}
			result.put(header, jsonArray);		
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}
    }

}
