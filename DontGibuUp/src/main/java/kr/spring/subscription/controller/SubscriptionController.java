package kr.spring.subscription.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	                     Model model,
	                     HttpServletRequest request,
	                     HttpSession session,
	                     @RequestParam(value = "card_nickname", required = false) String cardNickname) {
	    log.debug("정기기부 등록 subscriptionVO : " + subscriptionVO);

	    MemberVO user = (MemberVO) session.getAttribute("user");
	    // 유저가 가진 payuid와 대조할 payuidVO 생성
	    PayuidVO payuid = new PayuidVO();
	    payuid.setMem_num(user.getMem_num());
	    //카드가 선택되었으면 카드 이름으로 대조
	    if(cardNickname != null) {
	    	payuid.setCard_nickname(cardNickname);
	    }else if("easy-pay".equals(subscriptionVO.getSub_method())) {//이지페이가 선택되었으면 이지페이 플랫폼으로 대조
	        payuid.setEasypay_method(subscriptionVO.getEasypay_method());
	        log.debug("payuid : " + payuid);
	    }
	    

	    if (payuidService.getPayuidByMethod(payuid) == null) {
	        PayuidVO reg_payuid = new PayuidVO();
	        // UUID 생성 -> payuid로 사용
	        String newpayuid = generateUUIDFromMem_num(user.getMem_num());
	        // 데이터 설정
	        reg_payuid.setPay_uid(newpayuid);
	        reg_payuid.setMem_num(user.getMem_num());
	        
	        if ("easy-pay".equals(subscriptionVO.getSub_method())) {
	            reg_payuid.setEasypay_method(subscriptionVO.getEasypay_method());
	        } else {
	            reg_payuid.setCard_nickname(cardNickname);
	        }

	        log.debug("payuid 등록 테스트 : " + reg_payuid);
	        // payuid 등록
	        payuidService.registerPayUId(reg_payuid);
	        
	        //오늘 날짜를 다음 결제일로 지정
	        subscriptionVO.setSub_ndate(getTodayDateString());
	        // subscription 등록
	        subscriptionService.insertSubscription(subscriptionVO);
	        //등록된 subscription의 sub_num 반환
	        long sub_num = subscriptionService.getSubscriptionNum(user.getMem_num(), subscriptionVO.getDcate_num());
	        
	        model.addAttribute("user",user);
	        model.addAttribute("sub_num", sub_num);
	        model.addAttribute("payuidVO", reg_payuid);
	        // payuid 정보로 빌링키 발급 창 이동
	        log.debug("getCardpayuid로 이동하나?");
	        return "getCardpayuid";
	    }

	    // 이미 payuid가 있는 경우 처리 로직 구현

	    // 프로세스 처리
	    return "redirect:/category/success"; // 성공시 리다이렉트
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

