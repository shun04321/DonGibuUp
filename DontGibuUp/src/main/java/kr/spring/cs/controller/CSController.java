package kr.spring.cs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.cs.vo.InquiryVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CSController {
	//자바빈(VO) 초기화
	@ModelAttribute
	public InquiryVO initCommand() {
		return new InquiryVO();
	}

	@GetMapping("/cs/faqlist")
	public String faqlist() {
		return "faqlist";
	}

	@GetMapping("/cs/inquiry")
	public String form(Model model) {
		Map<String, String> inquiry_category = new HashMap<String, String>();
		inquiry_category.put("", "카테고리 선택");
		inquiry_category.put("0", "정기기부");
		inquiry_category.put("1", "기부박스");
		inquiry_category.put("2", "챌린지");
		inquiry_category.put("3", "굿즈샵");
		inquiry_category.put("4", "기타");

		model.addAttribute("inquiry_category", inquiry_category);

		return "inquiryForm";
	}

	@PostMapping("/cs/inquiry")
	public String inquiry(@Valid InquiryVO inquiryVO, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			Map<String, String> inquiry_category = new HashMap<String, String>();
			inquiry_category.put("", "카테고리 선택");
			inquiry_category.put("0", "정기기부");
			inquiry_category.put("1", "기부박스");
			inquiry_category.put("2", "챌린지");
			inquiry_category.put("3", "굿즈샵");
			inquiry_category.put("4", "기타");

			model.addAttribute("inquiry_category", inquiry_category);
			return "inquiryForm";
		}
		
		return "resultPage";
	}
}
