package kr.spring.subscription.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.config.validation.ValidationSequence;
import kr.spring.member.vo.MemberVO;
import kr.spring.payuid.service.PayuidService;
import kr.spring.payuid.vo.PayuidVO;
import kr.spring.subscription.service.SubscriptionService;
import kr.spring.subscription.vo.SubscriptionVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SubscriptionController {
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private PayuidService payuidService;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/subscription/subscriptionMain")
	public String subScriptionMain() {
		return "subscriptionMain";
	}
	
	@PostMapping("/category/registerSubscription")
    public String signup(@Validated(ValidationSequence.class) SubscriptionVO subscriptionVO,
						/* BindingResult result, */
    					 Model model,
                         HttpServletRequest request,
                         HttpSession session) {
		log.debug("정기기부 등록 subscriptionVO : "+subscriptionVO);
		
		//조건 체크 자바스크립트로 실행
		/*
		 * if (result.hasErrors()) { DonationCategoryVO category =
		 * categoryService.selectDonationCategory(subscriptionVO.getDcate_num());
		 * model.addAttribute("category", category); // 필요한 데이터 다시 추가
		 * model.addAttribute("subscriptionVO", subscriptionVO); // 폼 데이터를 다시 설정 return
		 * "categoryDetail"; // 에러가 있는 경우 다시 폼을 보여줌 }
		 */
		MemberVO user = (MemberVO)session.getAttribute("user");
		
		/*
		 * if(payuidService.getPayUId()) { //payuid가 없는 결제수단을 선택한 경우 } // 이미 payuid가 있는
		 * 결제수단을 선택한 경우
		 */
	
        // 프로세스 처리
        return "redirect:/category/success"; // 성공시 리다이렉트
    }
}

