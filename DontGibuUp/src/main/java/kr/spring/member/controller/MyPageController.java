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
		
		MemberVO memberVO = memberService.selectMemberDetail(user.getMem_num());

		model.addAttribute("memberVO", memberVO);
		return "memberInfo";
	}
	
	//회원정보 수정
	@PostMapping("/member/myPage/updateMember")
	public String updateMemberInfo(@Validated(ValidationSequence.class) MemberVO memberVO, BindingResult result, HttpSession session, Model model) {
		
		if (memberVO.getMem_phone().equals("010")) {
			memberVO.setMem_phone(null);
		}

		log.debug("<<회원정보 수정>> : " + memberVO);
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		memberVO.setMem_num(user.getMem_num());
		memberVO.setMem_email(user.getMem_email());
		
		model.addAttribute("memberVO", memberVO);

		if (result.hasFieldErrors("mem_phone")) {
			log.debug("<<회원정보 수정 유효성검사 실패>> : "+ result.getAllErrors());
			return "memberInfo";
		}
//		if (result.hasFieldErrors("mem_nick")) {
//			result.rejectValue("mem_nick", "닉네임을 입력해주세요");
//			return "memberInfo";
//		}
//		if (result.hasFieldErrors("mem_phone")) {
//			return "memberInfo";
//		}
//		if (result.hasFieldErrors("mem_birth")) {
//			return "memberInfo";
//		}
		memberService.updateMember(memberVO);
		return "memberInfo";
	}
	
	//비밀번호 수정 폼
	@GetMapping("/member/myPage/changePassword")
	public String changePasswordForm() {
		return "memberChangePassword";
	}
}
