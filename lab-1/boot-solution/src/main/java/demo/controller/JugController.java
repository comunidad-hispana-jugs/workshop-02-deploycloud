package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.Jug;
import demo.repository.JugRepository;

@RestController
public class TeamController {

	@Autowired
	JugRepository jugRepository;
	
	@RequestMapping("/teams")
	public Iterable<Jug> getTeams() {
		return jugRepository.findAll();
	}
	
	@RequestMapping("/teams/{id}")
	public Jug getTeam(@PathVariable Long id){
		return jugRepository.findOne(id);
	}
	
}
