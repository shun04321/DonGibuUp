package kr.spring.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.notify.service.NotifyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@Autowired
	NotifyService notifyService;
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	@GetMapping("/main/main")
	public String main() {
		
		return "main";//Tiles의 설정명
	}
	@GetMapping("/main/admin")
	public String adminMain() {
		return "admin";//Tiles의 설정명
	}
}








