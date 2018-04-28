package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import demo.dao.DcClient;
import demo.dao.MarvelClient;


/**
 * Build a sentence by assembling randomly generated heroes.  The individual parts of speech will 
 * be obtained by calling the various DAOs.
 */
@Service
public class DcVsMarvelServiceImpl implements DcVsMarvelService {

	DcClient dcService;
	MarvelClient marvelService;
	
	

	/**
	 * Assemble a sentence by gathering random heroes of each organization:
	 */
	public String buildSentence() {
		String sentence = "Hay un problema obteniendo la lista de heroes!";
		sentence =  
			String.format("%s %s %s %s %s.",
					dcService.getHeroe().getString(),
					marvelService.getHeroe().getString() );
		return sentence;
	}

	@Autowired
	public void setDcService(DcClient dcService) {
		this.dcService = dcService;
	}

	@Autowired
	public void setMarvelService(MarvelClient marvelService) {
		this.marvelService = marvelService;
	}


	
}
