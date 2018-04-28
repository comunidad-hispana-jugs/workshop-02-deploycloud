package demo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;



@Controller
public class DcVsMarvelController {

	@Autowired DiscoveryClient client;
	
	@RequestMapping("/dcvsmarvel")
	public @ResponseBody String getSentence() {
	  return String.format("[DC] %s vs [MARVEL] %s.",
		  getWord("LAB-4-DC"),
		  getWord("LAB-4-MARVEL") );
	}

	public String getWord(String service) {
        List<ServiceInstance> list = client.getInstances(service);
        if (list != null && list.size() > 0 ) {
      	URI uri = list.get(0).getUri();
	      	if (uri !=null ) {
	      		return (new RestTemplate()).getForObject(uri,String.class);
	      	}
        }
        return null;
	}

}
