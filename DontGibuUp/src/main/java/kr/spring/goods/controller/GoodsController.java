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
import org.springframework.web.servlet.ModelAndView;


import kr.spring.goods.service.GoodsService;
import kr.spring.goods.util.fileUtil;
import kr.spring.goods.vo.GoodsVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;
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
	// 요청
    @GetMapping("/goods/list")
    public String getlist(@RequestParam(defaultValue="1") int pageNum,
                          @RequestParam(defaultValue="0") int order,
                          @RequestParam(defaultValue="0") int dcate_num,
                          String keyfield, String keyword, Model model) {

        log.debug("<<상품 목록 - category>> : " + dcate_num);
        log.debug("<<상품 목록 - order>> : " + order);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dcate_num", dcate_num);
        map.put("keyfield", keyfield);
        map.put("keyword", keyword);

        // 전체, 검색 레코드 수
        int count = goodsService.selectRowCount(map);

        // 페이지 처리
        PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 20, 10, "list", "&dcate_num=" + dcate_num + "&order=" + order);

        List<GoodsVO> list = null;
        if (count > 0) {
            map.put("order", order);
            map.put("start", page.getStartRow());
            map.put("end", page.getEndRow());

            list = goodsService.selectList(map);
        }

        model.addAttribute("count", count);
        model.addAttribute("list", list);
        model.addAttribute("page", page.getPage());
        model.addAttribute("dcate_num", dcate_num); // dcate_num 값을 뷰로 전달

        return "goodsList";
    }
	
	/*===================================
	 * 				상품 상세
	 *==================================*/
	@GetMapping("/goods/detail")
	public ModelAndView process(long item_num) {
		log.debug("<<게시판 글 상세 - item_num>> :" + item_num);
		
		
		GoodsVO goods = goodsService.detailGoods(item_num);
		
		//제목에 태그를 허용하지 않음
		goods.setItem_name(StringUtil.useNoHTML(goods.getItem_name()));
		
		//내용에 태그를 허용하지 않으면서 줄바꿈 처리(ck에디터 사용시 주석처리해야함)
		//board.setContent( StringUtil.useBrNoHTML(board.getContent()));
		
		return new ModelAndView("goodsView","goods",goods);
	}
	
	
	/*===================================
	 * 				상품 등록(관리자)
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
		
		//세션에서 Member_status 가져오기
		Integer member_status = (Integer)session.getAttribute("member_status");
		
		//Member_status가 9가 아닌경우 접근을 거부
		if(member_status == null || member_status !=9) {
			model.addAttribute("message","관리자만 접근 가능합니다.");
			model.addAttribute("uri","/goods/list");
			return "common/resultAlert";
		}
		
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
		 // 상품 사진 업로드 처리
	    String uploadedFileName = fileUtil.createFile(request, goodsVO.getUpload());
	    goodsVO.setItem_photo(uploadedFileName);

	    goodsService.insertGoods(goodsVO);

	    model.addAttribute("message", "성공적으로 상품이 등록되었습니다.");
	    model.addAttribute("uri", request.getContextPath() + "/goods/list");

	    return "common/resultAlert";
	}
	/*===================================
	 * 			상품 삭제하기(관리자)
	 *==================================*/
	
	
	
	
}