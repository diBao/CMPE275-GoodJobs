package rest.repo;

import org.springframework.data.repository.CrudRepository;

import rest.module.JobSeeker;
import rest.module.Position;

public interface PositionRepo extends CrudRepository<Position, Long>{

	Position findById(Long pID);
}
