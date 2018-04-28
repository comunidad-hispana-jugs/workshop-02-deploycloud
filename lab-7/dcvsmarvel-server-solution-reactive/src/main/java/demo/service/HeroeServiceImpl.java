package demo.service;


import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import demo.dao.DcClient;
import demo.dao.MarvelClient;
import demo.domain.Heroe;
import demo.domain.Heroe.Role;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ken Krueger, Jorge Centeno Fernandez 
 */
@Service
public class HeroeServiceImpl implements HeroeService {

	@Autowired DcClient dcClient;

	@Autowired MarvelClient marvelClient;

	@Autowired Executor executor;	//	Source of threads
	
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackDc")
	public Observable<Heroe> getDc() {
		//	This 'reactive' observable is backed by a regular Java Callable, which can run in a different thread:
		return Observable.fromCallable(
			() ->  new Heroe (dcClient.getHeroe().getHeroe(), Role.dc)
		).subscribeOn(Schedulers.from(executor));
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackMarvel")
	public Observable<Heroe> getMarvel() {
		return Observable.fromCallable(
			() ->  new Heroe (marvelClient.getHeroe().getHeroe(), Role.marvel)
		).subscribeOn(Schedulers.from(executor));
	}
	

	
	
	public Heroe getFallbackDc() {
		return new Heroe("Pepito", Role.dc);
	}
	
	public Heroe getFallbackMarvel() {
		return new Heroe("Paco", Role.marvel);
	}
	


}
