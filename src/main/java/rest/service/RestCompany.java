package rest.service;

import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONException;
import org.json.JSONObject;

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
			String from = "cmpe275goodjobs@gmail.com";
	    	//please add it in local env and do not git push unless you delete the password
	    	String emailPassword = "qgv-hzg-k92-PZZ";
			String subject = "GoodJobs Confirmation";
		    String body ="You have company acount: "+ email + " in GoodJobs now!s";
		    Properties props = System.getProperties();
		    String host = "smtp.gmail.com";
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.user", from);
		    props.put("mail.smtp.password", emailPassword);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
		    Session session = Session.getDefaultInstance(props);
		    MimeMessage message = new MimeMessage(session);
		    try {
		            message.setFrom(new InternetAddress(from));
		            InternetAddress toAddress = new InternetAddress(email);
		            message.addRecipient(Message.RecipientType.TO, toAddress);
		            message.setSubject(subject);
		            message.setText(body);
		            Transport transport = session.getTransport("smtp");
		            transport.connect(host, from, emailPassword);
		            transport.sendMessage(message, message.getAllRecipients());
		            transport.close();
		    }catch (AddressException ae) {
		            ae.printStackTrace();
		    }catch (MessagingException me) {
		            me.printStackTrace();
		    }
			return company.getJSON();
		}
		catch(Exception e){
			//TODO error message
			return "create Company failed";
		}
	}
	
	public String retrieve_company(String email){
		Company company = repo_company.findByemail(email);
		if(company != null){
			return company.getJSON();
		}
		else{
			//TODO error message 
			return "No result found";
		}		
	}
	
	public String update_company(String newEmail, String name, String website, String logoImageUrl, String address, String email, String description,
			String password){
		Company company = repo_company.findByemail(newEmail);
		if(company == null){
			return "No Company found";
		}
		
		if(name != null){
			company.setCompanyName(name);
		}
		if(website != null){
			company.setWebsite(website);
		}
		if(logoImageUrl != null){
			company.setLogoImageUrl(logoImageUrl);
		}
		if(address != null){
			company.setAddress(address);
		}
		if(email != null){
			company.setEmail(email);
		}
		if(description != null){
			company.setDescription(description);
		}
		if(password != null){
			company.setPassword(password);
		}
				
		try {
        	repo_company.save(company);
        } catch (Exception e) {
        	return "Company update failed";
        }
		
		return company.getJSON();		
	}
	
	
	public String retrieve_positions(String email, String status){
		
		Company company = repo_company.findByemail(email);
		if(company != null){			
			if(status == null){
				Set<Position> p_set = company.getPositionSet();
				JSONObject result = new JSONObject();
				JSONObject[] positions = new JSONObject[p_set.size()];
				int count = 0;
				for(Position i : p_set){
					positions[count] = i.getJSONObj();
					count++;				
				}
				try {
					result.put("positions", positions);
					return result.toString();	
				} catch (JSONException e) {
					return "JSON convert failed";
				}		
			}
			else{
				Set<Position> p_set = company.getPositionSet();
				int k = 0;
				for(Position j : p_set){
					if(status.equals(j.getStatus())){
						k++;
					}
				}
				
				JSONObject result = new JSONObject();
				JSONObject[] positions = new JSONObject[k];
				int count = 0;
				for(Position i : p_set){
					if(status.equals(i.getStatus()) && count < k){
						positions[count] = i.getJSONObj();
						count++;	
					}								
				}
				try {
					if(positions.length == 0){
						return null;
					}
					
					result.put("positions", positions);
					return result.toString();	
				} catch (JSONException e) {
					return "JSON convert failed";
				}	
			}
		}
		else{
			//TODO error message 
			return "No result found";
		}		
	}

	/*
	public String delete_company(){
		return "";
	}*/
}
