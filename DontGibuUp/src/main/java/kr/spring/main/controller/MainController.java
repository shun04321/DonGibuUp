package kr.spring.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.data.service.DataService;
import kr.spring.data.vo.TotalVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@Autowired
	DataService dataService;
	@Autowired
	private ChallengeService challengeService;
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	//데이터, 인기 챌린지
	@GetMapping("/main/main")
	public String main(Model model) {
		TotalVO totalVO = dataService.selectTotalMain();
		List<ChallengeVO> popularChallenges = challengeService.getPopularChallenges();
		
		model.addAttribute("totalVO", totalVO);
		model.addAttribute("popularChallenges", popularChallenges);
		
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








