package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import demo.domain.Jug;

@RestResource(path="teams", rel="team")
public interface TeamRepository extends CrudRepository<Jug,Long>{

}
