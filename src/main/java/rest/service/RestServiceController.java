package rest.service;

import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestServiceController {  
    @Autowired
    private rest.repo.CompanyRepo repo_company;
    @Autowired
    private rest.repo.ApplicationRepo repo_application;
    @Autowired
    private rest.repo.PositionRepo repo_position;
    @Autowired
    private rest.repo.JobSeekerRepo repo_jobseeker;
	
	/*******************
	 * JobSeeker
	 ***************/
    
    //retrieve jobseeker
	@RequestMapping(
			value = "/jobseeker/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String getJobSeeker(@PathVariable Long id) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.read_jobseeker(id);
	}
	
	//create jobseeker
    @RequestMapping(value="/jobseeker", method=RequestMethod.POST)
    public  @ResponseBody String createJobSeeker(
    		@RequestParam("firstname") String firstName, 
    		@RequestParam("lastname") String lastName,
    		@RequestParam(value = "picture", required = false) String picture,
    		@RequestParam(value = "selfintroduction", required = false) String selfIntroduction, 
    		@RequestParam("workexperience") String workExperience,
    		@RequestParam("education") String education,
    		@RequestParam("skills") String skills,
    		@RequestParam("email") String email,
    		@RequestParam("password") String password
    		) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.create_jobseeker(firstName, lastName, picture, selfIntroduction, workExperience,
				education, skills, email, password);
    }
    
    //update jobseeker
    @RequestMapping(value="/jobseeker/{id}", method=RequestMethod.PUT)
    public  @ResponseBody String updateJobSeeker(
    		@PathVariable Long id,
    		@RequestParam(value = "firstname", required = false) String firstName, 
    		@RequestParam(value = "lastname", required = false) String lastName,
    		@RequestParam(value = "picture", required = false) String picture,
    		@RequestParam(value = "selfintroduction", required = false) String selfIntroduction, 
    		@RequestParam(value = "workexperience", required = false) String workExperience,
    		@RequestParam(value = "education", required = false) String education, 
    		@RequestParam(value = "skills", required = false) String skills,
    		@RequestParam(value = "email", required = false) String email,
    		@RequestParam(value = "password", required = false) String password
    		) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.update_jobseeker(id, firstName, lastName, picture, selfIntroduction, workExperience,
				education, skills, email, password);
    }
    
    //mark interest
    @RequestMapping(
			value = "/jobseeker/{id}",
			params= "mark",
			method = RequestMethod.POST)
	public @ResponseBody String markInterest(
			@PathVariable Long id,
			@RequestParam("mark") Long mark
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.mark_interest(id, mark);
	}
    
    //unmark interest
    @RequestMapping(
			value = "/jobseeker/{id}",
			params= "unmark",
			method = RequestMethod.POST)
	public @ResponseBody String unmarkInterest(
			@PathVariable Long id,
			@RequestParam("unmark") Long unmark
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.unmark_interest(id, unmark);
	}
    
    //delete jobseeker
    @RequestMapping(
			value = "/jobseeker/{id}",
			method = RequestMethod.DELETE)
	public @ResponseBody String deleteJobSeeker(
			@PathVariable Long id
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.delete_jobseeker(id);
	}
    
    //retrieve all applications 
    @RequestMapping(
			value = "/jobseeker/application/{id}",
			method = RequestMethod.GET)
	public @ResponseBody String retrieveAllApplications(
			@PathVariable Long id
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.retrieve_all_applications(id);
	}
    
    
    /*******************
     * Company
     ***************/
    
    //create company
    @RequestMapping(value="/company", method=RequestMethod.POST)
    public  @ResponseBody String createCompany(
    		@RequestParam("name") String name,
    		@RequestParam("website") String website,
    		@RequestParam("logoimageurl") String logoImageUrl,
    		@RequestParam("address") String address,
    		@RequestParam("email") String email,
    		@RequestParam("description") String description,
    		@RequestParam("password") String password
    		) {
		RestCompany rest_company = new RestCompany(repo_jobseeker, repo_company, repo_application, repo_position);
		
		return rest_company.create_company(name, website, logoImageUrl, address, email,
				description, password);
    }
    
    //update company
    @RequestMapping(value="/company/{id}", method=RequestMethod.PUT)
    public  @ResponseBody String updateCompany(
    		@PathVariable Long id,
    		@RequestParam(value = "name", required = false) String name,
    		@RequestParam(value = "website", required = false) String website,
    		@RequestParam(value = "logoimageurl", required = false) String logoImageUrl,
    		@RequestParam(value = "address", required = false) String address,
    		@RequestParam(value = "email", required = false) String email,
    		@RequestParam(value = "description", required = false) String description,
    		@RequestParam(value = "password", required = false) String password
    		) {
    	RestCompany rest_company = new RestCompany(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_company.update_company(id, name, website, logoImageUrl, address, email,
				description, password);
    }
    
    //retrieve company
    @RequestMapping(
			value = "/company/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String retrieveCompany(
			@PathVariable Long id
			) {
		RestCompany rest_company = new RestCompany(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_company.retrieve_company(id);
	}
    
    //retrieve all positions from company
    @RequestMapping(
			value = "/company/{id}", 
			params= "status",
			method = RequestMethod.GET)
	public @ResponseBody String retrievePositions(
			@PathVariable Long id,
			@RequestParam("status") String status
			) {
		RestCompany rest_company = new RestCompany(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_company.retrieve_positions(id, status);
	}
  
    
    /*******************
     * Position
     ***************/
    //create position
    @RequestMapping(value="/position", method=RequestMethod.POST)
    public  @ResponseBody String createPosition(
    		@RequestParam("title") String title,
    		@RequestParam("description") String description,
    		@RequestParam("responsibilities") String responsibilities,
    		@RequestParam("officelocation") String officeLocation,
    		@RequestParam("salary") Long salary
    		) { 
		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_position.create_position(title, description, responsibilities, officeLocation, salary);
    }
    
    //company update position
    @RequestMapping(value="/position/{id}", method=RequestMethod.PUT)
    public  @ResponseBody String updatePosition(
    		@PathVariable Long id,
    		@RequestParam(value = "cid") Long cID,
    		@RequestParam(value = "title", required = false) String title,
    		@RequestParam(value = "description", required = false) String description,
    		@RequestParam(value = "responsibilities", required = false) String responsibilities,
    		@RequestParam(value = "officelocation", required = false) String officeLocation,
    		@RequestParam(value = "salary", required = false) Long salary,
    		@RequestParam(value = "status", required = false) String status
    		) { 
		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_position.update_position(id, cID, title, description, responsibilities, officeLocation, salary, status);
    }
    
    //jobseeker search positions
    @RequestMapping(value="/position", method=RequestMethod.GET)
    public  @ResponseBody String searchPosition(
    		@RequestParam("title") String[] title,
    		@RequestParam("companyname") String[] companyName,
    		@RequestParam("skill") String[] skill,
    		@RequestParam(value = "salarystart", required = false) Long salaryStart,
    		@RequestParam(value = "salaryend", required = false) Long salaryEnd,
    		@RequestParam(value = "location", required = false) String[] location
    		) { 
		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_position.search_position(title, companyName, skill, salaryStart, salaryEnd, location);
    }
    
    //retrieve one position
    @RequestMapping(value="/position/{id}", method=RequestMethod.GET)
    public  @ResponseBody String retrievePosition(
    		@PathVariable Long id
    		) { 
		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_position.retrieve_position(id);
    }   		

      
    /*************
     *  Application
     *************/
    //retrieve one application
    @RequestMapping(
			value = "/application/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String retrieveApplication(@PathVariable Long id) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_application.retrieve_application(id);
	}
    
    //create application
    @RequestMapping(
			value = "/application", 
			method = RequestMethod.POST)
	public @ResponseBody String applyApplication(
			@RequestParam("sid") Long sID,
			@RequestParam("pid") Long pID,
			@RequestParam(value = "resumeUrl", required = false) String resumeUrl
			) {
    	
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_application.create_application(sID, pID, resumeUrl);
	}
    
    //jobseeker update application (for multiple applications)
    @RequestMapping(
			value = "/application", 
			method = RequestMethod.PUT)
	public @ResponseBody String updateApplication(
			@RequestParam("sid") Long sID,
			@RequestParam("aid") Long[] aID,
			@RequestParam("reply") String reply
			) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_application.update_application(sID, aID, reply);
	}
    
    //company cancel application
    @RequestMapping(
			value = "/application", 
			method = RequestMethod.PUT)
	public @ResponseBody String cancelApplication(
			@RequestParam("cid") Long cID,
			@RequestParam("aid") Long aID,
			@RequestParam("reply") String reply
			) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_application.cancel_application(cID, aID, reply);
	}
    
  //jobseeker accept or reject application
    @RequestMapping(
			value = "/application/{id}", 
			method = RequestMethod.PUT)
	public @ResponseBody String accRejApplication(
			@RequestParam("sid") Long sID,
			@RequestParam("reply") String reply
			) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_application.acc_rej_application(sID, reply);
	}
}
