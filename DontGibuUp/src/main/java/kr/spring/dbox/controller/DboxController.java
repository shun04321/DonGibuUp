package kr.spring.dbox.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.ChallengeCategoryVO;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.dbox.service.DboxService;
import kr.spring.dbox.vo.DboxBudgetVO;
import kr.spring.dbox.vo.DboxDonationVO;
import kr.spring.dbox.vo.DboxResultVO;
import kr.spring.dbox.vo.DboxVO;
import kr.spring.dbox.vo.DboxValidationGroup_2;
import kr.spring.dbox.vo.DboxValidationGroup_3;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DboxController {
	@Autowired
	private DboxService dboxService;
	@Autowired
	private CategoryService categoryService;

	//자바빈 초기화
	@ModelAttribute
	public DboxVO initCommand() {
		return new DboxVO();
	}
	/*===================================
	 * 		기부박스 목록
	 *==================================*/
    @GetMapping("/dbox/list")
    public String list() {
    	log.debug("<<목록접속>> : ");
        return "dboxList";
    }
    /*===================================
     * 		기부박스 상세페이지 - content
     *==================================*/
    @GetMapping("/dbox/{dboxNum}/content")
    public String detailContent(@PathVariable long dboxNum,Model model) {
    	log.debug("<<상세 페이지(content)>> : " + dboxNum);
    	
    	//기부박스 및 모금계획 불러오기
    	DboxVO dbox = dboxService.selectDbox(dboxNum);
    	List<DboxBudgetVO> dboxBudget = dboxService.selectDboxBudgets(dboxNum);
    	log.debug("<<상세 페이지(content) - Dbox : >>" + dbox);
    	log.debug("<<상세 페이지(content) - DboxBudget : >>" + dboxBudget);
   
    	//뷰에 전달
    	model.addAttribute("dbox",dbox);
    	model.addAttribute("dboxBudget",dboxBudget);
    	return "dboxDetailContent";
    }
    /*===================================
     * 		기부박스 상세페이지 - donators
     *==================================*/
    @GetMapping("/dbox/{dboxNum}/donators")
    public String detailDonators(@PathVariable long dboxNum,Model model) {
    	log.debug("<<상세 페이지 접속(donators)>> : " + dboxNum);
    	
    	//기부박스 및 모금계획 불러오기
    	DboxVO dbox = dboxService.selectDbox(dboxNum);
    	List<DboxBudgetVO> dboxBudget = dboxService.selectDboxBudgets(dboxNum);
    	log.debug("<<상세 페이지(donators) - Dbox : >>" + dbox);
    	log.debug("<<상세 페이지(donators) - DboxBudget : >>" + dboxBudget);
   
    	
    	//기부현황
    	int count = dboxService.selectDboxDonationsCount(dboxNum);
		List<DboxDonationVO> dboxDonations = null;
//		if(count > 0) {
//			dboxDonations = dboxService.selectDboxDonations(dboxNum);
//		}else {
//			dboxDonations = Collections.emptyList();//null일 경우 빈 배열로 인식되게 세팅
//		}
    	log.debug("<<상세 페이지(donators) - DboxDonations : >>" + dboxDonations);
    	
    	//뷰에 전달
    	model.addAttribute("dboxDonations",dboxDonations);
    	model.addAttribute("dbox",dbox);
    	model.addAttribute("dboxBudget",dboxBudget);
    	return "dboxDetailDonators";
    }
    /*===================================
     * 		기부박스 상세페이지 - news
     *==================================*/
    @GetMapping("/dbox/{dboxNum}/news")
    public String detailNews(@PathVariable long dboxNum,Model model) {
    	log.debug("<<상세 페이지 접속(news)>> : " + dboxNum);
    	
    	//기부박스 및 모금계획 불러오기
    	DboxVO dbox = dboxService.selectDbox(dboxNum);
    	List<DboxBudgetVO> dboxBudget = dboxService.selectDboxBudgets(dboxNum);
    	log.debug("<<상세 페이지(news) - Dbox : >>" + dbox);
    	log.debug("<<상세 페이지(news) - DboxBudget : >>" + dboxBudget);
    	
    	//소식
    	DboxResultVO dboxResult = dboxService.selectDboxResult(dboxNum);
    	log.debug("<<상세 페이지(news) - DboxResult : >>" + dboxResult);
    	
    	//뷰에 전달
    	model.addAttribute("dboxNews",dboxResult);
    	model.addAttribute("dbox",dbox);
    	model.addAttribute("dboxBudget",dboxBudget);
    	return "dboxDetailNews";
    }
	
	
	/*===================================
	 * 		기부박스 제안하기
	 *==================================*/
	@GetMapping("/dbox/propose")
	public String proposeForm() {
		return "dboxPropose";
	}
	/*===================================
	 * 		기부박스 제안하기 : 1.나의 다짐
	 *==================================*/
	@GetMapping("/dbox/propose/step1")
	public String proposeStep1(HttpSession session,Model model) {
		//로그인체크
		MemberVO user = (MemberVO)session.getAttribute("user");
		log.debug("<<Step1 회원번호>> : " + user);
		if(user==null) {
			return "redirect:/member/login";
		}
		List<DonationCategoryVO> list = categoryService.selectListNoPage();
		
		model.addAttribute("list",list);
		
		return "dboxProposeStep1";		
	}
	@PostMapping("/dbox/propose/step1")
	public String Step1Submit(@Valid DboxVO dboxVO,
							  BindingResult result,
							  HttpSession session) {
		//dboxVO에 dcate_num이 담겼는지 확인
		log.debug("<<기부박스 제안 Step1>> : " + dboxVO);
		
		//로그인 체크
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			//로그인 안한 경우
			return "redirect:/member/login";
		}
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasFieldErrors("dcate_num")) {
			return "dboxProposeStep1";
		}

		
		//dboxVO를 세션에 "dbox"명칭으로 저장
		session.setAttribute("dbox", dboxVO);
		
		//세션에 저장된 dboxVO 확인
		log.debug("<<기부박스 제안 Step1 - 세션 저장>> : " + dboxVO);
		
		//다음페이지로 이동
		return "redirect:/dbox/propose/step2";
	}
	
	
	/*=======================================
	 * 		기부박스 제안하기 : 2.팀 및 계획 작성
	 *=======================================*/
	@GetMapping("/dbox/propose/step2")
	public String proposeStep2(HttpSession session) {
		//로그인 체크
		MemberVO user = (MemberVO)session.getAttribute("user");
		DboxVO dbox = (DboxVO)session.getAttribute("dbox");
		log.debug("<<Step2 회원정보>> : " + user);
		log.debug("<<Step2 세션에 저장된 dbox : >>" + dbox);
		if(user==null) {
			//로그인 안한 경우
			return "redirect:/member/login";
		}
		return "dboxProposeStep2";
	}
	@PostMapping("/dbox/propose/step2")
	public String Step2Submit(@Validated(DboxValidationGroup_2.class) DboxVO dboxVO,
							  BindingResult result,
							  HttpSession session,
							  HttpServletRequest request) throws IllegalStateException, IOException {
		//dboxVO에 dcate_num이 담겼는지 확인
		log.debug("<<기부박스 제안 Step2>> : " + dboxVO);
		
		//로그인 체크
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			//로그인 안한 경우
			return "redirect:/member/login";
		}
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
	        for (FieldError error : result.getFieldErrors()) {
	            System.out.println("Field: " + error.getField());
	            System.out.println("Error: " + error.getDefaultMessage());
	        }
			return "dboxProposeStep2";
		}
		//세션에 저장된 dbox 불러오기
		DboxVO s_dbox = (DboxVO)session.getAttribute("dbox"); 
		log.debug("<<기부박스 제안 Step2 - 세션에서 불러온 정보>> : " + s_dbox);
		
		//파일처리
		dboxVO.setDbox_team_photo(FileUtil.createFileDbox(request, dboxVO.getDbox_team_photo_file()));
		dboxVO.setDbox_business_plan(FileUtil.createFileDbox(request, dboxVO.getDbox_business_plan_file()));
		dboxVO.setDbox_budget_data(FileUtil.createFileDbox(request, dboxVO.getDbox_budget_data_file()));
		
		//dboxVO에 세션정보 넣기
		dboxVO.setDcate_num(s_dbox.getDcate_num());
		
		//dboxVO를 세션에 "dbox"명칭으로 저장
		session.setAttribute("dbox", dboxVO);
		
		//세션에 저장된 dboxVO 확인
		log.debug("<<기부박스 제안 Step3 - 세션 저장>> : " + dboxVO);
		
		//다음페이지로 이동
		return "redirect:/dbox/propose/step3";
	}
	
	/*===================================
	 * 		기부박스 제안하기 : 3.내용 작성 
	 *==================================*/
	@GetMapping("/dbox/propose/step3")
	public String proposeStep3(HttpSession session) {
		//로그인 체크
		MemberVO user = (MemberVO)session.getAttribute("user");
		DboxVO dbox = (DboxVO)session.getAttribute("dbox");
		log.debug("<<Step3 회원정보>> : " + user);
		log.debug("<<Step3 세션에 저장된 dbox : >>" + dbox);
		if(user==null) {
			//로그인 안한 경우
			return "redirect:/member/login";
		}
		return "dboxProposeStep3";
	}
	@PostMapping("/dbox/propose/step3")
	public String Step3Submit(@Validated(DboxValidationGroup_3.class) DboxVO dboxVO,
							  BindingResult result,
							  HttpSession session,
							  HttpServletRequest request ) throws IllegalStateException, IOException {
		//기부박스 제목, 대표이미지, 내용작성 체크
		log.debug("<<기부박스 제안 Step3>> : " + dboxVO);
		
		//로그인 체크
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			//로그인 안한 경우
			return "redirect:/member/login";
		}
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "dboxProposeStep3";
		}
		//세션에 저장된 dbox 불러오기
		DboxVO s_dbox = (DboxVO)session.getAttribute("dbox"); 
		log.debug("<<기부박스 제안 Step3 - 세션에서 불러온 정보>> : " + s_dbox);
		
		//회원번호 및 작성한 데이터 합치기
		s_dbox.setMem_num(user.getMem_num());
		s_dbox.setDbox_title(dboxVO.getDbox_title());
		s_dbox.setDbox_photo(dboxVO.getDbox_photo());
		s_dbox.setDbox_content(dboxVO.getDbox_content());
		
		//파일처리
		s_dbox.setDbox_photo(FileUtil.createFileDbox(request, dboxVO.getDbox_photo_file()));
		
		//제출되는 dboxVO 확인
		log.debug("<<기부박스 제안 Step3 - 제안 폼 제출>> : " + s_dbox);
		//Dbox 등록및 현재 등록번호 반환
		long current_dbox_num = dboxService.insertDbox(s_dbox);
		log.debug("<<기부박스 제안 Step3 - dbox번호>> : " + current_dbox_num);
		//제안완료 페이지로 dbox_num 전달
		session.setAttribute("dbox_num",current_dbox_num);
		//세션에서 제거
		session.removeAttribute("dbox");
		
		//다음페이지로 이동
		return "redirect:/dbox/propose/end";
	}
	/*===================================
	 * 		기부박스 제안하기 : 제안 완료
	 *==================================*/
	@GetMapping("/dbox/propose/end")
	public String proposeEnd(HttpSession session ,Model model) {
		long dbox_num = (long)session.getAttribute("dbox_num");
		
		log.debug("<<기부박스 제안하기 - end>> : " + dbox_num);
		//뷰에 dbox_num 전달
		model.addAttribute("dbox_num");
		
		return "dboxProposeEnd";
	}
	
	/*===================================
	 * 		기부박스 제안하기 : 예시
	 *==================================*/
	@GetMapping("/dbox/example")
	public String proposeExample() {
		log.debug("<<기부박스 제안하기 - example>> : ");
		
		return "dboxExample";
	}

	
}