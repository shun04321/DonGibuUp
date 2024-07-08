package kr.spring.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryServce;
	
	@GetMapping("/category/categoryList")
	public String categoryList() {
		
		return "categoryList";
	}
	
	@GetMapping("/category/insertCategory")
	public String insertCategoryForm(Model model) {
		model.addAttribute("donationCategoryVO", new DonationCategoryVO());
		return "insertCategory";
	}
}
