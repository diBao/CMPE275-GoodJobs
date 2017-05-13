package rest.repo;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import rest.module.Application;
import rest.module.JobSeeker;
import rest.module.Position;

public interface ApplicationRepo  extends CrudRepository<Application, Long>{
	public Application findById(Long aID);
	//search by both jobSeeker and position
	public Set<Application> findApplicationByJobSeekerAndPosition(JobSeeker jobSeeker,Position position);
	public Set<Application> findByPosition(Position position);
}
