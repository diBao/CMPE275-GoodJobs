package rest.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rest.module.Company;

public interface CompanyRepo extends CrudRepository<Company, Long>{

}
