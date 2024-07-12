package kr.spring.member.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.config.validation.ValidationGroups.PatternCheckGroup;
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
		MemberVO user = (MemberVO) session.getAttribute("user");

		MemberVO memberVO = memberService.selectMemberDetail(user.getMem_num());

		if (memberVO.getMem_phone() != null) {
			model.addAttribute("phone2", memberVO.getMem_phone().substring(3, 7));
			model.addAttribute("phone3", memberVO.getMem_phone().substring(7, 11));
			log.debug("<<phone>> : " + memberVO.getMem_phone().substring(3, 7)
					+ memberVO.getMem_phone().substring(7, 11));
		}

		if (memberVO.getMem_birth() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate parsedDate = LocalDate.parse(memberVO.getMem_birth(), formatter);

			model.addAttribute("birth_year", parsedDate.getYear());
			model.addAttribute("birth_month", parsedDate.getMonthValue());
			model.addAttribute("birth_day", parsedDate.getDayOfMonth());
		}

		model.addAttribute("memberVO", memberVO);
		return "memberInfo";
	}

	//회원정보 수정
	@PostMapping("/member/myPage/updateMember")
	public String updateMemberInfo(@Valid MemberVO memberVO, BindingResult result,
			HttpSession session, Model model) {
		if (memberVO.getMem_phone().equals("010")) {
			memberVO.setMem_phone(null);
		}
		

		MemberVO user = (MemberVO) session.getAttribute("user");

		memberVO.setMem_num(user.getMem_num());
		memberVO.setMem_email(user.getMem_email());
		
		log.debug("<<회원정보 수정>> : " + memberVO);
		
		//닉네임 유효성 검사
		if (memberVO.getMem_nick()=="" || memberVO.getMem_nick() == null) {
			result.rejectValue("mem_nick", "NotBlank.mem_nick");
		}
		//phone 유효성 검사
		if (memberVO.getMem_phone() != null && !memberVO.getMem_phone().isEmpty() && !memberVO.getMem_phone().matches("\\d{11}")) {
		    result.rejectValue("mem_phone", "InvalidFormat.mem_phone");
		}

		if (result.hasErrors()) {
			log.debug("<<에러>> : " + result.getAllErrors());
			return "memberInfo";
		}

		model.addAttribute("memberVO", memberVO);

		//회원정보 수정
		memberService.updateMember(memberVO);

		// 세션에 저장된 user 정보 업데이트
		user.setMem_nick(memberVO.getMem_nick());
		// 세션에 업데이트된 user 객체 저장
		session.setAttribute("user", user);

		//리디렉트할 모델 value 설정
		if (memberVO.getMem_phone() != null && !memberVO.getMem_phone().isEmpty()) {
			model.addAttribute("phone2", memberVO.getMem_phone().substring(3, 7));
			model.addAttribute("phone3", memberVO.getMem_phone().substring(7, 11));
			log.debug("<<phone>> : " + memberVO.getMem_phone().substring(3, 7)
					+ memberVO.getMem_phone().substring(7, 11));
		}

		if (memberVO.getMem_birth() != null && !memberVO.getMem_birth().isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate parsedDate = LocalDate.parse(memberVO.getMem_birth(), formatter);

			model.addAttribute("birth_year", parsedDate.getYear());
			model.addAttribute("birth_month", parsedDate.getMonthValue());
			model.addAttribute("birth_day", parsedDate.getDayOfMonth());
		}

		return "memberInfo";
	}

	//비밀번호 수정 폼
	@GetMapping("/member/myPage/changePassword")
	public String changePasswordForm() {
		return "memberChangePassword";
	}

	//비밀번호 수정
	@PostMapping("/member/myPage/changePassword")
	public String changePassword(@Validated(PatternCheckGroup.class) MemberVO memberVO, BindingResult result,
			Model model, HttpServletRequest request, HttpSession session) {
		
		log.debug("<<비밀번호 수정>> : " + memberVO);

		if (result.hasErrors()) {
			log.debug("<<에러>> : " + result.getAllErrors());
			return "memberChangePassword";
		}
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		
		memberService.updatePassword(memberVO);
		
		model.addAttribute("accessTitle", "비밀번호 변경 완료");
		model.addAttribute("accessMsg", "비밀번호 변경이 완료되었습니다");
		model.addAttribute("accessBtn", "홈으로");
		model.addAttribute("accessUrl", request.getContextPath() + "/main/main");
		
		return "changePasswordResultPage";
	}

	//친구초대이벤트 페이지
	@GetMapping("/member/myPage/inviteFriendEvent")
	public String inviteFriendEvent(HttpSession session, Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		MemberVO member =  memberService.selectMemberDetail(user.getMem_num());
		
		//모델에 rcode 담아보내기
		model.addAttribute("rcode", member.getMem_rcode());
		
		return "inviteFriendEvent";
	}
}
