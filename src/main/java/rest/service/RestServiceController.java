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

//import rest.repo.CompanyRepo;



@RestController
public class RestServiceController {
//    
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
//    @RequestMapping(
//			value = "/{number}", 
//			method = RequestMethod.POST,
//			produces = "application/json")
//	public @ResponseBody Object updateReservation(
//			@PathVariable String number,
//			@RequestParam(value = "flightsAdded", required = false) String[] flightsAdded,
//            @RequestParam(value = "flightsRemoved", required = false) String[] flightsRemoved,
//            HttpServletResponse response) throws ParseException{
    
	@RequestMapping(
			value = "/jobseeker/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String getJobSeeker(@PathVariable Long id) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.read_jobseeker(id);
	}
	
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
		return rest_jobseeker.update_jobseeker(firstName, lastName, picture, selfIntroduction, workExperience,
				education, skills, email, password);
    }
    
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
    
    @RequestMapping(
			value = "/jobseeker/{id}",
			method = RequestMethod.DELETE)
	public @ResponseBody String deleteJobSeeker(
			@PathVariable Long id
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_jobseeker.delete_jobseeker(id);
	}

    
    /*******************
     * Company
     ***************/

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
    
    @RequestMapping(
			value = "/company/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String getCompany(
			@PathVariable Long id,
			@RequestParam("fliter") String filter) {
		RestCompany rest_company = new RestCompany(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_company.search_company(id, filter);
	}
    

    
    
    
//    static final Logger logger = LogManager.getLogger(RestServiceController.class.getName());

  
    
    /*******************
     * Position
     ***************/
    
//  Company name (allow multiple)
//  Location (city names, allow multiple)
//  Salary range (can be a single value, an open range, or a close range)
//  @RequestMapping(value="/jobseeker", method=RequestMethod.POST)
//  public  @ResponseBody String createJobSeeker(
//  		@RequestParam("firstname") String firstName, 
//  		@RequestParam("lastname") String lastName,
//  		@RequestParam(value = "picture", required = false) String picture
//  		) {
//		RestJobSeeker rest_jobseeker = new RestJobSeeker();
//		return rest_jobseeker.create_jobseeker(firstName, lastName, picture, selfIntroduction, workExperience,
//				education, skills, email, password);
//  }
  
    @RequestMapping(value="/position", method=RequestMethod.POST)
    public  @ResponseBody String createPosition(
    		@RequestParam("title") String title,
    		@RequestParam("description") String description,
    		@RequestParam("responsibilities") String responsibilities,
    		@RequestParam("officelocation") String officeLocation,
    		@RequestParam("salary") Long salary
    		) {
    	//TODO 
//		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_position.create_position(title, description, responsibilities, officeLocation, salary);
    	
    	return "";
    }
  
   
    
//    /* Create Passenger */
//    @RequestMapping(value="/passenger", method=RequestMethod.POST)
//    @ResponseBody
//    public String createPassenger(String firstname, String lastname, int age, String gender,String phone) {
//    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
//    	return rest_pass.create_Passenger(firstname, lastname, age, gender, phone);
//    }
//        		
//    /* Retrieve Passenger */
//    @RequestMapping(value="/passenger/{id}", method=RequestMethod.GET)
//    @ResponseBody
//    public String retrievePassenger(@PathVariable("id") long id, boolean json, boolean xml) {
//    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
//    	return rest_pass.retrieve_Passenger(id, json, xml);
//    }
//
//    /* Update Passenger */
//    @RequestMapping(value="/passenger/{id}", method=RequestMethod.PUT)
//    @ResponseBody
//    public String updatePassenger(@PathVariable("id") long id, String firstname, String lastname, int age, String gender,String phone) {
//    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
//    	return rest_pass.update_Passenger(id, firstname, lastname, age, gender, phone);
//    } 
//    
//    /* Delete Passenger */
//    @RequestMapping(value="/passenger/{id}", method=RequestMethod.DELETE)
//    @ResponseBody
//    public String deletePassenger(@PathVariable("id") long id) {
//    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
//    	return rest_pass.delete_Passenger(id);
//    } 
//        		

      
    /*************
     *  Application
     *************/
    
    @RequestMapping(
			value = "/application", 
			method = RequestMethod.POST)
	public @ResponseBody String applyPosition(
			@RequestParam("id") Long id,
			@RequestParam("position") String position, //TODO possible duplicate parameter
			@RequestParam(value = "resumeUrl", required = false) String resumeUrl
			) {
    	//TODO
//		RestApplication rest_application = new RestApplication();
//		return rest_application.apply_position(id, position, resumeUrl);
    	return "";
	}
}
