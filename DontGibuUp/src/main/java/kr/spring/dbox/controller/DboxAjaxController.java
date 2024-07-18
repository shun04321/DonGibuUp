package kr.spring.dbox.controller;

import java.util.Collections;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.dbox.service.DboxService;
import kr.spring.dbox.vo.DboxVO;
import kr.spring.dbox.vo.DboxValidationGroup_2;
import kr.spring.dbox.vo.DboxValidationGroup_3;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DboxAjaxController {
	@Autowired
	private DboxService dboxService;


	/*========================================
	 * 	목록
	 *========================================*/
	@GetMapping("/dbox/dboxList")
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(defaultValue="1" )int pageNum,
									   @RequestParam(defaultValue="1") int rowCount,
									   HttpSession session){
		log.debug("<<목록 - pageNum : >>" + pageNum);
		log.debug("<<목록 - rowCount : >>" + rowCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		//총 글의 개수
		int count = dboxService.selectListCount();
		
		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum, count, rowCount);//페이지 표시는 하지 않고 start 번호 end 번호를 연산해줌
		map.put("start", page.getStartRow());//DboxMapper.xml의 selectListReply의 rnum #{start}로 전달
		map.put("end", page.getEndRow());//DboxMapper.xml의 selectListReply의 rnum #{end}로 전달
		
		List<DboxVO> list = null;
		if(count > 0) {
			list = dboxService.selectList(map);
		}else {
			list = Collections.emptyList();//null일 경우 빈 배열로 인식되게 세팅
		}
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("count",count);
		mapJson.put("list", list);
		
		return mapJson;
	}
}