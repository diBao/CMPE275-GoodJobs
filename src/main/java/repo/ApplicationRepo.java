package repo;

import org.springframework.data.repository.CrudRepository;

import module.Application;

public interface ApplicationRepo  extends CrudRepository<Application, Long>{

}
