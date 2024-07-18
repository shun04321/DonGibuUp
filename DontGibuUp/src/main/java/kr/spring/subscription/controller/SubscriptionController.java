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
import kr.spring.subscription.service.Sub_paymentService;
import kr.spring.subscription.service.SubscriptionService;
import kr.spring.subscription.vo.SubscriptionVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	 * 정기기부 신청폼 제출
	 *-------------------*/
	@PostMapping("/category/registerSubscription")
	public String signup(@Validated(ValidationSequence.class) SubscriptionVO subscriptionVO,
			Model model,
			HttpServletRequest request,
			HttpSession session,                    
			RedirectAttributes redirectAttributes,
			@RequestParam(name = "sub_date") String subDate
			) {
		log.debug("정기기부 등록 subscriptionVO : " + subscriptionVO);
		MemberVO user = (MemberVO) session.getAttribute("user");
		//비로그인 상태면 로그인 페이지로 이동
		if(user==null) {
			return "redirect:/member/login";
		}
		//sub_num 생성
		subscriptionVO.setSub_num(subscriptionService.getSub_num());
		//subDate int 형으로 변환
		subscriptionVO.setSub_ndate(Integer.parseInt(subDate));
		
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
			reg_payuid.setPay_uid(payuidService.generateUUIDFromMem_num(user.getMem_num()));
			reg_payuid.setMem_num(user.getMem_num());
			if ("easy_pay".equals(subscriptionVO.getSub_method())) {
				reg_payuid.setEasypay_method(subscriptionVO.getEasypay_method());
			} else {
				reg_payuid.setCard_nickname(subscriptionVO.getCard_nickname());
			}
			log.debug("등록할 payuid 정보 : " + reg_payuid);
			//payuid 등록 -> 빌링키 발급 실패하면 삭
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
		subscriptionService.insertSubscription(subscriptionVO);
		// user와 payuidVO 데이터를 사용하여 로직을 처리합니다.
		model.addAttribute("user", user);
		model.addAttribute("subscriptionVO",subscriptionVO);
		model.addAttribute("payuidVO", payuidVO);
		return "/subscription/getpayuid";
	}
	/*--------------------
	 * 기존의 결제수단으로 정기기부 등록 (결제 성공하면 정기기부 등록, 결제내역 등록)
	 *-------------------*/
	@GetMapping("/subscription/paymentReservation")
	public String sign_up(@ModelAttribute("user") MemberVO user,
			@ModelAttribute("payuidVO") PayuidVO payuidVO,
			@ModelAttribute("subscriptionVO") SubscriptionVO subscriptionVO,
			HttpSession session,
			Model model) {				
		subscriptionService.insertSubscription(subscriptionVO);
		
		//결제
		String payment_result = insertSub_Payment(payuidVO.getPay_uid(), subscriptionVO.getSub_num(),session );
		if(payment_result.equals("payment success")) {
			model.addAttribute("accessTitle", "결제 결과");
			model.addAttribute("accessMsg", "정기기부 결제 및 등록완료");
			
		}else {
			model.addAttribute("accessTitle", "결제 결과");
			model.addAttribute("accessMsg", "정기기부 결제 및 등록실패");
		}
		// API 응답을 모델에 추가

		model.addAttribute("accessBtn", "완료");
		model.addAttribute("accessUrl", "/main/main");

		// JSP 페이지로 이동
		return "common/resultView"; // 결제 결과를 보여줄 JSP 페이지의 이름
	}

	/*--------------------
	 * 결제수단 발급 성공 -> payuid 저장 이후 첫 결제 성공하면 정기기부 등록, 결제내역 등록
	 *-------------------*/
	@PostMapping("/subscription/paymentReservation")
	@ResponseBody
	public String sign_up2(String pay_uid, long sub_num, HttpSession session, Model model) {	    
		SubscriptionVO subscriptionVO = subscriptionService.getSubscription(sub_num);
		log.debug("pay_uid : " + pay_uid );
		log.debug("sub_num : " + sub_num);
		PayuidVO payuidVO = payuidService.getPayuidVOByPayuid(pay_uid);
		
		//결제
		String payment_result = insertSub_Payment(payuidVO.getPay_uid(), subscriptionVO.getSub_num(), session);
		if(payment_result.equals("payment success")) {
			model.addAttribute("accessTitle", "결제 결과");
			model.addAttribute("accessMsg", "정기기부 결제 및 등록완료");			
		}else {
			model.addAttribute("accessTitle", "결제 결과");
			model.addAttribute("accessMsg", "정기기부 결제 및 등록실패");
			subscriptionService.deleteSubscription(sub_num);
		}
		model.addAttribute("accessBtn", "완료");
		model.addAttribute("accessUrl", "/main/main");

		// JSP 페이지로 이동
		return "common/resultView"; // 결제 결과를 보여줄 JSP 페이지의 이름
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
			//정기기부도 삭제
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
	
	
	
		/*-----------------------
		 * 첫 결제
		 ------------------------*/
	    public String insertSub_Payment(String payuid, long sub_num, HttpSession session) {
	    	MemberVO user = (MemberVO)session.getAttribute("user");
	    	SubscriptionVO subscriptionVO = subscriptionService.getSubscription(sub_num);
	    	log.debug("sub_num" + sub_num);
	    	DonationCategoryVO categoryVO = categoryService.selectDonationCategory(subscriptionVO.getDcate_num());
	    	
	        String token = subscriptionService.getToken();
	        Gson gson = new Gson();
	        token = token.substring(token.indexOf("response") + 10);
	        token = token.substring(0, token.length() - 1);

	        // token에서 response 부분을 추출하여 GetTokenVO로 변환
	        GetTokenVO vo = gson.fromJson(token, GetTokenVO.class);

	        String access_token = vo.getAccess_token();
	        System.out.println(access_token);

	        RestTemplate restTemplate = new RestTemplate();
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(access_token);
	        Sub_paymentVO sub_paymentVO = new Sub_paymentVO();
			sub_paymentVO.setSub_pay_num(sub_paymentService.getSub_payment_num());
			sub_paymentVO.setMem_num(user.getMem_num());
			sub_paymentVO.setSub_num(subscriptionVO.getSub_num());
			sub_paymentVO.setSub_price(subscriptionVO.getSub_price());
			sub_paymentVO.setSub_pay_date("2024-07-18");
	        
	        Map<String, Object> map = new HashMap<>();
	        map.put("customer_uid", payuid);
	        map.put("merchant_uid", sub_paymentVO.getSub_pay_num());
	        map.put("amount", sub_paymentVO.getSub_price());
	        map.put("name", categoryVO.getDcate_name() + " 정기 기부");

	        String json = gson.toJson(map);
	        System.out.println("json : " + json);

	        HttpEntity<String> entity = new HttpEntity<>(json, headers);
	        ResponseEntity<String> response = restTemplate.postForEntity("https://api.iamport.kr/subscribe/payments/again", entity, String.class);

	        // API 응답을 문자열로 받음
	        String responseBody = response.getBody();

		    // API 응답 문자열에서 code 값을 추출
	        Number codeNumber = (Number) gson.fromJson(responseBody, Map.class).get("code");
	        int code = codeNumber.intValue();


	        if (response.getStatusCode() == HttpStatus.OK) {
	            // API 호출은 성공적으로 되었지만, 실제 결제 성공 여부는 API 응답의 상태를 확인해야 함
	            if (code == 0) {
	                // 결제 성공
	            	sub_paymentService.insertSub_payment(sub_paymentVO);
	                return "payment success";
	            } else {
	                // 결제 실패
	                return "payment fail";
	            }
	        } else {
	            // API 호출 실패
	            return "api fail";
	        }
	    }
}

