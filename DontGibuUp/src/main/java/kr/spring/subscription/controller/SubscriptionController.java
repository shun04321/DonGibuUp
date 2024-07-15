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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	    MemberVO member_db = memberService.selectMember(user.getMem_num()); 
	    PayuidVO payuid = new PayuidVO();
	    payuid.setMem_num(user.getMem_num());
	    
	    if(cardNickname != null) {
	        payuid.setCard_nickname(cardNickname);
	    } else if("easy-pay".equals(subscriptionVO.getSub_method())) {
	        payuid.setEasypay_method(subscriptionVO.getEasypay_method());
	        log.debug("payuid : " + payuid);
	    }

	    if (payuidService.getCountPayuidByMethod(payuid) == 0) {
	        PayuidVO reg_payuid = new PayuidVO();
	        String newpayuid = generateUUIDFromMem_num(user.getMem_num());
	        reg_payuid.setPay_uid(newpayuid);
	        reg_payuid.setMem_num(user.getMem_num());
	        
	        if ("easy-pay".equals(subscriptionVO.getSub_method())) {
	            reg_payuid.setEasypay_method(subscriptionVO.getEasypay_method());
	        } else {
	            reg_payuid.setCard_nickname(cardNickname);
	        }

	        log.debug("payuid 등록 테스트 : " + reg_payuid);
	        payuidService.registerPayUId(reg_payuid);
	        subscriptionVO.setSub_ndate(getTodayDateString());
	        subscriptionService.insertSubscription(subscriptionVO);
	        
	        redirectAttributes.addFlashAttribute("user", member_db);
	        redirectAttributes.addFlashAttribute("payuidVO", reg_payuid);
	        
	        log.debug("getCardpayuid로 이동하나?");
	        return "redirect:/subscription/getCardpayuid";
	    }

	    return "redirect:/category/success"; // 성공시 리다이렉트
	}
	
	@GetMapping("/subscription/getCardpayuid")
	public String getCardpayuid(@ModelAttribute("user") MemberVO user,
	                            @ModelAttribute("payuidVO") PayuidVO payuidVO,
	                            Model model) {
	    // user와 payuidVO 데이터를 사용하여 로직을 처리합니다.
	    model.addAttribute("user", user);
	    model.addAttribute("payuidVO", payuidVO);
	    return "/subscription/getCardpayuid";
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

