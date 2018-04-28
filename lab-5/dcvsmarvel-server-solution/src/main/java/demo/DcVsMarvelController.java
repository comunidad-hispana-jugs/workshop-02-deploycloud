package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;



@Controller
public class DcVsMarvelController {

	//	This is referencing the RestTemplate we defined earlier:
	@Autowired  RestTemplate template;
	
	/**
	 * Display a small list of Sentences to the caller:
	 */
	@RequestMapping("/dcvsmarvel")
	public @ResponseBody String getSentence() {
	  return 
		"<h3>Algunos heroes</h3><br/>" +	  
		buildSentence() + "<br/><br/>" +
		buildSentence() + "<br/><br/>"
		;
	}

	/**
	 * Assemble a sentence by gathering random heroes of each part of speech:
	 */
	public String buildSentence() {
		String sentence = "Hay problemas para obtener la lista de heroes!";
		try{
			sentence =  
				String.format("%s %s %s %s %s.",
					getHeroe("MARVEL"),
					getHeroe("DC") );			
		} catch ( Exception e ) {
			System.out.println(e);
		}
		return sentence;
	}
	
	/**
	 * Obtain a random word for a given part of speech, where the part 
	 * of speech is indicated by the given service / client ID:
	 */
	public String getHeroe(String service) {
		return template.getForObject("http://" + service, String.class);
	
	}

}
