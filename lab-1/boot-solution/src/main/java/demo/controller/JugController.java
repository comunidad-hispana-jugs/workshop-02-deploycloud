package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.Jug;
import demo.repository.JugRepository;

@RestController
public class JugController {

	@Autowired
	JugRepository jugRepository;
	
	@RequestMapping("/jugs")
	public Iterable<Jug> getJugs() {
		return jugRepository.findAll();
	}
	
	@RequestMapping("/jugs/{id}")
	public Jug getJug(@PathVariable Long id){
		return jugRepository.findOne(id);
	}
	
}
