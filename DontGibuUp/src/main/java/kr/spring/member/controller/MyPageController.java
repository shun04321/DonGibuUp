package kr.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyPageController {
	//자바빈 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	@GetMapping("/member/myPage")
	public String myPage() {
		return "redirect:/member/myPage/memberInfo";
	}
	
	//회원정보 수정
	@GetMapping("/member/myPage/memberInfo")
	public String memberInfo() {
		return "memberInfo";
	}
	
	//비밀번호 수정
	@GetMapping("/member/myPage/changePassword")
	public String changePasswordForm() {
		return "memberChangePassword";
	}
}
