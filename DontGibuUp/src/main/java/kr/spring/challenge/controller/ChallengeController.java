package kr.spring.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChallengeController {
	@Autowired
	private ChallengeService challengeService;
	
	@ModelAttribute
	public ChallengeVO initCommand() {
		return new ChallengeVO();
	}
}
