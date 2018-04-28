package demo.dao;

import org.springframework.stereotype.Component;

@Component("marvelService")
public class MarvelDaoImpl extends HeroeDaoImpl {

	@Override
	public String getHeroes() {
		return MARVEL;
	}

	
}
