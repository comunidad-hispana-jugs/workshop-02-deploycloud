package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dao.HeroeDao;

/**
 * Build a sentence by assembling randomly generated heroes.  The individual parts of heroes will 
 * be obtained by calling the various DAOs.
 */
@Service
public class DcVsMarvelServiceImpl implements DcVsMarvelService {

	private HeroeDao marvelService;
	private HeroeDao dcService;
	

	/**
	 * Assemble a sentence by gathering random words of each part of speech:
	 */
	public String buildSentence() {
		String sentence = "Hay un problema obteniendo la lista de heroes!";
		sentence =  
			String.format("%s %s %s %s %s.",
					marvelService.getHeroe().getString(),
					dcService.getHeroe().getString() );
		return sentence;
	}


	@Autowired
	public void setMarvelService(HeroeDao marvelService) {
		this.marvelService = marvelService;
	}


	@Autowired
	public void setDcService(HeroeDao dcService) {
		this.dcService = dcService;
	}	
	
	
}
