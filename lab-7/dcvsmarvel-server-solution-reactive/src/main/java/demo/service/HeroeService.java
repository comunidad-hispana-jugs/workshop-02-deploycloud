package demo.service;

import demo.domain.Heroe;
import rx.Observable;

public interface HeroeService {

	Observable<Heroe> getMarvel();
	Observable<Heroe> getDc();	

}
