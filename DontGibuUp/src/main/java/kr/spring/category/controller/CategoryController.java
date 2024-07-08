package kr.spring.category.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/category/categoryList")
	public String categoryList(@RequestParam(defaultValue="1") int pageNum, Model model) {

		Map<String,Object> map = 
				new HashMap<String,Object>();

		//전체,검색 레코드수
		int count = categoryService.getListCount(map);

		//페이지 처리
		PagingUtil page = 
				new PagingUtil(pageNum,
						count,20,10,"list");
		List<DonationCategoryVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			list = categoryService.selectList(map);
		}

		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());

		return "categoryList";
	}


	@GetMapping("/category/insertCategory")
	public String insertCategoryForm(Model model) {
		model.addAttribute("donationCategoryVO", new DonationCategoryVO());
		return "insertCategory";
	}




	@PostMapping("/category/insertCategory")
	public String submit(@Valid DonationCategoryVO donationCategoryVO,
			BindingResult result,
			HttpServletRequest request,
			HttpSession session,
			Model model)
					throws IllegalStateException,
					IOException{
		log.debug("<<카테고리 및 기부처 저장>> : " + donationCategoryVO);

		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			if (donationCategoryVO.getUpload() == null || donationCategoryVO.getUpload().isEmpty()) {
				result.rejectValue("upload", "error.upload", "기부처 아이콘을 업로드해야 합니다.");
			}
			return "insertCategory";
		}

		//파일 업로드
		donationCategoryVO.setDcate_icon(FileUtil.createFile(request, 
				donationCategoryVO.getUpload()));

		//기부 카테고리 등록
		categoryService.insertDonationCategory(donationCategoryVO);

		//View 메시지 처리
		model.addAttribute("message", "카테고리 및 기부처가 등록되었습니다.");
		model.addAttribute("url", 
				request.getContextPath()+"/category/categoryList");

		return "common/resultAlert";
	}
}
