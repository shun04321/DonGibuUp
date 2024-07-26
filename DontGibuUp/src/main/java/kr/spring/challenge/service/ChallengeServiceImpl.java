package kr.spring.challenge.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.challenge.dao.ChallengeMapper;
import kr.spring.challenge.vo.ChallengeChatVO;
import kr.spring.challenge.vo.ChallengeFavVO;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeReviewVO;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.challenge.vo.ChallengeVerifyRptVO;
import kr.spring.challenge.vo.ChallengeVerifyVO;
import kr.spring.member.service.MemberService;
import kr.spring.point.service.PointService;
import kr.spring.point.vo.PointVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService{
	
	@Autowired
	ChallengeMapper challengeMapper;
	@Autowired
	PointService pointService;
	@Autowired
	MemberService memberService;

	//*챌린지 개설*//
	@Override
	public void insertChallenge(ChallengeVO chalVO,ChallengeJoinVO joinVO,ChallengePaymentVO payVO,ChallengeChatVO chatVO) {
		chalVO.setChal_num(challengeMapper.selectChal_num());
		challengeMapper.insertChallenge(chalVO);
		joinVO.setChal_num(chalVO.getChal_num());
		joinVO.setChal_joi_num(challengeMapper.selectChal_joi_num());
		challengeMapper.insertChallengeJoin(joinVO);
		payVO.setChal_joi_num(joinVO.getChal_joi_num());
		challengeMapper.insertChallengePayment(payVO);
		if(chalVO.getChal_public() == 0) {
			chatVO.setChal_num(chalVO.getChal_num());	
			long chat_id = challengeMapper.selectChat_id();
			chatVO.setChat_id(chat_id);
			challengeMapper.insertChallengeChat(chatVO);
		}				
	}	

	@Override
	public List<ChallengeVO> selectList(Map<String, Object> map) {
		return challengeMapper.selectList(map);
	}
	
	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return challengeMapper.selectRowCount(map);
	}

	@Override
	public ChallengeVO selectChallenge(Long chal_num) {
		return challengeMapper.selectChallenge(chal_num);
	}

	@Override
	public void updateChallenge(Long chal_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteChallenge(Long chal_num) {
		challengeMapper.deleteChallenge(chal_num);
	}

	@Override
	public void deleteChalPhoto(Long chal_num) {
		// TODO Auto-generated method stub
		
	}
	
	//참가 인원수 조회
	@Override
	public int countCurrentParticipants(long chal_num) {
	    return challengeMapper.countCurrentParticipants(chal_num);
	}
	
	//*챌린지 참가*//
    @Override
    public void insertChallengeJoin(ChallengeJoinVO chalJoinVO, ChallengePaymentVO chalPayVO) {
    	chalJoinVO.setChal_joi_num(challengeMapper.selectChal_joi_num());
        challengeMapper.insertChallengeJoin(chalJoinVO);
        chalPayVO.setChal_joi_num(chalJoinVO.getChal_joi_num());
        challengeMapper.insertChallengePayment(chalPayVO);
    }

    @Override
    public List<ChallengeJoinVO> selectChallengeJoinList(Map<String,Object> map) {
        return challengeMapper.selectChallengeJoinList(map);
    }

	@Override public ChallengeJoinVO selectChallengeJoin(Long chal_num) {
		return challengeMapper.selectChallengeJoin(chal_num); 
	}
    
	@Override public void deleteChallengeJoin(Long chal_joi_num) {
		challengeMapper.deleteChallengeJoin(chal_joi_num); 
	}
    
    //챌린지 ID로 챌린지 참가 데이터 삭제
    @Override
    public void deleteChallengeJoinsByChallengeId(Long chal_num) {
        challengeMapper.deleteChallengeJoinsByChallengeId(chal_num);
    }
    
    //리더 여부 확인
    @Override
    public boolean isChallengeLeader(Long chal_num, Long mem_num) {
        ChallengeVO challenge = challengeMapper.selectChallenge(chal_num);
        return challenge != null && challenge.getMem_num() == mem_num;
    }
    //리더 chal_joi_num 확인하기
    @Override
	public Long selectLeaderJoiNum(Long chal_num) {
		return challengeMapper.selectLeaderJoiNum(chal_num);
	}
    
    //후기 작성 여부
    @Override
    public ChallengeReviewVO selectChallengeReviewByMemberAndChallenge(Map<String, Object> map) {
        return challengeMapper.selectChallengeReviewByMemberAndChallenge(map);
    }
    
    //*챌린지 결제*//
    @Override
    public void insertChallengePayment(ChallengePaymentVO chalPayVO) {
        challengeMapper.insertChallengePayment(chalPayVO);
    }
    
    //*챌린지 인증*//
    @Override
    public void insertChallengeVerify(ChallengeVerifyVO chalVerifyVO) {
        challengeMapper.insertChallengeVerify(chalVerifyVO);
    }
    
	@Override
	public Integer selectChallengeVerifyListRowCount(Map<String, Object> map) {
		return challengeMapper.selectChallengeVerifyListRowCount(map);
	}
	
    @Override
    public List<ChallengeVerifyVO> selectChallengeVerifyList(Map<String, Object> map) {
        return challengeMapper.selectChallengeVerifyList(map);
    }

	@Override 
	public ChallengeVerifyVO selectChallengeVerify(Long chal_ver_num) {
		return challengeMapper.selectChallengeVerify(chal_ver_num); 
	}
	
    @Override
    public void updateChallengeVerify(ChallengeVerifyVO challengeVerify) {
        challengeMapper.updateChallengeVerify(challengeVerify);
    }
	 
    @Override
    public void deleteChallengeVerify(Long chal_ver_num) {
        challengeMapper.deleteChallengeVerify(chal_ver_num);
    }
    
    //주별 인증 횟수 확인
    @Override
    public int countWeeklyVerify(Long chal_joi_num, LocalDate startDate, int weekNumber) {
        Map<String, Object> params = new HashMap<>();
        params.put("chal_joi_num", chal_joi_num);
        params.put("startDate", startDate.toString());
        params.put("weekNumber", weekNumber);
        return challengeMapper.countWeeklyVerify(params);
    }

	@Override
	public List<ChallengeJoinVO> selectJoinMemberList(Map<String,Object> map) {
		return challengeMapper.selectJoinMemberList(map);
	}

	@Override
	public Integer selectJoinMemberRowCount(Map<String, Object> map) {
		return challengeMapper.selectJoinMemberRowCount(map);
	}

	//*챌린지 후기*//
    @Override
    public void insertChallengeReview(ChallengeReviewVO chalReviewVO) {
        chalReviewVO.setChal_rev_num(challengeMapper.selectChal_rev_num());
        challengeMapper.insertChallengeReview(chalReviewVO);
        
        // 포인트 지급
        givePointsForReview(chalReviewVO);
    }
    
    @Override
    public List<ChallengeReviewVO> selectChallengeReviewList(Long chal_num) {
        return challengeMapper.selectChallengeReviewList(chal_num);
    }   
    
    @Override
    public ChallengeReviewVO selectChallengeReview(Long chal_rev_num) {
        return challengeMapper.selectChallengeReview(chal_rev_num);
    }   
    
    @Override
    public void updateChallengeReview(ChallengeReviewVO chalReviewVO) {
        challengeMapper.updateChallengeReview(chalReviewVO);
    }
    
    @Override
    public void deleteChallengeReview(Long chal_rev_num) {
        challengeMapper.deleteChallengeReview(chal_rev_num);
    }

	@Override
	public Integer selectChallengeJoinListRowCount(Map<String, Object> map) {
		return challengeMapper.selectChallengeJoinListRowCount(map);
	}
	
	//후기 작성 시 포인트 지급
	public void givePointsForReview(ChallengeReviewVO review) {
	    int pointAmount = 500;//지급할 포인트 양
	    long memNum = review.getMem_num();
	    long chalNum = review.getChal_num();
	    
	    //포인트 로그 추가
	    PointVO pointVO = new PointVO(13, pointAmount, memNum); //(이벤트 타입, 포인트 양, 회원 번호)
	    pointService.insertPointLog(pointVO);
	    
	    //회원 포인트 업데이트
	    memberService.updateMemPoint(pointVO);
	    
	    log.info("회원 ID {}에게 챌린지 ID {}에 대한 후기 작성으로 {} 포인트를 지급했습니다.", memNum, chalNum, pointAmount);
	}
	
	//*챌린지 채팅*//
	//챌린지 채팅 메시지 넣기
	@Override
	public void insertChallengeChat(ChallengeChatVO chatVO) {
		//chal_chat 테이블에 레코드 삽입
		long chat_id = challengeMapper.selectChat_id();
		chatVO.setChat_id(chat_id);
		challengeMapper.insertChallengeChat(chatVO);
		//chal_chat_read 테이블에 레코드 삽입
		Map<String,Object> map = new HashMap<>();
		map.put("chal_num", chatVO.getChal_num());
		map.put("chat_id",chat_id);
		for(ChallengeJoinVO vo:challengeMapper.selectJoinMemberList(map)) {			
			map.put("mem_num", vo.getMem_num());
			challengeMapper.insertChatRead(map);
		}		
	}
	
	//챌린지 채팅 메시지 읽기
	@Override
	public List<ChallengeChatVO> selectChallengeChat(Map<String, Object> map) {
		//채팅 읽음 표시처리
		challengeMapper.deleteChatRead(map);
		//채팅 내용 불러오기				 
		return challengeMapper.selectChallengeChat(map);
	}
	
	//챌린지 채팅방 삭제
	@Override
	public void deleteChallengeChat(Long chal_num) {
		//챌린지 채팅 읽기 기록 삭제
		challengeMapper.deleteChalChatRead(chal_num);
		//챌린지 채팅 전체 삭제
		challengeMapper.deleteChallengeChat(chal_num);		
	}
	
	//*챌린지 좋아요*//
    @Override
    public ChallengeFavVO selectFav(ChallengeFavVO fav) {
        return challengeMapper.selectFav(fav);
    }

    @Override
    public Integer selectFavCount(Long chal_num) {
        return challengeMapper.selectFavCount(chal_num);
    }

    @Override
    public void insertFav(ChallengeFavVO fav) {
        challengeMapper.insertFav(fav);
    }

    @Override
    public void deleteFav(ChallengeFavVO fav) {
        challengeMapper.deleteFav(fav);
    }

	@Override
	public void updateVerifyStatus(Map<String,Long> map) {
		challengeMapper.updateVerifyStatus(map);		
	}

	@Override
	public void insertVerifyReport(ChallengeVerifyRptVO chalVerifyRptVO) {
		challengeMapper.insertVerifyReport(chalVerifyRptVO);
		challengeMapper.updateReportStatus(chalVerifyRptVO.getChal_ver_num());
	}
	
	//*챌린지 스케줄러*//
    @Override
    public void processTodayExpiredChallenges() {
        try {
            LocalDate today = LocalDate.now();
            List<ChallengeVO> todayExpiredChallenges = challengeMapper.getTodayExpiredChallenges(today);

            for (ChallengeVO challenge : todayExpiredChallenges) {
                try {
                    processChallenge(challenge);
                } catch (Exception e) {
                    // 개별 챌린지 처리 중 예외가 발생한 경우
                    log.error("챌린지 ID {} 처리 중 오류 발생: {}", challenge.getChal_num(), e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            // 메서드 전체에서 예외가 발생한 경우
            log.error("오늘 종료된 챌린지 처리 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    private void processChallenge(ChallengeVO challenge) {
        try {
            //각 챌린지에 대한 종료 작업
        	long chal_num = challenge.getChal_num();
        	
            //1. 참가자에게 환급 포인트 지급
        	refundPointsToUsers(chal_num);

            //2. 단체 채팅방 삭제
            challengeMapper.deleteChalChatRead(chal_num);
            challengeMapper.deleteChallengeChat(chal_num);

            log.info("챌린지 ID {}의 채팅방 및 채팅 기록 삭제 완료", chal_num);
        } catch (Exception e) {
            log.error("챌린지 ID {} 처리 중 예외 발생: {}", challenge.getChal_num(), e.getMessage(), e);
        }
    }
    
    //챌린지 종료시 환급 포인트 지급
    @Override
    public void refundPointsToUsers(Long chal_num) {
        Map<String, Object> map = new HashMap<>();
        map.put("chal_num", chal_num);
        List<ChallengeJoinVO> joinList = challengeMapper.selectJoinMemberList(map);

        for (ChallengeJoinVO join : joinList) {
            ChallengeVO challenge = challengeMapper.selectChallenge(join.getChal_num());
            join.setChal_sdate(challenge.getChal_sdate());
            join.setChal_edate(challenge.getChal_edate());
            join.setChal_fee(challenge.getChal_fee().longValue());
            join.setChal_freq(challenge.getChal_freq());

            int returnPoint = calculateReturnPoint(join);

            challengeMapper.insertRefundPoints(join.getMem_num(), 14, returnPoint);//이벤트 타입 (챌린지 환급)
        }
    }

    private int calculateReturnPoint(ChallengeJoinVO challengeJoin) {
        Long chal_fee = challengeJoin.getChal_fee();
        int achieveRate = calculateAchieveRate(challengeJoin);

        if (achieveRate == 100) {
            return (int) (chal_fee * 0.95);
        } else {
            return (int) (achieveRate / 100.0 * chal_fee);
        }
    }

    private int calculateAchieveRate(ChallengeJoinVO challengeJoin) {
        Long chal_joi_num = challengeJoin.getChal_joi_num();
        int chal_freq = challengeJoin.getChal_freq();

        Map<String, Object> verifyMap = new HashMap<>();
        verifyMap.put("chal_joi_num", chal_joi_num);
        List<ChallengeVerifyVO> verifyList = challengeMapper.selectChallengeVerifyList(verifyMap);

        long successCount = verifyList.stream().filter(v -> v.getChal_ver_status() == 0).count();

        String sdate = challengeJoin.getChal_sdate();
        String edate = challengeJoin.getChal_edate();

        LocalDate startDate = LocalDate.parse(sdate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(edate, DateTimeFormatter.ISO_LOCAL_DATE);
        long totalWeeks = ChronoUnit.WEEKS.between(startDate, endDate) + 1;

        long totalCount = totalWeeks * chal_freq;

        return totalCount > 0 ? (int) ((double) successCount / totalCount * 100) : 0;
    }
}