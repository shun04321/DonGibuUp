package kr.spring.challenge.controller;

import java.io.IOException;

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
	//챌린지 개설 폼에서 전송된 데이터 처리
	@PostMapping("/challenge/write")
	public String submit(@Valid ChallengeVO challengeVO,BindingResult result, 
			HttpServletRequest request, HttpSession session,Model model) throws IllegalStateException, IOException {
		log.debug("<<챌린지 개설 확인>> : "+challengeVO);
		
		//유효성 체크
		if(result.hasErrors()) {
			return form();
		}
		
		//데이터 추가
		//회원번호
		MemberVO member = (MemberVO) session.getAttribute("user");
		challengeVO.setMem_num(member.getMem_num());
		//ip
		challengeVO.setChal_ip(request.getRemoteAddr());
		//대표 사진 업로드 및 파일 저장
		challengeVO.setChal_photo(FileUtil.createFile(request, challengeVO.getUpload()));
		
		//챌린지 개설
		challengeService.insertChallenge(challengeVO);
		
		//view에 메시지 추가
		model.addAttribute("message", "챌린지 개설이 완료되었습니다!");
		model.addAttribute("url", request.getContextPath()+"/challenge/list");
		
		return "common/resultAlert";
	}
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		//true이면 요청 파라미터의 값이 null이거나 빈 문자열("")일때 변환 처리를 하지 않고 null 값으로 할당.
		//false이면 변환 과정에서 에러가 발생하고 에러 코드로 "typeMismatch"가 추가됨
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class,false));
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
		
		ChallengeVO challenge = challengeService.selectChallenge(chal_num);
		
		return new ModelAndView("challengeView","challenge",challenge);
	}
}
