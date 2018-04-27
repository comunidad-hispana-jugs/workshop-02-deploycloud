package demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.domain.Member;
import demo.domain.Jug;
import demo.repository.JugRepository;

@SpringBootApplication
public class Application {

	@Autowired
    JugRepository jugRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @PostConstruct
	public void init() {
		List<Jug> list = new ArrayList<>();

		Set<Member> set = new HashSet<>();
		set.add(new Member("Jose Diaz", "Leader"));
		set.add(new Member("Eddu Melendez", "Co Leader"));
		set.add(new Member("Ytalo Borja", "Member"));
		
		list.add(new Jug("Peru", "Peru JUG", set));
		list.add(new Jug("Colombia","Barranquilla JUG",null));

		jugRepository.save(list);
	}    
    
}
