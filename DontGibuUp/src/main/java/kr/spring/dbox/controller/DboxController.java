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
		log.debug("<<목록 : >>");
		return "dboxList";
	}
	/*===================================
	 * 		기부박스 제안하기
	 *==================================*/
	@GetMapping("/dbox/propose")
	public String proposeForm() {
		log.debug("<<기부박스 제안하기 : >>");
		return "dboxPropose";
	}
	/*===================================
	 * 		기부박스 제안하기 : 1.나의 다짐
	 *==================================*/
	@GetMapping("/dbox/propose/step1")
	public String proposeStep1() {
		log.debug("<<기부박스 제안하기 - step1 : >>");
		return "dboxProposeStep1";
	}
	
	
	/*===================================
	 * 		기부박스 제안하기 : 2.팀 및 계획 작성
	 *==================================*/
	@GetMapping("/dbox/propose/step2")
	public String proposeStep2() {
		log.debug("<<기부박스 제안하기 - step2 : >>");
		return "dboxProposeStep2";
	}
	
	
	/*===================================
	 * 		기부박스 제안하기 : 3.내용 작성
	 *==================================*/
	@GetMapping("/dbox/propose/step3")
	public String proposeStep3() {
		log.debug("<<기부박스 제안하기 - step3 : >>");
		return "dboxProposeStep3";
	}
	
	
	/*===================================
	 * 		기부박스 제안하기 : 제안 완료
	 *==================================*/
	@GetMapping("/dbox/propose/end")
	public String proposeEnd() {
		log.debug("<<기부박스 제안하기 - end : >>");
		return "dboxProposeEnd";
	}
	
	
}