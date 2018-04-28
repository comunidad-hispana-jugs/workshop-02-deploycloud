package demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.dao.DcClient;
import demo.dao.MarvelClient;
import demo.domain.Heroe;


@Service
public class HeroeServiceImpl implements HeroeService {

	@Autowired DcClient dcClient;

	@Autowired MarvelClient marvelClient;

	@HystrixCommand(fallbackMethod="getFallbackDc")
	public Heroe getDc() {
		return dcClient.getHeroe();
	}
	

	
	public Heroe getFallbackDc() {
		return new Heroe("Pepe");
	}
	
	public Heroe getFallbackMarvel() {
		return new Heroe("Paco");
	}



	@HystrixCommand(fallbackMethod="getFallbackMarvel")
	public Heroe getMarvel() {
		return marvelClient.getHeroe();
	}

	
	
	

}
