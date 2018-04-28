package demo.dao;

import demo.domain.Heroe;

public interface HeroeDao {

	static final String MARVEL = "MARVEL";
	static final String DC = "DC";
	
	Heroe getHeroe();
	
}
