package kr.spring.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryServce;
	
	@GetMapping("/category/categoryList")
	public String subScriptionMain() {
		return "categoryList";
	}
}
