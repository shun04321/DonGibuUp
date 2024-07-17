package kr.spring.challenge.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.category.service.CategoryService;
import kr.spring.category.vo.ChallengeCategoryVO;
import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.challenge.service.ChallengeService;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.challenge.vo.ChallengeVerifyVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    
    @Autowired
	private CategoryService categoryService;

    @ModelAttribute("challengeVO")
    public ChallengeVO initChallengeVO() {
        return new ChallengeVO();
    }
    @ModelAttribute("challengeJoinVO")
    public ChallengeJoinVO initChallengeJoinVO() {
        return new ChallengeJoinVO();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /*==========================
     *  챌린지 개설
     *==========================*/
    //챌린지 개설 폼
    @GetMapping("/challenge/write")
    public String form() {
        return "challengeWrite";
    } 

    //챌린지 개설 (챌린지 개설 유효성 검사 확인 후, 세션에 저장)
    @PostMapping("/challenge/write")
    public String checkValidation(@Valid ChallengeVO challengeVO, BindingResult result, 
                                  HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
        log.debug("<<챌린지 개설 정보 확인>> : " + challengeVO);

        // 유효성 체크
        if (result.hasErrors()) {
            return form();
        }
        
		//회원번호
		MemberVO member = (MemberVO) session.getAttribute("user");
		challengeVO.setMem_num(member.getMem_num());
		//ip
		challengeVO.setChal_ip(request.getRemoteAddr());
		//챌린지 종료일 계산
        challengeVO.calculateChalEdate();
        //대표 사진 업로드 및 파일 저장
      	challengeVO.setChal_photo(FileUtil.createFile(request, challengeVO.getUpload()));
        
        session.setAttribute("challengeVO", challengeVO);

        return "redirect:/challenge/leaderJoin";
    }
    
    //챌린지 개설 목록
    @GetMapping("/challenge/list")
    public String list(Model model) {
    	List<ChallengeCategoryVO> categories = categoryService.selectChalCateList();
		model.addAttribute("categories", categories);
        return "challengeList";
    }
    @GetMapping("/challenge/pastList")
    public String pastList(Model model) {
    	List<ChallengeCategoryVO> categories = categoryService.selectChalCateList();
		model.addAttribute("categories", categories);
        return "challengePastList";
    }

    //챌린지 개설 상세
    @GetMapping("/challenge/detail")
    public ModelAndView chalDetail(@RequestParam("chal_num") long chal_num, HttpSession session) {
        ChallengeVO challenge = challengeService.selectChallenge(chal_num);
        MemberVO member = (MemberVO) session.getAttribute("user");

        boolean isJoined = false;
        if (member != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("chal_num", chal_num);
            map.put("mem_num", member.getMem_num());
            List<ChallengeJoinVO> joinList = challengeService.selectChallengeJoinList(map);
            // Check if the user is specifically joined to this challenge
            isJoined = joinList.stream().anyMatch(join -> join.getChal_num() == chal_num);
        }

        //참여금을 포맷팅
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedFee = numberFormat.format(challenge.getChal_fee());

        ModelAndView mav = new ModelAndView("challengeView");
        mav.addObject("challenge", challenge);
        mav.addObject("isJoined", isJoined);
        mav.addObject("formattedFee", formattedFee); // 포맷팅된 참여금 추가

        return mav;
    }

    /*==========================
     *  챌린지 참가
     *==========================*/
    //챌린지 참가 폼
    @GetMapping("/challenge/join/write")
    public String joinForm(@RequestParam("chal_num") long chal_num, HttpSession session, Model model) {
        ChallengeVO challengeVO = challengeService.selectChallenge(chal_num);
        List<DonationCategoryVO> categories = categoryService.selectListNoPage();

        model.addAttribute("categories", categories);
        model.addAttribute("challengeVO", challengeVO);

        ChallengeJoinVO challengeJoinVO = new ChallengeJoinVO();
        challengeJoinVO.setChal_num(chal_num);
        model.addAttribute("challengeJoinVO", challengeJoinVO);

        session.setAttribute("chal_num", chal_num); // 챌린지 번호를 세션에 저장

        return "challengeJoinWrite";
    }
    //챌린지 참가 폼 (리더)
    @GetMapping("/challenge/leaderJoin")
    public String joinForm(Model model,HttpSession session) {
        List<DonationCategoryVO> categories = categoryService.selectListNoPage();
        model.addAttribute("categories", categories);
        
        ChallengeVO vo = (ChallengeVO) session.getAttribute("challengeVO");
        model.addAttribute("challenge", vo);
        
        return "leaderJoinForm";
    }    
    //챌린지 참가 및 결제
    @PostMapping("/challenge/join/write")
    public String join(@Valid @ModelAttribute("challengeJoinVO") ChallengeJoinVO challengeJoinVO, BindingResult result,
                       HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
        log.debug("<<챌린지 신청 확인>> : " + challengeJoinVO);

        //유효성 체크
        if (result.hasErrors()) {
            log.debug("<<유효성 검사 실패>> : " + result.getAllErrors());
            return "challengeJoinWrite";
        }

        //회원번호
        MemberVO member = (MemberVO) session.getAttribute("user");
        if (member == null) {
            log.debug("<<로그인되지 않은 사용자>>");
            model.addAttribute("message", "로그인 후 신청 가능합니다.");
            model.addAttribute("url", request.getContextPath() + "/member/login");
            return "common/resultAlert";
        }
        challengeJoinVO.setMem_num(member.getMem_num());
        // ip
        challengeJoinVO.setChal_joi_ip(request.getRemoteAddr());
        
        log.debug("<<챌린지 신청 확인>> : " + challengeJoinVO);
        
        //챌린지 결제 정보 저장
        ChallengePaymentVO challengePaymentVO = new ChallengePaymentVO();
        challengePaymentVO.setChal_joi_num(challengeJoinVO.getChal_joi_num());
        challengePaymentVO.setMem_num(member.getMem_num());
        challengePaymentVO.setOd_imp_uid(request.getParameter("od_imp_uid"));
        challengePaymentVO.setChal_pay_price(Long.parseLong(request.getParameter("chal_pay_price")));
        challengePaymentVO.setChal_point(0);//사용된 포인트 (기본값 0)
        challengePaymentVO.setChal_pay_status(0);//결제 상태 (0: 결제 완료)
        challengeService.insertChallengePayment(challengePaymentVO);
        
        //챌린지 신청
        challengeService.insertChallengeJoin(challengeJoinVO, challengePaymentVO);
       
        //view에 메시지 추가
        model.addAttribute("message", "챌린지 신청이 완료되었습니다!");
        model.addAttribute("url", request.getContextPath() + "/challenge/list");

        return "common/resultAlert";
    }
    
    
    //챌린지 참가 목록
    @GetMapping("/challenge/join/list")
    public ModelAndView list(@RequestParam(value = "status", defaultValue = "pre") String status,
                             @RequestParam(value = "month", required = false) String month,
                             HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("mem_num", member.getMem_num());
        map.put("status", status);

        //현재 날짜를 기반으로 month가 없는 경우 이번 달로 설정
        LocalDate currentMonth = month != null ? LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")) : LocalDate.now().withDayOfMonth(1);
        String currentMonthString = currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        //이번 달의 챌린지만 필터링
        List<ChallengeJoinVO> list = challengeService.selectChallengeJoinList(map).stream()
            .filter(challenge -> {
                if (challenge.getChal_sdate() == null) {
                    return false;
                }
                return challenge.getChal_sdate().substring(0, 7).equals(currentMonthString);
            })
            .collect(Collectors.toList());

        //각 챌린지에 대한 달성률과 참여금 계산
        List<Map<String, Object>> challengeDataList = list.stream().map(challengeJoin -> {
            Map<String, Object> challengeData = new HashMap<>();
            long chal_joi_num = challengeJoin.getChal_joi_num();
            Map<String, Object> verifyMap = new HashMap<>();
            verifyMap.put("chal_joi_num", chal_joi_num);
            List<ChallengeVerifyVO> verifyList = challengeService.selectChallengeVerifyList(verifyMap);

            //인증 성공 횟수
            long successCount = verifyList.stream().filter(v -> v.getChal_ver_status() == 0).count();

            //전체 주 수 계산
            LocalDate startDate = LocalDate.parse(challengeJoin.getChal_sdate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(challengeJoin.getChal_edate(), DateTimeFormatter.ISO_LOCAL_DATE);
            long totalWeeks = ChronoUnit.WEEKS.between(startDate, endDate) + 1;

            //전체 인증 횟수
            long totalCount = totalWeeks * challengeJoin.getChal_freq();

            //달성률 계산
            int achieveRate = totalCount > 0 ? (int) ((double) successCount / totalCount * 100) : 0;

            //참여금 관련 계산
            Long chal_fee = challengeJoin.getChal_fee();
            int returnPoint = (int) (achieveRate / 100.0 * chal_fee);
            int donaAmount = (int) (chal_fee - returnPoint);

            //숫자를 포맷팅
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

            //챌린지 데이터 추가
            challengeData.put("challengeJoin", challengeJoin);
            challengeData.put("achieveRate", achieveRate);
            challengeData.put("returnPoint", numberFormat.format(returnPoint));
            challengeData.put("donaAmount", numberFormat.format(donaAmount));
            challengeData.put("formattedFee", numberFormat.format(chal_fee));

            return challengeData;
        }).collect(Collectors.toList());

        ModelAndView mav = new ModelAndView("challengeJoinList");
        mav.addObject("challengesByMonth", Map.of(currentMonthString, challengeDataList));
        mav.addObject("status", status);
        mav.addObject("currentMonth", currentMonthString);

        return mav;
    }

    
	/*
	 * @GetMapping("/challenge/joinDetail") public ModelAndView
	 * joinDetail(@RequestParam("chal_joi_num") Long chal_joi_num) { ChallengeJoinVO
	 * challengeJoin = challengeService.selectChallengeJoin(chal_joi_num); return
	 * new ModelAndView("challengeJoinView", "challengeJoin", challengeJoin); }
	 */
    
    //챌린지 참가 삭제
    @PostMapping("/challenge/join/delete")
    public ResponseEntity<String> deleteChallengeJoin(@RequestParam("chal_joi_num") Long chal_joi_num, HttpSession session) {
        try {
            MemberVO member = (MemberVO) session.getAttribute("user");
            ChallengeJoinVO challengeJoin = challengeService.selectChallengeJoin(chal_joi_num);
            
            //참가 정보가 없거나 회원 정보가 일치하지 않는 경우 처리
            if (challengeJoin == null || challengeJoin.getMem_num() != member.getMem_num()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("챌린지 참가 정보가 없거나 권한이 없습니다.");
            }
            
            //리더인 경우 챌린지와 참가 데이터 모두 삭제
            if (challengeService.isChallengeLeader(challengeJoin.getChal_num(), member.getMem_num())) {
                challengeService.deleteChallengeJoinsByChallengeId(challengeJoin.getChal_num());
                challengeService.deleteChallenge(challengeJoin.getChal_num());
            } else {
                // 리더가 아닌 경우 챌린지 참가 데이터만 삭제
                challengeService.deleteChallengeJoin(chal_joi_num);
            }
            return ResponseEntity.ok("챌린지가 취소되었습니다.");
        } catch (Exception e) {
            log.error("챌린지 취소 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("챌린지 취소 중 오류가 발생했습니다.");
        }
    }
    
    /*==========================
     *  챌린지 인증
     *==========================*/
    //챌린지 인증 폼
    @GetMapping("/challenge/verify/write")
    public String verifyForm(@RequestParam("chal_joi_num") long chal_joi_num, Model model) {
        ChallengeVerifyVO challengeVerifyVO = new ChallengeVerifyVO();
        challengeVerifyVO.setChal_joi_num(chal_joi_num);
        model.addAttribute("challengeVerifyVO", challengeVerifyVO);

        ChallengeJoinVO challengeJoin = challengeService.selectChallengeJoin(chal_joi_num);
        if (challengeJoin != null) {
            ChallengeVO challenge = challengeService.selectChallenge(challengeJoin.getChal_num());
            if (challenge != null) {
                model.addAttribute("chal_title", challenge.getChal_title());
                model.addAttribute("chal_verify", challenge.getChal_verify());
            }
        }

        return "challengeVerifyWrite";
    }
    //챌린지 인증
    @PostMapping("/challenge/verify/write")
    public String submitVerify(@Valid @ModelAttribute("challengeVerifyVO") ChallengeVerifyVO challengeVerifyVO, BindingResult result,
                               HttpServletRequest request, HttpSession session, Model model) throws IllegalStateException, IOException {
        log.debug("<<챌린지 인증 등록>> : " + challengeVerifyVO);

        //인증 사진 유효성 검사
        MultipartFile uploadFile = challengeVerifyVO.getUpload();
        if (uploadFile == null || uploadFile.isEmpty()) {
            result.rejectValue("upload", "error.upload", "인증 사진 필수 입력");
        }

        //유효성 체크
        if (result.hasErrors()) {
            ChallengeJoinVO challengeJoin = challengeService.selectChallengeJoin(challengeVerifyVO.getChal_joi_num());
            if (challengeJoin != null) {
                ChallengeVO challenge = challengeService.selectChallenge(challengeJoin.getChal_num());
                if (challenge != null) {
                    model.addAttribute("chal_title", challenge.getChal_title());
                    model.addAttribute("chal_verify", challenge.getChal_verify());
                }
            }
            return "challengeVerifyWrite";
        }

        //회원 번호 설정
        MemberVO member = (MemberVO) session.getAttribute("user");
        challengeVerifyVO.setMem_num(member.getMem_num());

        //인증 사진 업로드 및 파일 저장
        String filename = FileUtil.createFile(request, uploadFile);
        challengeVerifyVO.setChal_ver_photo(filename);

        //등록 날짜 설정
        challengeVerifyVO.setChal_reg_date(new Date(System.currentTimeMillis()));

        //챌린지 인증 등록
        challengeService.insertChallengeVerify(challengeVerifyVO);
        
        //챌린지 번호 가져오기
        //ChallengeJoinVO challengeJoinVO = challengeService.selectChallengeJoin(null);
        
        //view에 메시지 추가
        model.addAttribute("message", "챌린지 인증이 완료되었습니다!");
        model.addAttribute("url", request.getContextPath() + "/challenge/verify/list?chal_joi_num="+ challengeVerifyVO.getChal_joi_num()); 
        // + challengeJoinVO.getChal_num());

        return "common/resultAlert";
    }
    
    //챌린지 인증 목록
    @GetMapping("/challenge/verify/list")
    public ModelAndView verifyList( long chal_joi_num,
                                   @RequestParam(value = "status", defaultValue = "pre") String status,
                                   HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("chal_joi_num", chal_joi_num);

        List<ChallengeVerifyVO> verifyList = challengeService.selectChallengeVerifyList(map);
        ModelAndView mav = new ModelAndView("challengeVerifyList");
        mav.addObject("verifyList", verifyList);
        mav.addObject("chal_joi_num", chal_joi_num);
        mav.addObject("status", status);//추가
        
        //오늘 날짜 추가
        LocalDate today = LocalDate.now();
        mav.addObject("today", today.toString());

        //오늘 날짜의 인증이 있는지 확인
        boolean hasTodayVerify = verifyList.stream()
            .anyMatch(verify -> {
                LocalDate regDate = verify.getChal_reg_date().toLocalDate();
                return regDate.equals(LocalDate.now());
            });
        mav.addObject("hasTodayVerify", hasTodayVerify);

        //챌린지 정보 가져오기
        ChallengeJoinVO challengeJoin = challengeService.selectChallengeJoin(chal_joi_num);
        ChallengeVO challenge = challengeService.selectChallenge(challengeJoin.getChal_num());
        int chalFreq = challengeJoin.getChal_freq();
        String chal_sdate = challengeJoin.getChal_sdate();
        String chal_edate = challengeJoin.getChal_edate();
        
        mav.addObject("challenge", challenge);
        mav.addObject("chalFreq", chalFreq);
        mav.addObject("chal_sdate", chal_sdate);
        mav.addObject("chal_edate", chal_edate);

        if (chal_sdate == null) {
            throw new IllegalArgumentException("챌린지 시작 날짜가 설정되지 않았습니다.");
        }
        
        //인증 성공 횟수
        long successCount = verifyList.stream().filter(v -> v.getChal_ver_status() == 0).count();
        mav.addObject("successCount", successCount);
        
        //인증 실패 횟수
        long failureCount = verifyList.stream().filter(v -> v.getChal_ver_status() == 1).count();
        mav.addObject("failureCount", failureCount);

        //전체 주 수 계산
        LocalDate startDate = LocalDate.parse(chal_sdate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(chal_edate, DateTimeFormatter.ISO_LOCAL_DATE);
        long totalWeeks = ChronoUnit.WEEKS.between(startDate, endDate) + 1; 
        
        //전체 인증 횟수
        long totalCount = totalWeeks * chalFreq; 
        mav.addObject("totalCount", totalCount);
        
        //남은 인증 횟수
        long remainingCount = totalCount - successCount - failureCount;
        mav.addObject("remainingCount", remainingCount);
        
        //달성률 계산 (정수로 변환)
        int achievementRate = (int) ((double) successCount / totalCount * 100);
        mav.addObject("achievementRate", achievementRate);

        return mav;
    }
    
    //챌린지 인증 상세
    @GetMapping("/challenge/verify/detail")
    @ResponseBody
    public String getVerify(@RequestParam("chal_ver_num") long chal_ver_num) {
        ChallengeVerifyVO challengeVerify = challengeService.selectChallengeVerify(chal_ver_num);
        String editForm = "<textarea id='textarea-" + chal_ver_num + "'>" + challengeVerify.getChal_content() + "</textarea>";
        editForm += "<button onclick='updateContent(" + chal_ver_num + ")'>저장</button>";
        editForm += "<button onclick='hideEditForm(" + chal_ver_num + ")'>취소</button>";
        
        return editForm;
    }
    
    //챌린지 인증 수정
    @PostMapping("/challenge/verify/update")
    @ResponseBody
    public ResponseEntity<String> updateVerify(@RequestParam("chal_ver_num") long chal_ver_num,
                                               @RequestParam("chal_content") String chal_content) {
        try {
            ChallengeVerifyVO challengeVerify = new ChallengeVerifyVO();
            challengeVerify.setChal_ver_num(chal_ver_num);
            challengeVerify.setChal_content(chal_content);
            challengeService.updateChallengeVerify(challengeVerify);
            return new ResponseEntity<>("인증 내용이 성공적으로 수정되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("인증 내용 수정 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //챌린지 인증 삭제
    @PostMapping("/challenge/verify/delete")
    public ResponseEntity<String> deleteVerify(@RequestParam("chal_ver_num") long chal_ver_num) {
        try {
            challengeService.deleteChallengeVerify(chal_ver_num);
            return new ResponseEntity<>("인증이 삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("인증 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}