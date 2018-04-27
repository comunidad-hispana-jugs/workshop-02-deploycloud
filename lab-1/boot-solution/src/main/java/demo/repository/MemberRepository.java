package demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import demo.domain.Member;

@RestResource(path="members", rel="member")
public interface MemberRepository extends CrudRepository<Member,Long>{

}
