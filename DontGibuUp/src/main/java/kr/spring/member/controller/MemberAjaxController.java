package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.cs.vo.FaqVO;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberAjaxController {
	@Autowired
	private MemberService memberService;

	/*===============================
	   		이메일 중복 체크
	================================*/
	@GetMapping("/member/checkEmail")
	@ResponseBody
	public Map<String, String> checkEmail(MemberVO memberVO) {
		log.debug("<<이메일 중복체크>> : " + memberVO.getMem_email());
		
		Map<String, String> mapAjax = new HashMap<String, String>();

		MemberVO existingMember = memberService.selectMemberByEmail(memberVO.getMem_email());

		if (existingMember != null) {
			mapAjax.put("result", "exist");
		} else {
			mapAjax.put("result", "notExist");
		}

		return mapAjax;
	}

	/*===============================
		닉네임 중복 체크
	================================*/
	@GetMapping("/member/checkNick")
	@ResponseBody
	public Map<String, String> checkNick(MemberVO memberVO, HttpSession session) {
		log.debug("<<닉네임 중복체크>> : " + memberVO.getMem_nick());
		
		Map<String, String> mapAjax = new HashMap<String, String>();

		MemberVO existingMember = memberService.selectMemberByNick(memberVO.getMem_nick());

		if (existingMember != null) {
			mapAjax.put("result", "exist");
		} else {
			mapAjax.put("result", "notExist");
		}
		
		//회원정보 수정 페이지에서 중복체크 할 경우
		MemberVO user = (MemberVO)session.getAttribute("user");
		if (user != null && existingMember != null) {
			if (user.getMem_nick().equals(memberVO.getMem_nick())) {
				mapAjax.put("result", "notChanged");
			}
		}
		log.debug("<<닉네임 중복체크>> : " + mapAjax.toString());
		return mapAjax;
	}

	/*===================================
	 * 			관리자 회원관리
	 *==================================*/
	//회원 정지
	@ResponseBody
	@PostMapping("/admin/suspendMember")
	public Map<String, Object> suspendMemberAjax(long mem_num, HttpSession session) {
		Map<String, Object> mapAjax = new HashMap<String, Object>();

		MemberVO user = (MemberVO) session.getAttribute("user");

		if (user == null) {
			// 로그인 안 됨
			mapAjax.put("result", "logout");
		} else if (user.getMem_status() != 9) {
			mapAjax.put("result", "noAuthority");
		} else {
			MemberVO memberVO = new MemberVO();
			memberVO.setMem_num(mem_num);
			memberVO.setMem_status(1);
			memberService.updateMemStatus(memberVO);
			mapAjax.put("result", "success");
		}

		return mapAjax;
	}
	
	//회원 사용
	@ResponseBody
	@PostMapping("/admin/activateMember")
	public Map<String, Object> activateMemberAjax(long mem_num, HttpSession session) {
		Map<String, Object> mapAjax = new HashMap<String, Object>();

		MemberVO user = (MemberVO) session.getAttribute("user");

		if (user == null) {
			// 로그인 안 됨
			mapAjax.put("result", "logout");
		} else if (user.getMem_status() != 9) {
			mapAjax.put("result", "noAuthority");
		} else {
			MemberVO memberVO = new MemberVO();
			memberVO.setMem_num(mem_num);
			memberVO.setMem_status(2);
			memberService.updateMemStatus(memberVO);
			mapAjax.put("result", "success");
		}

		return mapAjax;
	}
}
