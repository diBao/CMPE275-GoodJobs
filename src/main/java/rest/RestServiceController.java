package rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import repo.CompanyRepo;


@RestController
public class RestServiceController {
    
    @Autowired
    private repo.CompanyRepo repo_company;

 
    
    static final Logger logger = LogManager.getLogger(RestServiceController.class.getName());

    /*******************
     * Company
     ***************/
    
    
    
    
    
    /*************
     *  Passenger
     *************/
    
    /* Create Passenger */
    @RequestMapping(value="/passenger", method=RequestMethod.POST)
    @ResponseBody
    public String createPassenger(String firstname, String lastname, int age, String gender,String phone) {
    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
    	return rest_pass.create_Passenger(firstname, lastname, age, gender, phone);
    }
        		
    /* Retrieve Passenger */
    @RequestMapping(value="/passenger/{id}", method=RequestMethod.GET)
    @ResponseBody
    public String retrievePassenger(@PathVariable("id") long id, boolean json, boolean xml) {
    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
    	return rest_pass.retrieve_Passenger(id, json, xml);
    }

    /* Update Passenger */
    @RequestMapping(value="/passenger/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public String updatePassenger(@PathVariable("id") long id, String firstname, String lastname, int age, String gender,String phone) {
    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
    	return rest_pass.update_Passenger(id, firstname, lastname, age, gender, phone);
    } 
    
    /* Delete Passenger */
    @RequestMapping(value="/passenger/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public String deletePassenger(@PathVariable("id") long id) {
    	RestPassenger rest_pass = new RestPassenger(repo_pass, repo_flight, repo_reserv);
    	return rest_pass.delete_Passenger(id);
    } 
        		

      

}
