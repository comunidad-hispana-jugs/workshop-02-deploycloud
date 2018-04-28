package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HeroeController {

	@Value("${heroes}") String heroes;
	
	@RequestMapping("/")
	public @ResponseBody String getHeroe() {
		String[] heroeArray = heroes.split(",");
		int i = (int)Math.round(Math.random() * (heroeArray.length - 1));
		return heroeArray[i];
	}
}
