package rest.repo;

import org.springframework.data.repository.CrudRepository;

import rest.module.Application;

public interface ApplicationRepo  extends CrudRepository<Application, Long>{

}
