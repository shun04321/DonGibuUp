package kr.spring.member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.config.validation.ValidationSequence;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	//자바빈 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}

	/*===================================
	 * 			게시판 글쓰기
	 *==================================*/
	//회원가입 폼
	@GetMapping("/member/signup")
	public String signupForm() {
		return "memberSignup";
	}
	
	//회원가입
	@PostMapping("/member/signup")
	public String signup(@Validated(ValidationSequence.class) MemberVO memberVO, BindingResult result, Model model, HttpServletRequest request) {
		log.debug("<<회원가입>> : " + memberVO);
		
		if (result.hasErrors()) {
			return signupForm();
		}
		
		memberService.insertMember(memberVO);
		
		model.addAttribute("accessTitle", "회원가입 완료");
		model.addAttribute("accessMsg", "회원가입이 완료되었습니다");
		model.addAttribute("accessBtn", "로그인하기");
		model.addAttribute("accessUrl", request.getContextPath() + "/member/login");
		
		log.debug("뷰호출 직전");
		
		return "resultPage";
	}
}
