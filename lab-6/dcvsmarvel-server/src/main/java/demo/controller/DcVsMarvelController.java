package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.service.DcVsMarvelService;



@Controller
public class DcVsMarvelController {

	@Autowired DcVsMarvelService dcVsMarvelService;
	
	
	/**
	 * Display a small list of Sentences to the caller:
	 */
	@RequestMapping("/dcvsmarvel")
	public @ResponseBody String getSentences() {
	  return 
		"<h3>Algunos heroes</h3><br/>" +	  
		dcVsMarvelService.buildSentence() + "<br/><br/>" +
		dcVsMarvelService.buildSentence() + "<br/><br/>"
		;
	}

}
