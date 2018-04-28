package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DcVsMarvelController {

	@RequestMapping("/")
	public String sentence() {
		return "dcvsmarvel";
	}
}
