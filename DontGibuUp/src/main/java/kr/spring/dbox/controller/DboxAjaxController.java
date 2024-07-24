package kr.spring.dbox.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.dbox.service.DboxService;
import kr.spring.dbox.vo.DboxDonationVO;
import kr.spring.dbox.vo.DboxVO;
import kr.spring.dbox.vo.DboxValidationGroup_2;
import kr.spring.dbox.vo.DboxValidationGroup_3;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.point.service.PointService;
import kr.spring.point.vo.PointVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DboxAjaxController {
	@Autowired
	private DboxService dboxService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PointService pointService;
	@Autowired
	private MemberService memberService;
	/*========================================
	 * 	목록
	 *========================================*/
	@GetMapping("/dbox/dboxList")
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(defaultValue = "1" ) int pageNum,
									   @RequestParam(defaultValue = "1") int rowCount,
									   @RequestParam(defaultValue = "1") int order,
									   @RequestParam(defaultValue = "") String category,
									   @RequestParam(defaultValue = "") String keyfield,
									   @RequestParam(defaultValue = "") String keyword,
									   HttpSession session){
		log.debug("<<목록 - pageNum : >>" + pageNum);
		log.debug("<<목록 - rowCount : >>" + rowCount);
		log.debug("<<목록 - category : >>" + category);
		log.debug("<<목록 - keyfield : >>" + keyfield);
		log.debug("<<목록 - keyword : >>" + keyword);
		log.debug("<<목록 - order : >>" + order);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", category);
		map.put("order", order);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		//총 글의 개수
		int count = dboxService.selectListCount(map);//category,order DboxMapper.xml에 우선 전달
		log.debug("<<목록 - count : >>" + count);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, rowCount,10,"list","&category="+category+"&order="+order);//페이지 표시는 하지 않고 start 번호 end 번호를 연산해줌
		map.put("start", page.getStartRow());//DboxMapper.xml의 selectListReply의 rnum #{start}로 전달
		map.put("end", page.getEndRow());//DboxMapper.xml의 selectListReply의 rnum #{end}로 전달
		
		
		List<DboxVO> list = null;
		if(count > 0) {
			list = dboxService.selectList(map);
		}else {
			list = Collections.emptyList();//null일 경우 빈 배열로 인식되게 세팅
		}
		log.debug("<<목록 - Dbox list : >> : " + list);
		List<DonationCategoryVO> category_list = categoryService.selectListNoPage();
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("count",count);
		mapJson.put("list", list);
		mapJson.put("category_list", category_list);
		mapJson.put("page", page.getPage());
		
		log.debug("<<목록 - JSON : >> : " + mapJson);
		return mapJson;
	}
	/*========================================
	 * 	결제 
	 *========================================*/
	//IamportClient 초기화 하기
	private IamportClient impClient; 

	private String apiKey = "1768802126155655";
	private String secretKey = "7lbuqivNTuXgdJ0ELcC9KH7mo8ruzxAQz6i7NEw72bobO7JIPfH8I07YSYcQUmPypmQg0S3H9XxqM9wQ";

	@PostConstruct
	public void initImp() {
		this.impClient = new IamportClient(apiKey,secretKey);
	}
	//결제 검증
	@PostMapping("/dbox/payment/{imp_uid}")
	@ResponseBody	
	public IamportResponse<Payment> validateIamportWrite(@PathVariable String imp_uid, HttpSession session, HttpServletRequest request)
			throws IamportResponseException, IOException {

		log.debug("<<결제 검증>> - imp_uid: " +imp_uid);
//		log.debug("<<결제 검증>> - amount: " + amount);
		IamportResponse<Payment> payment = impClient.paymentByImpUid(imp_uid);
		// 로그인 여부 확인하기
		MemberVO member = (MemberVO) session.getAttribute("user");
		
		// PG결제 금액 가져오기
		long realPay = payment.getResponse().getAmount().longValue();
		log.debug("<<결제 검증>> - amount: " + realPay);
		
//		if(payAmount != realPay || member==null) {
//			CancelData cancelData = new CancelData(imp_uid, true);
//			impClient.cancelPaymentByImpUid(cancelData);
//		}
		
		log.debug("<<결제 검증>> - payment: " + payment);

		return payment;
	}
	
	
	@PostMapping("/dbox/donation")
	@ResponseBody
	public Map<String, String> dboxDonation(@RequestBody Map<String, Object> data, HttpSession session, HttpServletRequest request){
		log.debug("<<결제정보>> : " + data);
		
		long dbox_num=Long.parseLong((String) data.get("dbox_num"));
		//long pay_price=(Long)data.get("pay_price");
		long price=Long.parseLong((String) data.get("price"));
		int point=Integer.parseInt((String) data.get("point"));
		String imp_uid=(String)data.get("dbox_imp_uid");
		String comment=(String)data.get("comment");
		int status=(Integer)data.get("pay_status");
		int annony=(Integer)data.get("annony");
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		MemberVO member = (MemberVO)session.getAttribute("user");
		if(member == null) {
			mapJson.put("result", "logout");
		}else {
			DboxDonationVO dboxDonationVO = new DboxDonationVO();
			dboxDonationVO.setDbox_num(dbox_num);
			dboxDonationVO.setMem_num(member.getMem_num());
			dboxDonationVO.setDbox_do_price(price);
			dboxDonationVO.setDbox_do_point(point);
			dboxDonationVO.setDbox_imp_uid(imp_uid);
			dboxDonationVO.setDbox_do_comment(comment);
			dboxDonationVO.setDbox_do_status(status);
			dboxDonationVO.setDbox_do_annony(annony);
			
			try {
				dboxService.insertDboxDonation(dboxDonationVO);
				mapJson.put("result", "success");
				log.debug("<<<<<<<<<<<<<<<<결제 성공>>>>>>>>>>>>>>>>>>>");
				//포인트 사용
				PointVO point_revent1 = new PointVO(21, -point, member.getMem_num()); //(포인트 타입, 포인트 양(음수면 -), 포인트 받을 회원 번호)
				
				//포인트 로그 추가
				pointService.insertPointLog(point_revent1);
				
				//member_detail 업데이트
				memberService.updateMemPoint(point_revent1);
				log.debug("<<회원 사용 포인트>> : " + point);
				log.debug("<<회원 보유 포인트(전)>> : " + member.getMem_point());
				member.setMem_point(member.getMem_point()-point);
				log.debug("<<회원 보유 포인트(후)>> : " + member.getMem_point());
			}catch(Exception e) {
				log.error("기부박스 결제 오류 발생",e);
			}
		}
		
		return mapJson;
	}
	
}