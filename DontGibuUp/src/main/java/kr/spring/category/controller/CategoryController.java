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
import org.springframework.web.servlet.ModelAndView;

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
	//기부 카테고리 및 기부처 리스트 호출
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

	//기부 카테고리 및 기부처 등록 폼 호출
	@GetMapping("/category/insertCategory")
	public String insertCategoryForm(Model model) {
		model.addAttribute("donationCategoryVO", new DonationCategoryVO());
		return "insertCategory";
	}



	//기부 카테고리 및 기부처 등록
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
	
	//기부 카테고리 상세
	@GetMapping("/category/detail")
	public ModelAndView detailCategory(long dcate_num) {
		log.debug("dcate_num : " + dcate_num);
		DonationCategoryVO categoryVO = categoryService.selectDonationCategory(dcate_num);
		log.debug("donationcategoryVO : " + categoryVO);
		return new ModelAndView("categoryDetail","category",categoryVO);
	} 
	
	//기부 카테고리 삭제
	@GetMapping("/category/deleteCategory")
	public String submitDelete(long dcate_num, HttpServletRequest request) {
		log.debug("<<기부 카테고리 삭제>> : dcate_num : " + dcate_num );
		DonationCategoryVO categoryVO = categoryService.selectDonationCategory(dcate_num);
		
		
		if(categoryVO.getDcate_icon()!=null) {
			FileUtil.removeFile(request, categoryVO.getDcate_icon());
		}
		categoryService.deleteDonationCategory(dcate_num);	
		
		return  "redirect:/category/categoryList";
	}
	// 카테고리 수정 폼 호출
	@GetMapping("/category/updateCategory")
	public String formUpdate(long dcate_num,Model model) {
		log.debug("dcate_num : " + dcate_num);
		DonationCategoryVO categoryVO = 
				categoryService.selectDonationCategory(dcate_num);
		model.addAttribute("donationCategoryVO", categoryVO);
		
		return "categoryUpdate";
	}

	
	  @PostMapping("/category/updateCategory") 
	  public String submitUpdate(@Valid DonationCategoryVO categoryVO, 
			  					BindingResult result, 
			  					Model model, 
			  					HttpServletRequest request)
	  throws IllegalStateException, IOException { 
		  log.debug("<<카테고리 수정>> : " + categoryVO);
	  
	  //유효성 체크 결과 오류가 있으면 폼 호출 
	  if(result.hasErrors()) { 
	  // 유효성 체크시 오류 있을 시 파일 정보 잃어버림
	  DonationCategoryVO vo = categoryService.selectDonationCategory(categoryVO.getDcate_num());
	  categoryVO.setDcate_icon(vo.getDcate_icon()); 
	  return "categoryUpdate"; 
	  }
	  
	  //DB에 저장된 파일 정보 구하기 
	  DonationCategoryVO db_category = categoryService.selectDonationCategory(categoryVO.getDcate_num());
	  //파일명 셋팅(FileUtil.createFile에서 파일이 없으면 null 처리함)
	  categoryVO.setDcate_icon(FileUtil.createFile(request, categoryVO.getUpload()));
	  
	  //카테고리 수정
	  categoryService.updateDonationCategory(categoryVO);
	  
	  if(categoryVO.getUpload()!=null && !categoryVO.getUpload().isEmpty()) { //수정전 파일 삭제처리
		 FileUtil.removeFile(request, db_category.getDcate_icon()); 
	  }
	  
	  model.addAttribute("message","카테고리 수정완료");
	  model.addAttribute("url",request.getContextPath()+"/category/detail?dcate_num="+categoryVO.getDcate_num()); 
	  
	  return "common/resultAlert";
	 }
	
}
