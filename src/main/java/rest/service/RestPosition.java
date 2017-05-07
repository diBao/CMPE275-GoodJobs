package rest.service;

import rest.repo.PositionRepo;
import rest.module.Position;
public class RestPosition {
	private PositionRepo positionRepo;
	Position createPostion(String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary) {
		// with initial status + new generate pID
		Position position = new Position();
		position.setDescription(pDesciption);
		position.setResponsibility(responsibility);
		position.setOfficeLocation(officeLocation);
		position.setSalary(salary);
		position.setStatus("pending");
		return positionRepo.save(position);
	}
	Position getPosition(Long pID){
		return positionRepo.findOne(pID);
	}
	Position updatePosition(Long pID,String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary,String status){
		//Notification all the seeker and Same return with create.
		Position position = positionRepo.findOne(pID);
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
			if(salary!=null){
				position.setSalary(salary);
			}
			if(status!=null){
				position.setStatus(status);
			}
			positionRepo.save(position);
		}
		return position;
	}
	boolean deletePosition(Long pID){ 
		Position position = positionRepo.findOne(pID);
		if(position!=null){
			try{
				positionRepo.delete(position);
			}catch(Exception e){
				// fail delete, MySql throw exception
				return false;
			}
			// success delete: true
			return true;
		}
	
		//not find pID or fail delete : false
		return false;
	}
	Void notificationSeeker(){
		return null;
		//listen function, listen to the changes of itself and notify its seekerSet
	}

}
