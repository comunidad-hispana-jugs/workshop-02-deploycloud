package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.service.DcVsMarvelService;



@Controller
public class DcVsMarvelController {

	@Autowired DcVsMarvelService sentenceService;
	
	
	/**
	 * Display a small list of Sentences to the caller:
	 */
	@RequestMapping("/dcvsmarvel")
	public @ResponseBody String getSentence() {
	  return 
		"<h3>FIGHT</h3><br/>" +	  
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>"
		;
	}

}
