package kr.spring.dbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.spring.dbox.dao.DboxMapper;
import kr.spring.dbox.vo.DboxVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DboxController {
	@Autowired
	private DboxMapper DboxMapper;

	//자바빈 초기화
	@ModelAttribute
	public DboxVO initCommand() {
		return new DboxVO();
	}

	/*===================================
	 * 		기본 출력
	 *==================================*/
	//요청
	@GetMapping("/dbox/list")
	public String listForm() {
		log.debug("<<목록>>");
		return "dboxList";
	}	
}