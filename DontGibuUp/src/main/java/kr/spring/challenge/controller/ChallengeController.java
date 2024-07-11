package kr.spring.challenge.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
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
	//챌린지 개설 폼에서 전송된 데이터 유효성 검사 및 결제창 이동
	@PostMapping("/challenge/write")
	public String checkValidation(@Valid ChallengeVO challengeVO,BindingResult result, 
			HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
		log.debug("<<챌린지 개설 정보 확인>> : "+challengeVO);
		
		//유효성 체크
		if(result.hasErrors()) {
			return form();
		}
		challengeVO.calculateChalEdate();
		
		session.setAttribute("challengeVO", challengeVO);
		
						
		//return "redirect:/challenge/챌린지 결제url";
		return "main";
	}
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class,false));
	}
    
    //챌린지 개설 및 결제 처리하기
    
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
		
		ChallengeVO challenge = challengeService.selectChallenge(chal_num);
		
		return new ModelAndView("challengeView","challenge",challenge);
	}
}
