package kr.spring.subscription.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.config.validation.ValidationSequence;
import kr.spring.member.service.MemberService;
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
	@Autowired
	private MemberService memberService;
	
	
	@GetMapping("/subscription/subscriptionMain")
	public String subScriptionMain() {
		return "subscriptionMain";
	}
	@PostMapping("/category/registerSubscription")
	public String signup(@Validated(ValidationSequence.class) SubscriptionVO subscriptionVO,
	                     Model model,
	                     HttpServletRequest request,
	                     HttpSession session,
	                     @RequestParam(value = "card_nickname", required = false) String cardNickname,
	                     RedirectAttributes redirectAttributes) {
	    log.debug("정기기부 등록 subscriptionVO : " + subscriptionVO);
	   
	    MemberVO user = (MemberVO) session.getAttribute("user");
	    //비로그인 상태면 로그인 페이지로 이동
	    if(user==null) {
	    	return "redirect:/member/login";
	    }
	    //로그인한 회원 정보 저장
	    MemberVO member_db = memberService.selectMemberDetail(user.getMem_num()); 
	    //존재하는 payuid와 대조할 payuidVO 생성후 데이터 셋팅
	    PayuidVO payuid = new PayuidVO();
	    payuid.setMem_num(user.getMem_num());
	    
	    if(!cardNickname.equals("")) { //결제수단 카드 선택(카드 이름 셋팅)
	        payuid.setCard_nickname(cardNickname);
	    } else{// 결제수단 이지페이 선택 (플랫폼 셋팅) 
	        payuid.setEasypay_method(subscriptionVO.getEasypay_method());	       
	    }
	    log.debug("결제수단에 따른 payuidVO 셋팅 : " + payuid);
	    
	    if (payuidService.getCountPayuidByMethod(payuid) == 0) { //선택한 결제수단의 payuid가 없는 경우, payuid 생성 후 빌링키 발급 페이지로 이동
	        PayuidVO reg_payuid = new PayuidVO();
	        String newpayuid = generateUUIDFromMem_num(user.getMem_num());
	        reg_payuid.setPay_uid(newpayuid);
	        reg_payuid.setMem_num(user.getMem_num());
	        
	        if ("easy_pay".equals(subscriptionVO.getSub_method())) {
	            reg_payuid.setEasypay_method(subscriptionVO.getEasypay_method());
	        } else {
	            reg_payuid.setCard_nickname(cardNickname);
	        }

	        log.debug("payuid 등록 테스트 : " + reg_payuid);
	        payuidService.registerPayUId(reg_payuid);
	        subscriptionVO.setSub_ndate(getTodayDateString());
	        //sub_num 생성하고 subscription 등록
	        subscriptionVO.setSub_num(subscriptionService.getSub_num());
	        subscriptionService.insertSubscription(subscriptionVO);
	        
	        redirectAttributes.addFlashAttribute("subscriptionVO",subscriptionVO);
	        redirectAttributes.addFlashAttribute("user", member_db);
	        redirectAttributes.addFlashAttribute("payuidVO", reg_payuid); // 등록되는 payuid 정보로 빌링키 발급 페이지 이동        

	        return "redirect:/subscription/getpayuid"; 
	    }
	    redirectAttributes.addFlashAttribute("subscriptionVO",subscriptionVO);
        redirectAttributes.addFlashAttribute("user", member_db);
        redirectAttributes.addFlashAttribute("payuidVO", payuid); // 이미 존재하는 payuid로 결제 예약 페이지 이동 

	    return "redirect:/subscription/paymentReservation";
	}
	
		
		//카드 payuid 발급 이동
		@GetMapping("/subscription/getpayuid")
		public String getpayuid(@ModelAttribute("user") MemberVO user,
		                        @ModelAttribute("payuidVO") PayuidVO payuidVO,
		                        @ModelAttribute("subscriptionVO") SubscriptionVO subscriptionVO,
		                            Model model) {
			log.debug("payuid 발급 페이지로 전달되는 유저 정보 : " + user);
			log.debug("payuid 발급 페이지로 전달되는 정기기부 정보 : " + subscriptionVO);
			
		    // user와 payuidVO 데이터를 사용하여 로직을 처리합니다.
		    model.addAttribute("user", user);
		    model.addAttribute("subscriptionVO",subscriptionVO);
		    model.addAttribute("payuidVO", payuidVO);
		    return "/subscription/getpayuid";
		}
	
		//정기결제 결제 예약
		@GetMapping("/subscription/paymentReservation")
		public String sign_up(@ModelAttribute("user") MemberVO user,
		                      @ModelAttribute("payuidVO") PayuidVO payuidVO,
		                      @ModelAttribute("subscriptionVO") SubscriptionVO subscriptionVO,
		                      Model model) {
			log.debug("결제 예약 페이지로 이동되는 유저정보 : " + user);
			log.debug("결제예약 페이지로 이동되는 정기기부 정보 : " + subscriptionVO);
			log.debug("결제예약 페이지로 이동되는 payuid 정보 : " + payuidVO);
		    // user와 payuidVO 데이터를 사용하여 로직을 처리합니다.
		    model.addAttribute("user", user);
		    model.addAttribute("subscriptionVO",subscriptionVO);
		    model.addAttribute("payuidVO", payuidVO);
		    return "/subscription/payment_reservation";
		}
	
		//빌링키 발급 실패(중단)시 생성한 payuid 삭제
		@PostMapping("/subscription/failGetpayId")
		@ResponseBody
		public Map<String,String> deletePayuid(String pay_uid, long sub_num, HttpSession session) throws Exception {
			Map<String,String> mapJson = new HashMap<String,String>();
			log.debug("빌링키 발급 실패(중단)된 pay_uid : " + pay_uid);
			try {
				//신청하려던 subscription 삭제
				subscriptionService.deleteSubscription(sub_num);
				//빌링키 발급 실패한 payuid 삭제
				payuidService.deletePayuid(pay_uid);
				mapJson.put("result", "success");
			}catch(Exception e) {
				mapJson.put("result", "fail");
				throw new Exception(e);
			}	
			return mapJson;
		}
	
	
	
	
	
	
	
	
     //payuid 생성 메소드
	 private String generateUUIDFromMem_num(long mem_num) {
	        String source = String.valueOf(mem_num);
	        String uuid = source + UUID.randomUUID();
	        return uuid.toString();
	 }
	 // 오늘 날짜를 구해서 저장하기 메소드
	 public static String getTodayDateString() {
	        // 현재 날짜 가져오기
	        LocalDate today = LocalDate.now();
	        
	        // 날짜 포맷 지정 (예: "yyyy-MM-dd")
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        
	        // 포맷에 맞게 날짜를 문자열로 변환하여 반환
	        return today.format(formatter);
	    }
}

