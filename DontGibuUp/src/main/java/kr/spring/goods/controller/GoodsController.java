package kr.spring.goods.controller;

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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import kr.spring.goods.service.GoodsService;
import kr.spring.goods.vo.GoodsVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GoodsController {
	@Autowired
	private GoodsService goodsService;

	//자바빈 초기화
	@ModelAttribute
	public GoodsVO initCommand() {
		return new GoodsVO();
	}

	/*===================================
	 * 				상품 목록 호출
	 *==================================*/
	//요청
	@GetMapping("/goods/list")
	public String getlist(@RequestParam(defaultValue="1")int pageNum,
						  @RequestParam(defaultValue="1")int order,
						  @RequestParam(defaultValue="1")int dcate_num,
						  String keyfield, String keyword, Model model){
		
		log.debug("<<상품 목록 - category>> : " + dcate_num);
		log.debug("<<게시판 목록 - order>> :" + order);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("dcate_num", dcate_num);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);

		//전체, 검색 레코드 수
		int count = goodsService.selectRowCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,
										count,20,10,"list","&dcate_num="+dcate_num+"&order="+order);
		
		List<GoodsVO> list = null;
		if(count > 0) {
			map.put("order", order);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list= goodsService.selectList(map);
		}
		 
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		
		
		return "goodsList";
	}	
	/*===================================
	 * 				상품 상세
	 *==================================*/
	//
	
	
	/*===================================
	 * 				상품 등록
	 *==================================*/
	//등록 폼 호출
	@GetMapping("/goods/write")
	public String form() {
		return "goodsWrite";
	}
	//등록 폼에서 전송된 데이터 처리
	
	@PostMapping("/goods/write")
	public String submit(@Valid GoodsVO goodsVO,
						BindingResult result,
						HttpServletRequest request,
						HttpSession session,
						Model model)throws IllegalStateException, IOException{
		log.debug("<<상품 등록>> : " + goodsVO);
		
		if(goodsVO.getUpload()==null || goodsVO.getUpload().isEmpty()) {
			result.rejectValue("upload", "fileNotFound");
		}
		
		//유효성 체크 결과가 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			for(FieldError f : result.getFieldErrors()) {
				log.debug("에러 필드 : " + f.getField());
			}
			
			return form();
		}
		//상품 번호
		goodsVO.setItem_photo(FileUtil.createFile(request,goodsVO.getUpload()));
		
		//글쓰기
		goodsService.insertGoods(goodsVO);
		
		model.addAttribute("message","성공적으로 상품이 등록되었습니다.");
		model.addAttribute("uri",request.getContextPath()+"/goods/list");
		
		return "common/resultAlert";
	}
	
}