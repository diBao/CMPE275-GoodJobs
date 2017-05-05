package repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import module.Company;

public interface CompanyRepo extends CrudRepository<Company, Long>{

}
