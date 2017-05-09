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
//    @Autowired
//    private rest.repo.CompanyRepo repo_company;
	
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
			value = "jobseeker/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String getJobSeeker(@PathVariable Long id) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker();
		return rest_jobseeker.get_jobseeker(id);
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
		RestJobSeeker rest_jobseeker = new RestJobSeeker();
		return rest_jobseeker.create_jobseeker(firstName, lastName, picture, selfIntroduction, workExperience,
				education, skills, email, password);
    }
    
    @RequestMapping(
			value = "jobseeker/{id}",
			params= "mark",
			method = RequestMethod.POST)
	public @ResponseBody String markInterest(
			@PathVariable Long id,
			@RequestParam("mark") String mark
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker();
		return rest_jobseeker.mark_interest(id, mark);
	}
    
    @RequestMapping(
			value = "jobseeker/{id}",
			params= "unmark",
			method = RequestMethod.POST)
	public @ResponseBody String unmarkInterest(
			@PathVariable Long id,
			@RequestParam("unmark") String unmark
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker();
		return rest_jobseeker.unmark_interest(id, unmark);
	}
    
//    Apply for a position
//    A user can apply for a selected position in search result page or the interested jobs page.
//    A user can choose to apply with his profile, or attach a resume. In both cases, the user name and email from the user’s profile becomes part of the application.
//    For any apply operation, the user would receive an email notification with the information of position(s).
//    You need to provide a view for a user to browse or cancel his applications.
//    Each application has a status, Pending, Offered, Rejected, OfferAccepted, OfferRejcted, or Cancelled. The latter four states are also called terminal state. 
//    A user can cancel a pending application and reject an offered application.
//    The user can cancel (or reject) one or more applications (or offers) in one transaction.
//    The company can cancel any application that is not in a terminal state. 
//    Jobs of all states need to show up in the application view.
//    A user cannot have more than 5 pending applications. 
//    A user cannot apply for the same position again if the previous application is not in a terminal state.
//    For any status change of an application, the job seeker receives an email update as well.
//TODO
    @RequestMapping(
			value = "jobseeker/{id}", 
			params= "position",
			method = RequestMethod.POST)
	public @ResponseBody String applyPosition(
			@PathVariable Long id,
			@RequestParam("position") String position
			) {
		RestJobSeeker rest_jobseeker = new RestJobSeeker();
		return rest_jobseeker.mark_interest(id, mark);
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
}
