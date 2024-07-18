package kr.spring.cs.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.spring.cs.service.CSService;
import kr.spring.cs.vo.InquiryVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.point.vo.PointVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CSController {
	@Autowired
	CSService csService;

	//자바빈(VO) 초기화
	@ModelAttribute
	public InquiryVO initCommand() {
		return new InquiryVO();
	}

	@GetMapping("/cs/faqlist")
	public String faqlist() {
		return "faqlist";
	}

	//1:1문의 폼
	@GetMapping("/cs/inquiry")
	public String form(Model model) {
		Map<String, String> inquiry_category = new HashMap<String, String>();
		inquiry_category.put("", "카테고리 선택");
		inquiry_category.put("0", "정기기부");
		inquiry_category.put("1", "기부박스");
		inquiry_category.put("2", "챌린지");
		inquiry_category.put("3", "굿즈샵");
		inquiry_category.put("4", "기타");

		model.addAttribute("inquiry_category", inquiry_category);

		return "inquiryForm";
	}

	//1:1문의 작성
	@PostMapping("/cs/inquiry")
	public String inquiry(@Valid InquiryVO inquiryVO, BindingResult result, HttpServletRequest request,
			HttpSession session, Model model) throws IllegalStateException, IOException {
		if (result.hasErrors()) {
			Map<String, String> inquiry_category = new HashMap<String, String>();
			inquiry_category.put("", "카테고리 선택");
			inquiry_category.put("0", "정기기부");
			inquiry_category.put("1", "기부박스");
			inquiry_category.put("2", "챌린지");
			inquiry_category.put("3", "굿즈샵");
			inquiry_category.put("4", "기타");

			model.addAttribute("inquiry_category", inquiry_category);
			return "inquiryForm";
		}

		//회원번호 세팅
		MemberVO user = (MemberVO) session.getAttribute("user");

		inquiryVO.setMem_num(user.getMem_num());
		
		//파일 업로드
		inquiryVO.setInquiry_filename(FileUtil.createFile(request, inquiryVO.getUpload()));

		log.debug("<<1:1문의 작성>> : " + inquiryVO);

		//문의작성
		csService.insertInquiry(inquiryVO);

		model.addAttribute("accessTitle", "문의 전송 완료");
		model.addAttribute("accessMsg", "1:1문의가 전송되었습니다");
		model.addAttribute("accessBtn", "마이페이지에서 확인하기");
		model.addAttribute("accessUrl", request.getContextPath() + "/member/myPage/inquiry");

		return "inquiryResultPage";
	}
	
	
	
	//관리자 1:1문의 목록
	@GetMapping("admin/cs/inquiry")
	public String memberPoint(@RequestParam(defaultValue="1") int pageNum,
							  @RequestParam(defaultValue="1") int status,
							  HttpSession session,
							  Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		
		log.debug("<<관리자 문의 목록 - status>> : " + status);
		
		
		//레코드 수
		int count = csService.selectInquiryListCount(map);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum, count, 30, 10, "adminInquiry", "&status=" + status);
		
		List<InquiryVO> list = null;
		if (count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			list = csService.selectInquiryList(map);
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		
		return "adminInquiry";
	}
	
	//관리자 1:1 문의 답변 폼 (문의 상세보기)
	@GetMapping("/admin/cs/inquiry/reply")
	public String replyInquiryForm(@RequestParam long inquiry_num, Model model) {
		InquiryVO inquiry = csService.selectInquiryDetail(inquiry_num);
		
		log.debug("<<문의 상세 - inquiry_num>> : " +inquiry_num);
		log.debug("<<문의 상세>> : " +inquiry);
		
		model.addAttribute("inquiry", inquiry);
		
		return "adminInquiryReply";
	}
	
	@PostMapping("/admin/cs/inquiry/reply")
	public String replyInquiry(@Valid InquiryVO inquiryVO, BindingResult result, Model model) {
		if (inquiryVO.getInquiry_reply() == null || inquiryVO.getInquiry_reply().equals("")) {
			result.rejectValue("inquiry_reply", "notBlank.inquiry_reply");
			return "adminInquiryReply";
		}
		
		//답변 수정
		csService.replyInquiry(inquiryVO);
		
		model.addAttribute("inquiry", inquiryVO);
		return "redirect:/admin/cs/inquiry/reply?inquiry_num=" + inquiryVO.getInquiry_num();
	}
	
	@GetMapping("/admin/cs/inquiry/modifyForm")
	public String modifyFormAjax(@RequestParam long inquiry_num, Model model) {
		InquiryVO inquiry = csService.selectInquiryDetail(inquiry_num);

		model.addAttribute("inquiry", inquiry);
		
		return "admin/cs/inquiryModifyForm";
	}
}
