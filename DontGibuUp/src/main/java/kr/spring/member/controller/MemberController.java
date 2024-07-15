package kr.spring.member.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.config.validation.ValidationSequence;
import kr.spring.member.service.MemberOAuthService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.KakaoInfo;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.AuthCheckException;
import kr.spring.util.KakaoURLParameterExtractor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberOAuthService memberOAuthService;

	//카카오 로그인 API 정보
	@Value("${kakao.client_id}")
	private String client_id;
	@Value("${kakao.redirect_uri}")
	private String redirect_uri;
	@Value("${kakao.client_secret}")
	private String client_secret;

	//자바빈 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}

	/*===================================
	 * 			회원가입
	 *==================================*/
	//일반 회원가입 폼
	@GetMapping("/member/signup")
	public String signupForm(@RequestParam(value = "rcode", required = false) String rcode, HttpSession session) {
		session.setAttribute("rcode", rcode);
		return "memberSignup";
	}
	
	//카카오 회원가입 폼
	@GetMapping("/member/signup/kakao")
	public String signupFormKakao(HttpSession session, Model model) {
		MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
		
		if (memberVO == null) {
			return "redirect:/member/signup";
		} else {
			model.addAttribute("memberVO", memberVO);
			return "memberSocialSignup";
		}
	}

	//일반 회원가입
	@PostMapping("/member/signup")
	public String signup(@Validated(ValidationSequence.class) MemberVO memberVO, BindingResult result, Model model,
			HttpServletRequest request, HttpSession session) {
		log.debug("<<회원가입>> : " + memberVO);

		if (result.hasErrors()) {
			log.debug("<<에러>> : " + result.getAllErrors());
			return "memberSignup";
		}
		//회원가입 타입 지정(1:일반 회원가입)
		memberVO.setMem_reg_type(1);
		
		//추천인 이벤트 참가
		if (memberVO.getFriend_rcode() != null && !memberVO.getFriend_rcode().equals("")) {
			//추천인 이벤트 참여
			if (memberService.selectMemNumByRCode(memberVO.getFriend_rcode()) != null) {
				memberVO.setRecommend_status(1);
			} else {
	            result.rejectValue("friend_rcode", "invalidRCode");
	            return "memberSignup"; // 회원가입 폼 다시 보여주기
			}
		} else {
			//미참여
			memberVO.setRecommend_status(0);
		}
		
		//이메일,닉네임 UK 체크
		if (memberService.selectMemberByEmail(memberVO.getMem_email()) != null) {
			result.rejectValue("mem_email", "emailExists");
			return "memberSignup";
		}
		if (memberService.selectMemberByNick(memberVO.getMem_nick()) != null) {
			result.rejectValue("mem_nick", "nickExists");
			return "memberSignup";
		}

		//회원가입
		memberService.insertMember(memberVO);
		session.invalidate(); //추천인코드 초기화

		model.addAttribute("accessTitle", "회원가입 완료");
		model.addAttribute("accessMsg", "회원가입이 완료되었습니다");
		model.addAttribute("accessBtn", "로그인하기");
		model.addAttribute("accessUrl", request.getContextPath() + "/member/login");

		return "signupResultPage";
	}
	
	//카카오 회원가입
	@PostMapping("/member/signup/kakao") 
	public String signupKakao(@Valid MemberVO memberVO, BindingResult result, Model model, HttpServletRequest request, HttpSession session) {
		memberVO.setMem_email(request.getParameter("mem_email"));
		memberVO.setMem_social_id(Long.parseLong(request.getParameter("mem_social_id")));
		memberVO.setMem_reg_type(3); // 회원가입 타입 지정
        memberVO.setMem_pw("N"); // 비밀번호 임의 지정
        
		//추천인 이벤트 참가
		if (memberVO.getFriend_rcode() != null && !memberVO.getFriend_rcode().equals("")) {
			//추천인 이벤트 참여
			if (memberService.selectMemNumByRCode(memberVO.getFriend_rcode()) != null) {
				memberVO.setRecommend_status(1);
			} else {
	            result.rejectValue("friend_rcode", "invalidRCode");
	            return "memberSocialSignup"; // 회원가입 폼 다시 보여주기
			}
		} else {
			//미참여
			memberVO.setRecommend_status(0);
		}
		//이메일,닉네임 UK 체크
		if (memberService.selectMemberByEmail(memberVO.getMem_email()) != null) {
			result.rejectValue("mem_email", "emailExists");
			return "memberSignup";
		}
		if (memberService.selectMemberByNick(memberVO.getMem_nick()) != null) {
			result.rejectValue("mem_nick", "nickExists");
			return "memberSignup";
		}
        
        log.debug("<<회원가입>> : " + memberVO);

        // 회원가입 처리
        memberService.insertMember(memberVO);
        session.invalidate(); //추천인코드 초기화
        
        // 메인으로 redirect
		model.addAttribute("accessTitle", "회원가입 완료");
		model.addAttribute("accessMsg", "회원가입이 완료되었습니다");
		model.addAttribute("accessBtn", "로그인하기");
		model.addAttribute("accessUrl", request.getContextPath() + "/member/login");

		return "signupResultPage";
	}
	

	//카카오 로그인/회원가입 api
	@GetMapping("/member/oauth/kakao")
	public String getKakaoLogin(String rcode) {
		String url = String.format(
				"https://kauth.kakao.com/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s", client_id,
				redirect_uri);
		return "redirect:" + url;
	}

	//카카오 로그인/회원가입 api 콜백
	@GetMapping("/member/callback/kakao")
	public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			// Access Token 획득
			String accessToken = memberOAuthService.getKakaoAccessToken(code);
			KakaoInfo kakaoMember = memberOAuthService.getKakaoInfo(accessToken);

			log.debug("<<카카오 로그인 정보>> : " + kakaoMember);

			// 이메일을 통해 기존 회원 정보 조회
			MemberVO existingMember = memberService.selectMemberByEmail(kakaoMember.getEmail());

			String redirectUrl;


			if (existingMember != null) { // 기존 회원이 존재하는 경우
				if (existingMember.getMem_status() == 1) { // 정지 회원인 경우
					redirectAttributes.addFlashAttribute("error", "정지된 회원입니다");
					redirectUrl = "/member/login";
				} else if (existingMember.getMem_reg_type() == 1) { //일반회원가입된 이메일일 경우
					redirectAttributes.addFlashAttribute("error", "일반 회원가입된 이메일입니다");
					redirectUrl = "/member/login";
				} else if (existingMember.getMem_reg_type() == 2) { //네이버 회원가입된 이메일일 경우
					redirectAttributes.addFlashAttribute("error", "네이버로 회원가입된 이메일입니다");
					redirectUrl = "/member/login";
				} else { // 정상 회원인 경우
					// TODO: 자동 로그인 체크 로직 추가

					// 로그인 처리
					session.setAttribute("user", existingMember);
					session.setMaxInactiveInterval(60 * 30); //30분
					session.setAttribute("kakaoToken", accessToken);

					log.debug("<<인증 성공>>");
					
					log.debug("<<photo>> : {}", existingMember.getMem_photo());
					log.debug("<<email>> : {}", existingMember.getMem_email());
					log.debug("<<status>> : {}", existingMember.getMem_status());
					log.debug("<<reg_type>> : {}", existingMember.getMem_reg_type());

					if (existingMember.getMem_status() == 9) { // 관리자
						redirectUrl = "/main/admin";
					} else { // 일반 회원
						redirectUrl = "/main/main";
					}
				}

			} else { // 기존 회원이 없는 경우 회원가입 폼으로 이동

				MemberVO memberVO = new MemberVO();
				memberVO.setMem_email(kakaoMember.getEmail());
				memberVO.setMem_social_id(kakaoMember.getId());
				
				log.debug("<<카카오 email>> : " + memberVO.getMem_email());

				//회원가입 폼으로 redirect
				session.setAttribute("memberVO", memberVO);
				redirectUrl = "/member/signup/kakao";
			}
			

			// 리다이렉트 URL을 반환
			return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, redirectUrl).body(null);

		} catch (Exception e) {
			// 예외 처리 로직: 에러 페이지로 리다이렉트
			return new ResponseEntity<>("Exception occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*===================================
	 * 			로그인
	 *==================================*/
	//로그인폼
	@GetMapping("/member/login")
	public String loginForm() {
		return "memberLogin";
	}

	//일반 로그인
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
					return loginForm();
				}

				// 비밀번호 일치여부 체크
				if (memberService.isCheckedPassword(member, memberVO.getMem_pw())) {
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
		String accessToken = (String) session.getAttribute("kakaoToken");
		if (accessToken != null && !"".equals(accessToken)) { //소셜(카카오) 로그인 
			try {
				// =====TODO 자동로그인 체크 시작====//
				// =====TODO 자동로그인 체크 끝====//
				memberOAuthService.kakaoDisconnect(accessToken);
			} catch (Exception e) {
				// 예외 처리
				return "Exception occurred: " + e.getMessage();
			}
		}

		//로그아웃
		session.invalidate();

		return "redirect:/main/main";
	}
}
