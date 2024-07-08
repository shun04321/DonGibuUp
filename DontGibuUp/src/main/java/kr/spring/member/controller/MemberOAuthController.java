package kr.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.member.service.MemberOAuthService;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.KakaoInfo;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberOAuthController {
	@Autowired
	private MemberOAuthService memberOauthService;

	@Autowired
	private MemberService memberService;

	@Value("${kakao.client_id}")
	private String client_id;

	@Value("${kakao.redirect_uri}")
	private String redirect_uri;

	@Value("${kakao.client_secret}")
	private String client_secret;




}