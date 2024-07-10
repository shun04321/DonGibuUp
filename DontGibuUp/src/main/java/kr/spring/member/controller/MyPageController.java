package kr.spring.member.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
public class MyPageController {
	@Autowired
	MemberService memberService;
	
	//자바빈 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	@GetMapping("/member/myPage")
	public String myPage() {
		return "redirect:/member/myPage/memberInfo";
	}
	
	//회원정보 수정 폼
	@GetMapping("/member/myPage/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		MemberVO member = memberService.selectMemberDetail(user.getMem_num());

		model.addAttribute("member", member);
		return "memberInfo";
	}
	
	//회원정보 수정
	@PostMapping("/member/myPage/updateMember")
	public String updateMemberInfo(@Validated(ValidationSequence.class) MemberVO memberVO, BindingResult result, HttpSession session, Model model) {
		log.debug("<<회원정보 수정>> : " + memberVO);

		if (result.hasErrors()) {
			return memberInfo(session, model);
		}
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		memberVO.setMem_num(user.getMem_num());
		memberService.updateMember(memberVO);
		
		return "memberInfo";
	}
	
	//비밀번호 수정 폼
	@GetMapping("/member/myPage/changePassword")
	public String changePasswordForm() {
		return "memberChangePassword";
	}
}
