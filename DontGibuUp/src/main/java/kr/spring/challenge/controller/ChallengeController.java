package kr.spring.challenge.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeJoinVO;
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
	
    /*==========================
     *  챌린지 참가 폼
     *==========================*/
    @GetMapping("/challenge/join")
    public String joinForm(@RequestParam("chal_num") long chal_num, Model model) {
        ChallengeJoinVO challengeJoinVO = new ChallengeJoinVO();
        challengeJoinVO.setChal_num(chal_num);

        ChallengeVO challengeVO = challengeService.selectChallenge(chal_num);
        List<DonationCategoryVO> categories = challengeService.selectDonaCategories();
        
        model.addAttribute("challengeJoinVO", challengeJoinVO);
        model.addAttribute("categories", categories);
        model.addAttribute("challengeVO", challengeVO);
        
        return "challengeJoinForm";
    }

    /*==========================
     *  챌린지 참가 및 결제
     *==========================*/
    @PostMapping("/challenge/join")
    public String join(@Valid ChallengeJoinVO challengeJoinVO, BindingResult result,
                       HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
        log.debug("<<챌린지 신청 확인>> : " + challengeJoinVO);

        // 유효성 체크
        if (result.hasErrors()) {
            return "challengeJoinForm";
        }

        // 데이터 추가
        // 회원번호
        MemberVO member = (MemberVO) session.getAttribute("user");
        challengeJoinVO.setMem_num(member.getMem_num());
        // ip
        challengeJoinVO.setChal_joi_ip(request.getRemoteAddr());

        // 챌린지 신청
        challengeService.insertChallengeJoin(challengeJoinVO);

        // view에 메시지 추가
        model.addAttribute("message", "챌린지 신청이 완료되었습니다!");
        model.addAttribute("url", request.getContextPath() + "/challenge/list");

        return "common/resultAlert";
    }

    /*==========================
     *  챌린지 참가 목록
     *==========================*/
    @GetMapping("/challenge/joinList")
    public ModelAndView joinList(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("user");

        Map<String, Object> map = new HashMap<>();
        map.put("mem_num", member.getMem_num());

        List<ChallengeJoinVO> list = challengeService.selectChallengeJoinList(map);

        return new ModelAndView("challengeJoinList", "list", list);
    }

    /*==========================
     *  챌린지 참가 상세
     *==========================*/
    @GetMapping("/challenge/joinDetail")
    public ModelAndView joinDetail(@RequestParam("chal_joi_num") Long chal_joi_num) {
        ChallengeJoinVO challengeJoin = challengeService.selectChallengeJoin(chal_joi_num);
        return new ModelAndView("challengeJoinView", "challengeJoin", challengeJoin);
    }
}
