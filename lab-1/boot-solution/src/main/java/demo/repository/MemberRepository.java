package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import demo.domain.Member;

@RestResource(path="players", rel="player")
public interface PlayerRepository extends CrudRepository<Member,Long>{

}
