package kr.spring.payuid.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.config.validation.ValidationSequence;
import kr.spring.member.vo.MemberVO;
import kr.spring.payuid.service.PayuidService;
import kr.spring.subscription.vo.SubscriptionVO;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class PayuidController {
	@Autowired
	private PayuidService payuidService;
	
	@PostMapping("/category/registerSubscription")
	public String signup(@Validated(ValidationSequence.class) SubscriptionVO subscriptionVO, BindingResult result, Model model,
			HttpServletRequest request) {
		if(result.hasErrors()) {
			log.debug("result : " + result.getAllErrors());
			return "categoryDetail";
		}
		log.debug("subscriptionVO : " + subscriptionVO);
		model.addAttribute("accessTitle", "회원가입 완료");
		model.addAttribute("accessMsg", "회원가입이 완료되었습니다");
		model.addAttribute("accessBtn", "로그인하기");
		model.addAttribute("accessUrl", request.getContextPath() + "/member/login");

		return "resultPage";
	}
}
	
