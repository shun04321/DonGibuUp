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
import org.springframework.http.ResponseEntity;
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
import kr.spring.challenge.vo.ChallengeVerifyVO;
import kr.spring.member.service.MemberService;
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
	
	@Autowired
	private MemberService memberService;

	/*==========================
	 *  챌린지 목록
	 *==========================*/
	@GetMapping("/challenge/addlist")
	@ResponseBody
	public Map<String,Object> getList(@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="1") int rowCount,@RequestParam(defaultValue="0") int order,
			String chal_type,@RequestParam(defaultValue="") String freqOrder,
			@RequestParam(defaultValue="") String keyword,String chal_sdate,String chal_edate){
		log.debug("chal_type : "+chal_type);
		Map<String,Object> map = new HashMap<>();
		//map에 검색할 자기계발 카테고리 넣기
		List<ChallengeCategoryVO> categories = categoryService.selectChalCateList();
		map.put("categories", categories);

		map.put("chal_type", chal_type);
		map.put("freqOrder", freqOrder);
		map.put("keyword", keyword);
		map.put("order", order);
		map.put("chal_sdate", chal_sdate);
		map.put("chal_edate", chal_edate);

		//총 챌린지 개수
		int count = challengeService.selectRowCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,rowCount);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());

		log.debug("keyword : "+keyword);
		log.debug("order : "+order);
		log.debug("start : "+page.getStartRow());
		log.debug("end : "+page.getEndRow());

		List<ChallengeVO> list = null;
		if(count > 0) {
			list = challengeService.selectList(map);			
		}else {
			list = Collections.emptyList();
		}

		Map<String,Object> mapJson = new HashMap<>();

		mapJson.put("freqOrder", freqOrder);
		mapJson.put("keyword", keyword);
		mapJson.put("order", order);
		mapJson.put("count", count);
		mapJson.put("list", list);

		return mapJson;
	}

	/*==========================
	 *  챌린지 결제
	 *==========================*/
	//IamportClient 초기화 하기
	private IamportClient impClient; 

	private String apiKey = "4015265277142442";
	private String secretKey = "qK84eiR8BNoTMNqgMzuZHpf2CM87DZLcIHSOufjYMhGEWJTrEh7ydoqtvDRcPI1CEdXKM2H9YiVc0Loa";

	@PostConstruct
	public void initImp() {
		this.impClient = new IamportClient(apiKey,secretKey);
	}

	// 결제 정보 검증하기
	@PostMapping("/challenge/paymentVerifyWrite/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> validateIamportWrite(@PathVariable String imp_uid, HttpSession session, HttpServletRequest request)
			throws IamportResponseException, IOException {
		IamportResponse<Payment> payment = impClient.paymentByImpUid(imp_uid);

		// 로그인 여부 확인하기
		MemberVO member = (MemberVO) session.getAttribute("user");

		// 실 결제 금액 가져오기
		long paidAmount = payment.getResponse().getAmount().longValue();

		log.debug("payment: " + payment);

		return payment;
	}

	//챌린지 참가 및 결제 정보 저장
	@PostMapping("/challenge/payAndEnrollWrite")
	@ResponseBody
	public Map<String, String> saveChallengeInfoWrite(@RequestBody Map<String, Object> data, HttpSession session, HttpServletRequest request)
			throws IllegalStateException, IOException {
		String odImpUid = (String) data.get("od_imp_uid");
		int chalPayPrice = (Integer) data.get("chal_pay_price");
		int chalPoint = (Integer) data.get("chal_point");
		int chalPayStatus = (Integer) data.get("chal_pay_status");
		int dcateNum = Integer.parseInt((String) data.get("dcate_num"));
		Long chalNum = Long.parseLong((String) data.get("chal_num"));
		String sdate = (String) data.get("sdate"); //클라이언트에서 전달된 sdate

		log.debug("odImpUid : " + odImpUid);
		log.debug("chalPayPrice : " + chalPayPrice);
		log.debug("chalPoint : " + chalPoint);
		log.debug("chalPayStatus : " + chalPayStatus);
		log.debug("dcateNum : " + dcateNum);
		log.debug("chalNum : " + chalNum);
		log.debug("sdate : " + sdate);

		Map<String, String> mapJson = new HashMap<>();

		//세션 데이터 가져오기
		MemberVO member = (MemberVO) session.getAttribute("user");

		if (member == null) {
			mapJson.put("result", "logout");
		} else {
			//챌린지 참가 정보 저장
			ChallengeJoinVO challengeJoinVO = new ChallengeJoinVO();
			challengeJoinVO.setMem_num(member.getMem_num());
			challengeJoinVO.setDcate_num(dcateNum);
			challengeJoinVO.setChal_num(chalNum);
			challengeJoinVO.setChal_joi_ip(request.getRemoteAddr());

			//챌린지 결제 정보 저장
			ChallengePaymentVO challengePaymentVO = new ChallengePaymentVO();
			challengePaymentVO.setMem_num(member.getMem_num());
			challengePaymentVO.setChal_pay_price(chalPayPrice);
			challengePaymentVO.setChal_point(chalPoint);
			challengePaymentVO.setOd_imp_uid(odImpUid);

			try {
				challengeService.insertChallengeJoin(challengeJoinVO, challengePaymentVO);
				mapJson.put("result", "success");
				mapJson.put("sdate", sdate); //응답에 sdate 포함
			} catch (Exception e) {
				log.error("챌린지 참가 및 결제 정보 저장 중 오류 발생", e);
				mapJson.put("result", "error");
				mapJson.put("message", "챌린지 참가 및 결제 정보 저장 중 오류가 발생했습니다. 관리자에게 문의하세요.");
			}
		}

		return mapJson;
	}

	//결제 정보 검증 (리더)
	@PostMapping("/challenge/paymentVerify/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid,HttpSession session,
			HttpServletRequest request) throws IamportResponseException, IOException{        
		IamportResponse<Payment> payment = impClient.paymentByImpUid(imp_uid);

		//로그인 여부 확인하기
		MemberVO member = (MemberVO) session.getAttribute("user");
		ChallengeVO challenge = (ChallengeVO) session.getAttribute("challengeVO");

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
			//대표 사진 업로드 및 파일 저장
			FileUtil.removeFile(request, challenge.getChal_photo());
		}
		log.debug("payment"+payment);
		return payment;
	}

	//챌린지 개설, 참가, 리더 결제 정보 저장
	@PostMapping("/challenge/payAndEnroll")
	@ResponseBody
	public Map<String,String> saveChallengeInfo(@RequestBody Map<String, Object> data, 
			HttpSession session, HttpServletRequest request) throws IllegalStateException, IOException{
		String odImpUid = (String) data.get("od_imp_uid");
		int chalPayPrice = (Integer) data.get("chal_pay_price");
		int chalPoint = (Integer) data.get("chal_point");
		int chalPayStatus = (Integer) data.get("chal_pay_status");
		int dcateNum = Integer.parseInt((String) data.get("dcate_num"));
		log.debug("odImpUid : "+odImpUid);
		log.debug("chalPayPrice : "+chalPayPrice);
		log.debug("chalPoint : "+chalPoint);
		log.debug("chalPayStatus : "+chalPayStatus);
		log.debug("dcateNum : "+dcateNum);

		Map<String,String> mapJson = new HashMap<>();

		//세션 데이터 가져오기
		MemberVO member = (MemberVO) session.getAttribute("user");
		ChallengeVO challenge = (ChallengeVO) session.getAttribute("challengeVO");

		if(member == null) {
			mapJson.put("result", "logout");
		}else {			
			//챌린지 참가 정보 저장
			ChallengeJoinVO challengeJoinVO = new ChallengeJoinVO();
			challengeJoinVO.setMem_num(member.getMem_num());
			challengeJoinVO.setChal_num(challenge.getChal_num());
			challengeJoinVO.setDcate_num(dcateNum);
			challengeJoinVO.setChal_joi_ip(request.getRemoteAddr());

			//챌린지 결제 정보 저장
			ChallengePaymentVO challengePaymentVO = new ChallengePaymentVO();
			challengePaymentVO.setMem_num(member.getMem_num());
			challengePaymentVO.setChal_pay_price(chalPayPrice);
			challengePaymentVO.setChal_point(chalPoint);
			challengePaymentVO.setOd_imp_uid(odImpUid);

			challengeService.insertChallenge(challenge,challengeJoinVO,challengePaymentVO);

			String sdate = challenge.getChal_sdate();

			session.removeAttribute("challengeVO");

			mapJson.put("result", "success");
			mapJson.put("sdate", sdate);
		}

		return mapJson;
	}

	//챌린지 참가 창 벗어날시 이미지 삭제
	@PostMapping("/challenge/deleteImage")
	public void deleteImg(HttpSession session, HttpServletRequest request) {		
		//세션에 저장된 파일 이름 가져오기
		ChallengeVO challenge = (ChallengeVO) session.getAttribute("challengeVO");

		if (challenge != null) {
			//파일 삭제
			FileUtil.removeFile(request, challenge.getChal_photo());
		}
	}

	/*==========================
	 *  챌린지 인증 상세 (비동기 통신)
	 *==========================*/
	/*
	//본인 챌린지 인증 내역 
	@GetMapping("/challenge/verify/myList")
	@ResponseBody
	public Map<String,Object> verifyList(@RequestParam(defaultValue="1") int pageNum,Long chal_joi_num,int rowCount){
		Map<String,Object> map = new HashMap<>();
		map.put("chal_joi_num", chal_joi_num);

		//총 챌린지 참가 멤버수
		int count = challengeService.selectChallengeVerifyListRowCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,rowCount);

		int start = page.getStartRow();
		int end = page.getEndRow();
		map.put("start", start);
		map.put("end", end);

		List<ChallengeVerifyVO> myList = challengeService.selectChallengeVerifyList(map);

		Map<String,Object> mapJson = new HashMap<>();
		mapJson.put("list", myList);
		mapJson.put("page", page.getPage());

		return mapJson;
	}*/

	//챌린지 참가 회원 목록
	@GetMapping("/challenge/verify/joinMemberList")
	@ResponseBody
	public Map<String,Object> joinMemberList(@RequestParam(defaultValue="1") int pageNum,
			long chal_num,long chal_joi_num,int rowCount){
		Map<String,Object> map = new HashMap<>();
		map.put("chal_num", chal_num);
		map.put("chal_joi_num", chal_joi_num);

		//총 챌린지 참가 멤버수 불러오기
		int memberCount = challengeService.selectJoinMemberRowCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,memberCount,rowCount);

		int start = page.getStartRow();
		int end = page.getEndRow();
		map.put("start", start);
		map.put("end", end);

		log.debug("<<start>> : "+start);
		log.debug("<<end>> : "+end);

		List<ChallengeJoinVO> joinList = challengeService.selectJoinMemberList(map);

		Map<String,Object> mapJson = new HashMap<>();
		mapJson.put("list", joinList);
		mapJson.put("count", memberCount);

		return mapJson;
	}

	//챌린지 인증 현황 불러오기
	@GetMapping("/challenge/verify/verifyMemberList")
	@ResponseBody
	public Map<String,Object> verifyMemberList(@RequestParam(defaultValue="1") int pageNum,
			long chal_joi_num,int rowCount,HttpSession session){
		Map<String,Object> map = new HashMap<>();
		map.put("chal_joi_num", chal_joi_num);

		//총 챌린지 인증 개수
		int count = challengeService.selectChallengeVerifyListRowCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,rowCount);

		int start = page.getStartRow();
		int end = page.getEndRow();
		map.put("start", start);
		map.put("end", end);

		List<ChallengeVerifyVO> verifyList = challengeService.selectChallengeVerifyList(map);

		Map<String,Object> mapJson = new HashMap<>();
		
		//회원의 참가 번호가 로그인한 사람의 참가 번호와 같은지 확인		
		ChallengeJoinVO challengeJoin = challengeService.selectChallengeJoin(chal_joi_num); 
		MemberVO user = (MemberVO) session.getAttribute("user"); 
		long mem_num = user.getMem_num();		
		if(challengeJoin.getMem_num() == mem_num) { 
			mapJson.put("isUser", true);
		}else { 
			mapJson.put("isUser", false);
			//회원 프로필 사진,닉네임
			MemberVO userInfo = memberService.selectMemberDetail(challengeJoin.getMem_num());
			mapJson.put("member", userInfo);
		}
				
		mapJson.put("list", verifyList);
		mapJson.put("count", count);

		return mapJson;
	}
}