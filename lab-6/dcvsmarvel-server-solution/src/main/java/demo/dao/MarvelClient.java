package demo.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.domain.Heroe;

@FeignClient("MARVEL")
public interface MarvelClient {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public Heroe getHeroe();

	
	static class HystrixClientFallback implements MarvelClient {

		@Override

		public Heroe getHeroe() {

		return new Heroe();

		}	
	}	
}
