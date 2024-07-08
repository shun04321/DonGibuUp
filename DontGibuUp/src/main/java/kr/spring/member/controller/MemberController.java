package kr.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
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
import kr.spring.util.AuthCheckException;
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
	 * 			회원가입
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
		
		return "resultPage";
	}
	
	/*===================================
	 * 			로그인
	 *==================================*/
	//로그인폼
	@GetMapping("/member/login")
	public String loginForm() {
		return "memberLogin";
	}
	
	//로그인
	@PostMapping("/member/login")
	public String login(@Valid MemberVO memberVO, BindingResult result, HttpSession session) {
	    log.debug("<<회원 로그인>> : {}", memberVO);
	    
	    // 유효성 체크 결과 오류가 있으면 폼 호출
	    if (result.hasFieldErrors("mem_email") || result.hasFieldErrors("mem_pw")) {
	        return loginForm();
	    }
	    
	    try {
	        MemberVO member = memberService.selectMemberByEmail(memberVO.getMem_email());
	        
	     // =====TODO 로그인타입 체크 ====//
	        if (member != null) {
	            // 멤버 email이 존재할 시 status가 정지회원, 탈퇴회원인지 체크
	            if (member.getMem_status() == 1) { // 정지회원
	                result.reject("suspendedMember");
	            }
	            
	            // 비밀번호 일치여부 체크
	            if (member.isCheckedPassword(memberVO.getMem_pw())) {
	                // 인증 성공
	        		// =====TODO 자동로그인 체크 시작====//
	        		// =====TODO 자동로그인 체크 끝====//
	            	
	            	// 로그인 처리
	                session.setAttribute("user", member);
	                
	                log.debug("<<인증 성공>>");
	                log.debug("<<email>> : {}", member.getMem_email());
	                log.debug("<<status>> : {}", member.getMem_status());
	                log.debug("<<reg_type>> : {}", member.getMem_reg_type());
	                
	                if (member.getMem_status() == 9) { // 관리자
	                    return "redirect:/main/admin";
	                } else {
	                    return "redirect:/main/main";
	                }
	            }
	        }
	        
	        // 인증 실패 시 예외 던지기
	        throw new AuthCheckException();
	    } catch (AuthCheckException e) {
	        result.reject("invalidEmailOrPassword");
	        log.debug("<<인증 실패>>");
	        return loginForm();
	    }
	}
	
	//로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		// =====TODO 자동로그인 체크 시작====//
		// =====TODO 자동로그인 체크 끝====//

		return "redirect:/main/main";
	}
}
