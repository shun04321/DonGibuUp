package kr.spring.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.data.service.DataService;
import kr.spring.data.vo.TotalVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@Autowired
	DataService dataService;
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	@GetMapping("/main/main")
	public String main(Model model) {
		TotalVO totalVO = dataService.selectTotalMain();
		
		model.addAttribute("totalVO", totalVO);
		
		return "main";//Tiles의 설정명
	}
	@GetMapping("/main/admin")
	public String adminMain() {
		return "admin";//Tiles의 설정명
	}
	
	@GetMapping("/main/main2")
	public String main2() {
		
		return "main2";//Tiles의 설정명
	}
}








