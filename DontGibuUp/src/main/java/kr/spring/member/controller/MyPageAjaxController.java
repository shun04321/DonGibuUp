package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyPageAjaxController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/member/myPage/modifyMemPhoto")
	@ResponseBody
	public Map<String, Object> processMemPhoto(MemberVO memberVO, HttpSession session) {
		
		// ajax 통신이기 때문에 인터셉터를 쓰면 안됨(로그인 화면이 중간에 보여질 수 없음)
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if (user == null) {
			mapAjax.put("result", "logout");
		} else {
			memberVO.setMem_num(user.getMem_num());
			memberService.updateMemPhoto(memberVO);
			
			mapAjax.put("result", "success");
		}
		
		return mapAjax;
	}
}
