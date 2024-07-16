package kr.spring.cs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CSController {
	@GetMapping("/cs/faqlist")
	public String faqlist() {
		return "faqlist";
	}
	
	@GetMapping("/cs/form")
	public String form() {
		return "inquiryForm";
	}
}
