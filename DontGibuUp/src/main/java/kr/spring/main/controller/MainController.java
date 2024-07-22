package kr.spring.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.member.vo.MemberVO;
import kr.spring.notify.service.NotifyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@Autowired
	NotifyService notifyService;
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";
	}
	
	@GetMapping("/main/main")
	public String main(HttpSession session, Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		
		if (user != null) {
			int unreadCount = notifyService.countUnreadNot(user.getMem_num());
			
			model.addAttribute("unreadCount", unreadCount);			
		}
		
		return "main";//Tiles의 설정명
	}
	@GetMapping("/main/admin")
	public String adminMain() {
		return "admin";//Tiles의 설정명
	}
}








