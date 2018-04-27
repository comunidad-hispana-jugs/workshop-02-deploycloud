package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import demo.domain.Jug;

@RestResource(path="jugs", rel="jug")
public interface JugRepository extends CrudRepository<Jug,Long>{

}
