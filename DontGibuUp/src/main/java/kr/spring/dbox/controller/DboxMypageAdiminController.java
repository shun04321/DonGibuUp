package kr.spring.dbox.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.dbox.service.DboxService;
import kr.spring.dbox.vo.DboxBudgetVO;
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
    public String dboxAdmin(@RequestParam(defaultValue = "1") int pageNum,
							@RequestParam(defaultValue = "1") int order,
							@RequestParam(defaultValue = "") List<Integer> status,
							@RequestParam(required = false) String keyfield,
							@RequestParam(required = false) String keyword,
							HttpSession session,Model model) {
    	log.debug("<<관리자 페이지 - 기부박스 관리 진입>>");

		log.debug("<<관리자 목록 - pageNum : >>" + pageNum);
		log.debug("<<관리자 목록 - order : >>" + order);
		log.debug("<<관리자 목록 - status : >>" + status);
		log.debug("<<관리자 목록 - keyfield : >>" + keyfield);
		log.debug("<<관리자 목록 - keyword : >>" + keyword);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (keyword != null && keyword.equals("")) {
			keyword = null;
		}
		// StringBuilder를 사용하여 문자열을 구성
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < status.size(); i++) {
            sb.append(status.get(i));
            if (i < status.size() - 1) {
                sb.append(",");
            }
        }

        // StringBuilder의 내용을 String으로 변환
        String statusValue = sb.toString();
        log.debug("<<관리자 목록 - statusValue : >>" + statusValue);
		
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("order", order);
		map.put("statusValue", statusValue);
		
		//전체, 검색 레코드수
		int count = dboxService.selectAdminListCount(map);

		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, pageNum, count, 10, 10, "dboxAdmin", "&order=" + order);

		List<DboxVO> list = null;
		if (count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			list = dboxService.selectAdminList(map);
		}

		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
    	
        return "dboxAdmin";
    }
    /*===================================
     * 		기부박스 상태 관리
     *==================================*/
    @GetMapping("/admin/dboxAdminStatus/{dboxNum}")
	public String proposeExample(@PathVariable long dboxNum,Model model,HttpSession session) {
		log.debug("<<관리자 기부박스 상태관리 - dbox_num>> : "+ dboxNum);
    	//멤버정보
    	MemberVO member = (MemberVO) session.getAttribute("user");
    	log.debug("<<관리자 기부박스 상태관리 - member>> : " + member);
    	
    	//기부박스 및 모금계획 불러오기
    	DboxVO dbox = dboxService.selectDbox(dboxNum);
    	List<DboxBudgetVO> dboxBudget = dboxService.selectDboxBudgets(dboxNum);
    	log.debug("<<관리자 기부박스 상태관리 - Dbox>> : " + dbox);
    	log.debug("<<관리자 기부박스 상태관리 - DboxBudget>> : " + dboxBudget);
   
    	//뷰에 전달
    	model.addAttribute("member",member);
    	model.addAttribute("dbox",dbox);
    	model.addAttribute("dboxTotal",dboxService.selecDoantionTotal(dboxNum));
    	model.addAttribute("dboxBudget",dboxBudget);
    	
    	return "dboxAdminStatus";
 
    }
}