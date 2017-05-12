package rest.repo;

import org.springframework.data.repository.CrudRepository;

import rest.module.Application;

public interface ApplicationRepo  extends CrudRepository<Application, Long>{
	public Application findByaID(Long aID);
	//search by both jobSeeker and position
	public Set<Application> findApplicationByJobSeekerAndPosition(JobSeeker jobSeeker,Position position);
	public Set<Application> findByPosition(Position position);
}
