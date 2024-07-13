package kr.spring.challenge.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChallengeAjaxController {
	@Autowired
	private ChallengeService challengeService;
	
	/*==========================
	 *  챌린지 목록
	 *==========================*/
	@GetMapping("/challenge/addlist")
	@ResponseBody
	public Map<String,Object> getList(@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="1") int rowCount){
		Map<String,Object> map = new HashMap<>();
		//map에 검색할 자기계발 카테고리 넣기
		
		
		//총 챌린지 개수
		int count = challengeService.selectRowCount(map);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,rowCount);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		
		List<ChallengeVO> list = null;
		if(count > 0) {
			list = challengeService.selectList(map);			
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = new HashMap<>();
		mapJson.put("count", count);
		mapJson.put("list", list);
		
		return mapJson;
	}
	
	//IamportClient 초기화 하기
	private IamportClient impClient; 
	
	private String apiKey = "7830478768772156";
	private String secretKey = "T5qKYEXltMHNhzZaGSBZYQ4iYQ2Woor1VleODHJ2mhXZ4FBma0OA2e0Z4XSj3CNYY4ZPk4XBy4naYmla";
	
	@PostConstruct
	public void initImp() {
		this.impClient = new IamportClient(apiKey,secretKey);
	}
	
	//결제 정보 검증하기
	@PostMapping("/challenge/paymentVerify/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException{
		IamportResponse<Payment> payment = impClient.paymentByImpUid(imp_uid);
		return payment;
	}
}
