package jmaster.io.restapi.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}
