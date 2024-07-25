package kr.spring.dbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.dbox.service.DboxService;
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
    public String dboxAdmin() {
    	log.debug("<<관리자 페이지 - 기부박스 관리>> : ");
    	
        return "dboxAdmin";
    }	
}