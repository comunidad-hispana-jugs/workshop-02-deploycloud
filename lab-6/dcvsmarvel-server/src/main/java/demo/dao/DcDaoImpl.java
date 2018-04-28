package demo.dao;

import org.springframework.stereotype.Component;

@Component("dcService")
public class DcDaoImpl extends HeroeDaoImpl {

	@Override
	public String getHeroes() {
		return DC;
	}

	
}
