package com.yuf.velocity.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RestController
public class IndexController {
	
	@RequestMapping(value={"/vm"})
	public String news(){
		return "news";
	}
	
	@RequestMapping(value={"/index"})
	public String hello(ModelMap map){
		map.addAttribute("host","http://blog.didispace.com");
		return "index";
	}
	
}
