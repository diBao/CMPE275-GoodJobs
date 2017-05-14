package rest.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import rest.module.JobSeeker;
import rest.module.Position;

public interface PositionRepo extends CrudRepository<Position, Long>{

	Position findBypID(Long pID);

 	
	Set<Position> findByOfficeLocation(String officeLocation);//multiple composite OR
	Set<Position> findByTitle(String title);//multiple composite OR
	Set<Position> findByStatus(String status);
	
}
