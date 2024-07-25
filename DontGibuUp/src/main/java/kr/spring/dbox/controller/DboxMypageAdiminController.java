package kr.spring.dbox.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.dbox.service.DboxService;
import kr.spring.dbox.vo.DboxVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DboxMypageAdiminController {
	@Autowired
	private DboxService dboxService;
	
	/*===================================
	 * 		MyPage
	 *==================================*/
	
	/*===================================
	 * 		제안한 기부박스
	 *==================================*/
    @GetMapping("/dbox/myPage/dboxMyPropose")
    public String dboxMyPropose() {
    	log.debug("<<MyPage - 제안한 기부박스>> : ");
    	
        return "dboxMyPropose";
    }	
    
    /*===================================
     * 		기부박스 기부내역
     *==================================*/
    @GetMapping("/dbox/myPage/dboxMyDonation")
    public String dboxMyDonation() {
    	log.debug("<<MyPage - 기부박스 기부내역>> : ");
    	
    	return "dboxMyDonation";
    }	
    
	/*===================================
	 * 		Admin
	 *==================================*/
    
    /*===================================
     * 		기부박스 관리
     *==================================*/
    @GetMapping("/admin/dboxAdmin")
    public String dboxAdmin(/*@RequestParam(defaultValue = "1" ) int pageNum,
							@RequestParam(defaultValue = "1") int rowCount,
							@RequestParam(defaultValue = "1") int order,
							//@RequestParam(defaultValue = "") String dbox_status,
							String keyfield, String keyword,HttpSession session,Model model*/) {
    	log.debug("<<관리자 페이지 - 기부박스 관리>> : ");

		/*Map<String, Object> map = new HashMap<String, Object>();
		if (keyword != null && keyword.equals("")) {
			keyword = null;
		}

		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("order", order);

		//전체, 검색 레코드수
		int count = dboxService.selectAdminListCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 20, 10, "dboxAdmin", "&order=" + order);

		List<DboxVO> list = null;
		if (count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			list = dboxService.selectAdminList(map);
		}

		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());*/
    	
        return "dboxAdmin";
    }	
}