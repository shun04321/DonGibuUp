package kr.spring.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChallengeController {
	@Autowired
	private ChallengeService challengeService;
	
	@ModelAttribute
	public ChallengeVO initCommand() {
		return new ChallengeVO();
	}
	/*==========================
	 *  챌린지 개설하기
	 *==========================*/
	//챌린지 개설 폼 불러오기
	@GetMapping("/challenge/write")
	public String form() {
		return "challengeWrite";
	}
	
	/*==========================
	 *  챌린지 목록
	 *==========================*/
	@GetMapping("/challenge/list")
	public String list() {
		return "challengeList";
	}
	
	/*==========================
	 *  챌린지 상세
	 *==========================*/
	@GetMapping("/challenge/detail")
	public ModelAndView chalDetail(long chal_num) {
		
		return new ModelAndView("challengeView");
	}
}
