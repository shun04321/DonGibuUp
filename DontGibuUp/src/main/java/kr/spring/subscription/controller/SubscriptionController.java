package kr.spring.subscription.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.config.validation.ValidationSequence;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.payuid.service.PayuidService;
import kr.spring.payuid.vo.PayuidVO;
import kr.spring.subscription.service.Sub_paymentService;
import kr.spring.subscription.service.SubscriptionService;
import kr.spring.subscription.vo.SubscriptionVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import kr.spring.subscription.vo.GetTokenVO;
import kr.spring.subscription.vo.Sub_paymentVO;
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
	@Autowired
	private Sub_paymentService sub_paymentService;
	
	/*--------------------
	 * 정기 기부 메인창 이동
	 *-------------------*/
	@GetMapping("/subscription/subscriptionMain")
	public String subScriptionMain() {
		
		System.out.println(subscriptionService.getToken());
		
		return "subscriptionMain";
	}
	/*--------------------
	 * 정기기부 신청폼
	 *-------------------*/
	@PostMapping("/category/registerSubscription")
	public String signup(@Validated(ValidationSequence.class) SubscriptionVO subscriptionVO,
	                     Model model,
	                     HttpServletRequest request,
	                     HttpSession session,                    
	                     RedirectAttributes redirectAttributes) {
	    log.debug("정기기부 등록 subscriptionVO : " + subscriptionVO);
	    MemberVO user = (MemberVO) session.getAttribute("user");
	    //비로그인 상태면 로그인 페이지로 이동
	    if(user==null) {
	    	return "redirect:/member/login";
	    }
	    //sub_ndate생성
	    subscriptionVO.setSub_ndate(subscriptionService.getTodayDateString());
        //sub_num 생성
        subscriptionVO.setSub_num(subscriptionService.getSub_num());
        //subscription 등록
        subscriptionService.insertSubscription(subscriptionVO);
        
	    //로그인한 회원 정보 저장
	    MemberVO member_db = memberService.selectMemberDetail(user.getMem_num()); 
	    //존재하는 payuid와 대조할 payuidVO 생성후 데이터 셋팅
	    PayuidVO payuid = new PayuidVO();
	    payuid.setMem_num(user.getMem_num());
	    
	    if(!subscriptionVO.getCard_nickname().equals("")) { //결제수단 카드 선택(카드 이름 셋팅)
	        payuid.setCard_nickname(subscriptionVO.getCard_nickname());
	    } else{// 결제수단 이지페이 선택 (플랫폼 셋팅) 
	        payuid.setEasypay_method(subscriptionVO.getEasypay_method());	       
	    }
	    log.debug("결제수단에 따른 payuidVO 셋팅 : " + payuid);
	    
	    if (payuidService.getPayuidByMethod(payuid) == null) { //선택한 결제수단의 payuid가 없는 경우, payuid 생성 후 빌링키 발급 페이지로 이동
	        PayuidVO reg_payuid = new PayuidVO();
	        String newpayuid = payuidService.generateUUIDFromMem_num(user.getMem_num());
	        reg_payuid.setPay_uid(newpayuid);
	        reg_payuid.setMem_num(user.getMem_num());
	        
	        if ("easy_pay".equals(subscriptionVO.getSub_method())) {
	            reg_payuid.setEasypay_method(subscriptionVO.getEasypay_method());
	        } else {
	            reg_payuid.setCard_nickname(subscriptionVO.getCard_nickname());
	        }

	        log.debug("payuid 등록 테스트 : " + reg_payuid);
	        payuidService.registerPayUId(reg_payuid);
	        
	        redirectAttributes.addFlashAttribute("subscriptionVO",subscriptionVO);
	        redirectAttributes.addFlashAttribute("user", member_db);
	        redirectAttributes.addFlashAttribute("payuidVO", reg_payuid); // 등록되는 payuid 정보로 빌링키 발급 페이지 이동        

	        return "redirect:/subscription/getpayuid"; 
	    }
	    payuid = payuidService.getPayuidByMethod(payuid);
	    log.debug("등록된 결제수단 payuid = " + payuid);
	    redirectAttributes.addFlashAttribute("subscriptionVO",subscriptionVO);
        redirectAttributes.addFlashAttribute("user", member_db);
        redirectAttributes.addFlashAttribute("payuidVO", payuid); // 이미 존재하는 payuid로 결제 예약 페이지 이동 
       
	    return "redirect:/subscription/paymentReservation";
	}
		/*--------------------
		 * 결제수단 등록페이지로 이동
		 *-------------------*/
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
	
		//과거에 등록한 결제수단으로 결제 페이지 이동
		@GetMapping("/subscription/paymentReservation")
		public String sign_up(@ModelAttribute("user") MemberVO user,
		                      @ModelAttribute("payuidVO") PayuidVO payuidVO,
		                      @ModelAttribute("subscriptionVO") SubscriptionVO subscriptionVO,
		                      Model model) {
			
			log.debug("등록된 결제수단 payuid2 = " + payuidVO);
			DonationCategoryVO categoryVO = categoryService.selectDonationCategory(subscriptionVO.getDcate_num());
			Sub_paymentVO sub_paymentVO = new Sub_paymentVO();
			sub_paymentVO.setSub_pay_num(sub_paymentService.getSub_payment_num());
			sub_paymentVO.setMem_num(user.getMem_num());
			sub_paymentVO.setSub_num(subscriptionVO.getSub_num());
			sub_paymentVO.setSub_price(subscriptionVO.getSub_price());
			sub_paymentVO.setSub_pay_date("2024-07-15");
			
			String token = subscriptionService.getToken();
			Gson str = new Gson();
			token = token.substring(token.indexOf("response") + 10);
			token = token.substring(0, token.length() - 1);

			GetTokenVO vo = str.fromJson(token, GetTokenVO.class);

			String access_token = vo.getAccess_token();
			System.out.println(access_token);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(access_token);

			Map<String, Object> map = new HashMap<>();
			map.put("customer_uid", payuidVO.getPay_uid());
			map.put("merchant_uid", sub_paymentVO.getSub_pay_num());
			map.put("amount", sub_paymentVO.getSub_price());
			map.put("name", categoryVO.getDcate_name());
			
			Gson var = new Gson();
			String json = var.toJson(map);
			System.out.println("json : " + json);
			HttpEntity<String> entity = new HttpEntity<>(json, headers);
			 String apiResponse = restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/again", entity, String.class);
			    
			    // API 응답을 모델에 추가
			    model.addAttribute("apiResponse", apiResponse);
			    model.addAttribute("memberVO",user);
			    
			    // JSP 페이지로 이동
			    return "/mypage/memberInfo"; // 결제 결과를 보여줄 JSP 페이지의 이름
		}
		
		/*--------------------
		 * 결제 수단 등록후 첫결제
		 *-------------------*/
		@PostMapping("/subscription/paymentReservation")
		@ResponseBody
		public String sign_up2(String pay_uid, long sub_num, HttpSession session, Model model) {
		    log.debug("결제수단 성공후 전달받은 pay_uid : " + pay_uid);
		    log.debug("결제수단 성공후 전달받은 sub_num : " + sub_num);
		    
		    SubscriptionVO subscriptionVO = subscriptionService.getSubscription(sub_num);
		    MemberVO user = (MemberVO) session.getAttribute("user");
		    PayuidVO payuidVO = payuidService.getPayuidVOByPayuid(pay_uid);
		    DonationCategoryVO categoryVO = categoryService.selectDonationCategory(subscriptionVO.getDcate_num());
		    
		    Sub_paymentVO sub_paymentVO = new Sub_paymentVO();
			sub_paymentVO.setSub_pay_num(sub_paymentService.getSub_payment_num());
			sub_paymentVO.setMem_num(user.getMem_num());
			sub_paymentVO.setSub_num(subscriptionVO.getSub_num());
			sub_paymentVO.setSub_price(subscriptionVO.getSub_price());
			sub_paymentVO.setSub_pay_date("2024-07-15");
			
			String token = subscriptionService.getToken();
			Gson str = new Gson();
			token = token.substring(token.indexOf("response") + 10);
			token = token.substring(0, token.length() - 1);

			GetTokenVO vo = str.fromJson(token, GetTokenVO.class);

			String access_token = vo.getAccess_token();
			System.out.println(access_token);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(access_token);

			Map<String, Object> map = new HashMap<>();
			map.put("customer_uid", payuidVO.getPay_uid());
			map.put("merchant_uid", sub_paymentVO.getSub_pay_num());
			map.put("amount", sub_paymentVO.getSub_price());
			map.put("name", categoryVO.getDcate_name());

			Gson var = new Gson();
			String json = var.toJson(map);
			System.out.println(json);
			HttpEntity<String> entity = new HttpEntity<>(json, headers);
			
			return restTemplate.postForObject("https://api.iamport.kr/subscribe/payments/again", entity, String.class);
		}
		/*--------------------
		 * 기존의 결제수단으로 결제시도
		 *-------------------*/
		@GetMapping("/subscription/payReservation")
		public String paymentReservationPage(HttpSession session, Model model) {
		    MemberVO user = (MemberVO) session.getAttribute("user");
		    SubscriptionVO subscriptionVO = (SubscriptionVO) session.getAttribute("subscriptionVO");
		    PayuidVO payuidVO = (PayuidVO) session.getAttribute("payuidVO");
		    DonationCategoryVO categoryVO = (DonationCategoryVO) session.getAttribute("categoryVO");
		    if (user == null || subscriptionVO == null || payuidVO == null) {
		        return "redirect:/category/detail?dcate_num="+subscriptionVO.getDcate_num(); // 필요한 데이터가 없으면 에러 페이지로 리다이렉트
		    }
		    
		    model.addAttribute("user", user);
		    model.addAttribute("subscriptionVO", subscriptionVO);
		    model.addAttribute("payuidVO", payuidVO);
		    model.addAttribute("categoryVO",categoryVO);
		    
		    return "subscription/payment_reservation";
		}

		
		
		//빌링키 발급 성공(중단)시 구독정보 저장.
		@PostMapping("/subscription/successGetpayuid")
		@ResponseBody
		public Map<String,String> insertSubscription(String pay_uid, long sub_num, HttpSession session) throws Exception {
			Map<String,String> mapJson = new HashMap<String,String>();
			log.debug("빌링키 발급 성공 : " + pay_uid);
			try {
				//빌링키 발급 실패한 payuid 삭제
				payuidService.deletePayuid(pay_uid);
				mapJson.put("result", "success");
			}catch(Exception e) {
				mapJson.put("result", "fail");
				throw new Exception(e);
			}	
			return mapJson;
		}
		
	/*--------------------
	 * 결제수단 등록 실패시 payuid 삭제
	 *-------------------*/
	@PostMapping("/subscription/failGetpayuid")
	@ResponseBody
	public Map<String,String> deletePayuid(String pay_uid, long sub_num, HttpSession session) throws Exception {
		Map<String,String> mapJson = new HashMap<String,String>();
		log.debug("빌링키 발급 실패(중단)된 pay_uid : " + pay_uid);
		try {
			//빌링키 발급 실패한 payuid 삭제
			payuidService.deletePayuid(pay_uid);
			mapJson.put("result", "success");
		}catch(Exception e) {
			mapJson.put("result", "fail");
			throw new Exception(e);
		}	
		return mapJson;
	}
	

	}


