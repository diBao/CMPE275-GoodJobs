package rest.service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.module.Application;
import rest.module.JobSeeker;
import rest.module.Position;
import rest.repo.*;

@RestController
public class RestServiceController {  
    @Autowired
    private CompanyRepo repo_company;
    @Autowired
    private ApplicationRepo repo_application;
    @Autowired
    private PositionRepo repo_position;
    @Autowired
    private JobSeekerRepo repo_jobseeker;
	
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
    		@RequestParam(value = "workexperience", required = false) String workExperience,
    		@RequestParam(value = "education", required = false) String education,
    		@RequestParam(value = "skills", required = false) String skills,
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
    //get all interested positions for one jobseeker
    @RequestMapping(
    		value="/jobseeker/getInterest/{id}", 
    		method=RequestMethod.GET)
    public @ResponseBody String retrieveInterestPosition(@PathVariable Long id){
    	JobSeeker jobSeeker = repo_jobseeker.findBysID(id);
    	return getPositionsJSON("InterestPositions", jobSeeker.getInterestSet());
    }
    public String getPositionsJSON(String header, Set<Position> positions){
    	try{
			JSONObject result = new JSONObject();
			JSONObject[] jsonArray = new JSONObject[positions.size()];
			int i = 0;
			for(Position position:positions){
				JSONObject positionJson = new JSONObject();
				positionJson.put("pid", position.getpID());
				positionJson.put("title", position.getTitle());
				positionJson.put("description", position.getDescription());
				positionJson.put("responsibility", position.getResponsibility());
				positionJson.put("office location", position.getOfficeLocation());
				positionJson.put("salary", position.getSalary());
				result.put("status", position.getStatus());
				jsonArray[i++] = positionJson;
			}
			result.put(header, jsonArray);		
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}
    	
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
    		@RequestParam(value = "website", required = false) String website,
    		@RequestParam(value = "logoimageurl", required = false) String logoImageUrl,
    		@RequestParam(value = "address", required = false) String address,
    		@RequestParam("email") String email,
    		@RequestParam(value = "description", required = false) String description,
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
			value = "/company/position/{id}", 
			method = RequestMethod.GET)
	public @ResponseBody String retrievePositions(
			@PathVariable Long id, //company id
			@RequestParam(value = "status", required = false) String status
			) {
		RestCompany rest_company = new RestCompany(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_company.retrieve_positions(id, status);
	}
  
//<<<<<<< Updated upstream
    /*******************
     * Position
     ***************/
    //create position
    @RequestMapping(value="/position", method=RequestMethod.POST)
    public  @ResponseBody String createPosition(
    		@RequestParam("cid") Long cID,
    		@RequestParam("title") String title,
    		@RequestParam("description") String description,
    		@RequestParam("responsibilities") String responsibilities,
    		@RequestParam("officelocation") String officeLocation,
    		@RequestParam("salary") Long salary
    		) { 
		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
		return rest_position.createPosition(cID, title, description, responsibilities, officeLocation, salary).getJSON();
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
		//return rest_position.updatePosition(id, cID, title, description, responsibilities, officeLocation, salary, status);
		Position position = rest_position.updatePosition(id, title, description, responsibilities, officeLocation, salary, status);
		if(position==null){
			//TODO bad request
			return "Position with Id " + id + " is not existed";
		}
		 return position.getJSON();
    }
    /*
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
		return getPositionsJSON("SearchedPositions",rest_position.searchPositions(title, companyName, skill, salaryStart, salaryEnd, location));
    }*/
    
    //retrieve one position
    @RequestMapping(value="/position/{id}", method=RequestMethod.GET)
    public  @ResponseBody String retrievePosition(
    		@PathVariable Long id
    		) { 
		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
		Position position = rest_position.getPosition(id);
		if(position==null){
			//TODO bad request
			return "Position with Id " + id + " is not existed";
		}
		return position.getJSON();
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
		return rest_application.getApplication(id).getJSON();
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
		JobSeeker jobSeeker = repo_jobseeker.findOne(sID);
		if(jobSeeker==null){
			//TODO
			return "JobSeeker with Id " + sID + " is not existed";
		}
		
		Application application = rest_application.createApplication(jobSeeker, jobSeeker.getEmail(), jobSeeker.getFirstName(), jobSeeker.getLastName(), repo_position.findBypID(pID), resumeUrl);
		if(application==null){
			// TODO Error Message Application == null
			//A user cannot have more than 5 pending applicationss
			//A user cannot apply for the same position again if the previous application is not in a terminal state
			return "Not allow to Create this application";
		}
		return application.getJSON();
	}
    
    //jobseeker update application (for multiple applications)
    @RequestMapping(
			value = "/application/jobseeker", 
			method = RequestMethod.PUT)
	public @ResponseBody String updateApplication(
			@RequestParam("sid") Long sID,
			@RequestParam("aid") Long[] aID,
			@RequestParam("reply") String reply
			) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		Set<Long> aIDs = new HashSet<Long>();
		if(aID!=null&&aID.length!=0){
			for(Long id: aID){
				//check if some aId existed
				if(this.repo_application.findByaID(id)==null){
					//TODO not existed
					return "error input aIDs, application id "+ id +" is not existed";
				}
				//OK
				aIDs.add(id);
			}
		}
		rest_application.updateApplications(aIDs);
		return "multiple cancelled ok";
	}
    
