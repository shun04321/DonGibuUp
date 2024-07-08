package kr.spring.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.spring.goods.dao.GoodsMapper;
import kr.spring.goods.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GoodsController {
	@Autowired
	private GoodsMapper goodsMapper;

	//자바빈 초기화
	@ModelAttribute
	public GoodsVO initCommand() {
		return new GoodsVO();
	}

	/*===================================
	 * 		기본 출력
	 *==================================*/
	//요청
	@GetMapping("/goods/list")
	public String list() {
		log.debug("<<목록>>");
		return "goodsList";
	}	
}