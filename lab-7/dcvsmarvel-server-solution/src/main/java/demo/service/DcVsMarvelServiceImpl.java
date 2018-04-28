package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Build a sentence by assembling randomly generated heroes.  The individual parts of speech will 
 * be obtained by calling the various DAOs.
 */
@Service
public class DcVsMarvelServiceImpl implements DcVsMarvelService {

	@Autowired HeroeService heroeService;
	

	/**
	 * Assemble a sentence by gathering random words of each part of speech:
	 */
	public String buildSentence() {
		return  
			String.format("%s %s.",
					heroeService.getMarvel().getString(),
					heroeService.getDc().getString() )
			;
	}	
}
