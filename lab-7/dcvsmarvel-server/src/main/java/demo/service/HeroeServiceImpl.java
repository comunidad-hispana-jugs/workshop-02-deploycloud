package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dao.DcClient;
import demo.dao.MarvelClient;
import demo.domain.Heroe;

@Service
public class HeroeServiceImpl implements HeroeService {

	@Autowired DcClient dcClient;
	@Autowired MarvelClient marvelClient;

	
	
	@Override
	public Heroe getMarvel() {
		return marvelClient.getHeroe();
	}
	
	@Override
	public Heroe getDc() {
		return dcClient.getHeroe();
	}
	

}
