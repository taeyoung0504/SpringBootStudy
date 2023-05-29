package com.mysite.sbb;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
    
	@GetMapping("/api/username")
	@ResponseBody
	public String getUsername(Principal principal) {
		String username = principal.getName();
		return username;
		
	}
	@GetMapping("/admin/main")
	public String dd() {
		return "main";
	}
	
	 @GetMapping("/page1")
	    public String page1() {
	        return "page1";
	    }
}