package kr.spring.challenge.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.spring.category.service.CategoryService;
import kr.spring.challenge.service.ChallengeService;
import kr.spring.category.vo.ChallengeCategoryVO;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChallengeAjaxController {
	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private CategoryService categoryService;
	
	/*==========================
	 *  챌린지 목록
	 *==========================*/
	@GetMapping("/challenge/addlist")
	@ResponseBody
	public Map<String,Object> getList(@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="1") int rowCount,@RequestParam(defaultValue="1") int order,
			  String chal_type,String freqOrder,String keyfield,String keyword){
		log.debug("chal_type : "+chal_type);
		Map<String,Object> map = new HashMap<>();
		//map에 검색할 자기계발 카테고리 넣기
		List<ChallengeCategoryVO> categories = categoryService.selectChalCateList();
		map.put("categories", categories);
				
		//총 챌린지 개수
		int count = challengeService.selectRowCount(map);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,rowCount);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		map.put("chal_type", chal_type);
		map.put("freqOrder", freqOrder);
		
		List<ChallengeVO> list = null;
		if(count > 0) {
			list = challengeService.selectList(map);			
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapJson = new HashMap<>();
		mapJson.put("freqOrder", freqOrder);
		mapJson.put("count", count);
		mapJson.put("list", list);
		
		return mapJson;
	}
	
	/*==========================
	 *  챌린지 결제
	 *==========================*/
	//IamportClient 초기화 하기
	private IamportClient impClient; 
	
	private String apiKey = "7830478768772156";
	private String secretKey = "T5qKYEXltMHNhzZaGSBZYQ4iYQ2Woor1VleODHJ2mhXZ4FBma0OA2e0Z4XSj3CNYY4ZPk4XBy4naYmla";
	
	@PostConstruct
	public void initImp() {
		this.impClient = new IamportClient(apiKey,secretKey);
	}
	
	//리더 결제 정보 검증하기
	@PostMapping("/challenge/paymentVerify/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid,HttpSession session) throws IamportResponseException, IOException{        
		IamportResponse<Payment> payment = impClient.paymentByImpUid(imp_uid);
        
		//로그인 여부 확인하기
		MemberVO member = (MemberVO) session.getAttribute("user");
		
		//세션에 저장된 결제 금액 가져오기
		ChallengeVO challengeVO = (ChallengeVO) session.getAttribute("challengeVO");
		long expectedAmount = challengeVO.getChal_fee();
		
		//실 결제 금액 가져오기
		long paidAmount = payment.getResponse().getAmount().longValue();
		
		//예정 결제 금액과 실 결제 금액 비교하기
		if(expectedAmount != paidAmount || member == null) {
			//결제 취소 요청하기
			CancelData cancelData = new CancelData(imp_uid, true);
			impClient.cancelPaymentByImpUid(cancelData);
		}
		return payment;
	}
	
	//챌린지 개설,신청 및 리더 결제 정보 저장하기
	@PostMapping("/challenge/payAndEnroll")
	@ResponseBody
	public Map<String,String> saveChallengeInfo(@RequestBody Map<String, Object> data, 
			HttpSession session, HttpServletRequest request) throws IllegalStateException, IOException{
		String odImpUid = (String) data.get("od_imp_uid");
	    int chalPayPrice = (Integer) data.get("chal_pay_price");
	    int chalPoint = (Integer) data.get("chal_point");
	    int chalPayStatus = (Integer) data.get("chal_pay_status");
	    int dcateNum = (Integer) data.get("dcate_num");
		
		Map<String,String> mapJson = new HashMap<>();
		
		//세션 데이터 가져오기
		MemberVO member = (MemberVO) session.getAttribute("user");
		ChallengeVO challenge = (ChallengeVO) session.getAttribute("challengeVO");
		
		if(member == null) {
			mapJson.put("result", "logout");
		}else {
			//챌린지 개설하기
			//대표 사진 업로드 및 파일 저장
			challenge.setChal_photo(FileUtil.createFile(request, challenge.getUpload()));
			challengeService.insertChallenge(challenge);
			session.removeAttribute("challengeVO");
			
			//챌린지 참가하기
			ChallengeJoinVO challengeJoinVO = new ChallengeJoinVO();
			challengeJoinVO.setMem_num(member.getMem_num());
			challengeJoinVO.setChal_num(challenge.getChal_num());
			challengeJoinVO.setDcate_num(dcateNum);
			challengeService.insertChallengeJoin(challengeJoinVO);
			
			//챌린지 결제 정보 저장하기
			ChallengePaymentVO challengePaymentVO = new ChallengePaymentVO();
			challengePaymentVO.setMem_num(member.getMem_num());
			challengePaymentVO.setChal_pay_price(chalPayPrice);
			challengePaymentVO.setChal_point(chalPoint);
			//Q. 결제 테이블에 chal_joi_num이 꼭 필요한지?
			//ChallengeJoinVO db_join = challengeService.
			//challengePaymentVO.setChal_joi_num();
			
			//challengeService.insertChallengePayment(challengePaymentVO);
			
			mapJson.put("result", "success");
		}
		
		return mapJson;
	}
}