    //company cancel application
    @RequestMapping(
			value = "/application/company", 
			method = RequestMethod.PUT)
	public @ResponseBody String cancelApplication(
			@RequestParam("cid") Long cID,
			@RequestParam("aid") Long aID,
			@RequestParam("reply") String reply
			) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		Application application = null;
		try{
			repo_application.findOne(aID);
		}catch(Exception e){
			//TODO
			return "Application with "+ aID +" not existed";
		}
		application = rest_application.updateApplication(aID,reply);
		if(application==null){
			//12.ii
			//TODO " bad request
			return "don't allow change the status for a offer accepted application";
		}
		return application.getJSON();
	}
    
  //jobseeker accept or reject application which application?
    @RequestMapping(
			value = "/application/{id}", 
			method = RequestMethod.PUT)
	public @ResponseBody String accRejApplication(
			@PathVariable Long id, // application ID
			@RequestParam("sid") Long sID,
			@RequestParam("reply") String reply
			) {
		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
		if(this.repo_application.findByaID(id)==null){
			return  "Application with Id " + id + " is not existed";
		}
		Application application = rest_application.updateApplication(id, reply);
		if(application==null){
			//TODO bad request
			return "don't allow change the status for a offer accepted application";
		}
		 return application.getJSON();
	}
//=======
//    
//    /*******************
//     * Position
//     ***************/
//    //create position
//    @RequestMapping(value="/position", method=RequestMethod.POST)
//    public  @ResponseBody String createPosition(
//    		@RequestParam("title") String title,
//    		@RequestParam("description") String description,
//    		@RequestParam("responsibilities") String responsibilities,
//    		@RequestParam("officelocation") String officeLocation,
//    		@RequestParam("salary") Long salary
//    		) { 
//		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_position.create_position(title, description, responsibilities, officeLocation, salary);
//    }
//    
//    //company update position
//    @RequestMapping(value="/position/{id}", method=RequestMethod.PUT)
//    public  @ResponseBody String updatePosition(
//    		@PathVariable Long id,
//    		@RequestParam(value = "cid") Long cID,
//    		@RequestParam(value = "title", required = false) String title,
//    		@RequestParam(value = "description", required = false) String description,
//    		@RequestParam(value = "responsibilities", required = false) String responsibilities,
//    		@RequestParam(value = "officelocation", required = false) String officeLocation,
//    		@RequestParam(value = "salary", required = false) Long salary,
//    		@RequestParam(value = "status", required = false) String status
//    		) { 
//		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_position.update_position(id, cID, title, description, responsibilities, officeLocation, salary, status);
//    }
//    
//    //jobseeker search positions
//    @RequestMapping(value="/position", method=RequestMethod.GET)
//    public  @ResponseBody String searchPosition(
//    		@RequestParam(value = "title", required = false) String[] title,
//    		@RequestParam(value = "companyname", required = false) String[] companyName,
//    		@RequestParam(value = "skill", required = false) String[] skill,
//    		@RequestParam(value = "salarystart", required = false) Long salaryStart,
//    		@RequestParam(value = "salaryend", required = false) Long salaryEnd,
//    		@RequestParam(value = "location", required = false) String[] location
//    		) { 
//		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_position.search_position(title, companyName, skill, salaryStart, salaryEnd, location);
//    }
//    
//    //retrieve one position
//    @RequestMapping(value="/position/{id}", method=RequestMethod.GET)
//    public  @ResponseBody String retrievePosition(
//    		@PathVariable Long id
//    		) { 
//		RestPosition rest_position = new RestPosition(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_position.retrieve_position(id);
//    }   		
//
//      
//    /*************
//     *  Application
//     *************/
//    //retrieve one application
//    @RequestMapping(
//			value = "/application/{id}", 
//			method = RequestMethod.GET)
//	public @ResponseBody String retrieveApplication(@PathVariable Long id) {
//		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_application.retrieve_application(id);
//	}
//    
//    //create application
//    @RequestMapping(
//			value = "/application", 
//			method = RequestMethod.POST)
//	public @ResponseBody String applyApplication(
//			@RequestParam("sid") Long sID,
//			@RequestParam("pid") Long pID,
//			@RequestParam(value = "resumeUrl", required = false) String resumeUrl
//			) {
//    	
//		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_application.create_application(sID, pID, resumeUrl);
//	}
//    
//    //jobseeker update application (for multiple applications)
//    @RequestMapping(
//			value = "/application", 
//			method = RequestMethod.PUT)
//	public @ResponseBody String updateApplication(
//			@RequestParam("sid") Long sID,
//			@RequestParam("aid") Long[] aID,
//			@RequestParam("reply") String reply
//			) {
//		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_application.update_application(sID, aID, reply);
//	}
//    
//    //company cancel application
//    @RequestMapping(
//			value = "/application", 
//			method = RequestMethod.PUT)
//	public @ResponseBody String cancelApplication(
//			@RequestParam("cid") Long cID,
//			@RequestParam("aid") Long aID,
//			@RequestParam("reply") String reply
//			) {
//		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_application.cancel_application(cID, aID, reply);
//	}
//    
//  //jobseeker accept or reject application
//    @RequestMapping(
//			value = "/application/{id}", 
//			method = RequestMethod.PUT)
//	public @ResponseBody String accRejApplication(
//    		@PathVariable Long id,
//			@RequestParam("sid") Long sID,
//			@RequestParam("reply") String reply
//			) {
//		RestApplication rest_application = new RestApplication(repo_jobseeker, repo_company, repo_application, repo_position);
//		return rest_application.acc_rej_application(sID, reply);
//	}
//>>>>>>> Stashed changes
}
