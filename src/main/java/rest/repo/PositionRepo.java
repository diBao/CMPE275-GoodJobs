package rest.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import rest.module.JobSeeker;
import rest.module.Position;

public interface PositionRepo extends CrudRepository<Position, Long>{

	Position findBypID(Long pID);

	//Set<Position> findByCompanyName(String companyName); directly use companyRepo
	
	Set<Position> findByOfficeLocation(String officeLocation);//multiple composite OR
	Set<Position> findByTitle(String title);//multiple composite OR
	Set<Position> findByStatus(String status);
	
//	@Query("SELECT p.PID FROM Position p WHERE (p.DESCRIPTION LIKE concat('%',concat(?1, '%')))")
//	Set<Long> findBySkill(String skill);//multiple composite OR
//	//single salary value
//	Set<Long> findBySalary(Long salary);
//	//close range of salary
//	@Query("SELECT p.PID FROM Position p WHERE (p.SALARY BETWEEN ?1 AND ?2)")
//	Set<Long> findByCloseSalary(Long start, Long end);
//	//open range of salary
//	@Query("SELECT p.PID FROM Position p WHERE (p.SALARY > ?1)")
//	Set<Long> findByGreaterSalary(Long salary);//bigger open
//	@Query("SELECT p.PID FROM Position p WHERE (p.SALARY < ?1)")
//	Set<Long> findByLessSalary(Long salary);//less open
}
