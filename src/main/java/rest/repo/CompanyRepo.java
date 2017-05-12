package rest.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rest.module.Company;
import rest.module.JobSeeker;

public interface CompanyRepo extends CrudRepository<Company, Long>{
	Company findBycID(Long cID);
}
