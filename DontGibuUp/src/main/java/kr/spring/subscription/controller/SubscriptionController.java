package kr.spring.subscription.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.config.validation.ValidationSequence;
import kr.spring.subscription.service.SubscriptionService;
import kr.spring.subscription.vo.SubscriptionVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SubscriptionController {
	@Autowired
	private SubscriptionService subscriptionServce;
	
	@GetMapping("/subscription/subscriptionMain")
	public String subScriptionMain() {
		return "subscriptionMain";
	}
	
	@PostMapping("/subscription/registerSubscription")
	public String signup(@Validated(ValidationSequence.class) SubscriptionVO subscriptionVO, BindingResult result, Model model,
			HttpServletRequest request) {
		if(result.hasErrors()) {
			log.debug("result : " + result.getAllErrors());
			return "categoryDetail";
		}
		log.debug("subscriptionVO : " + subscriptionVO);
	

		return "getpayuid";
	}
}
