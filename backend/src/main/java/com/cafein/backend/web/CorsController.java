package com.cafein.backend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorsController {

	@GetMapping("/cors")
	public String cors() {
		return "cors";
	}
}
