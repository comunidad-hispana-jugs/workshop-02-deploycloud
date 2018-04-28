package demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.client.RestTemplate;

import demo.domain.Heroe;


public abstract class HeroeDaoImpl implements HeroeDao {

	@Autowired LoadBalancerClient loadBalancer;
	
	public abstract String getHeroes();
	
	public Heroe getHeroe() {
		ServiceInstance instance = loadBalancer.choose(getHeroes());
   		return (new RestTemplate()).getForObject(instance.getUri(),Heroe.class);
	}
	
}
