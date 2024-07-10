package kr.spring.payuid.controller;

import java.io.IOException
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.payuid.service.PayuidService;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class PayuidController {
	@Autowired
	private PayuidService payuidService;
	
/*	@GetMapping("/category/categoryList")*/
}
