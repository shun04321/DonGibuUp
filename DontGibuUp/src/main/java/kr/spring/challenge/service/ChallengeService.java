package kr.spring.challenge.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.challenge.vo.ChallengeVerifyVO;

public interface ChallengeService {
	
	//챌린지 개설
	public void insertChallenge(ChallengeVO chalVO,ChallengeJoinVO joinVO,ChallengePaymentVO payVO);
	public List<ChallengeVO> selectList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public ChallengeVO selectChallenge(Long chal_num);
	public void updateChallenge(Long chal_num);
	public void deleteChallenge(Long chal_num);
	public void deleteChalPhoto(Long chal_num);
	
	//챌린지 참가
	public void insertChallengeJoin(ChallengeJoinVO chalJoinVO, ChallengePaymentVO chalPayVO);
	public List<ChallengeJoinVO> selectChallengeJoinList(Map<String,Object> map);
	public ChallengeJoinVO selectChallengeJoin(Long chal_joi_num);
	public Integer selectJoinMemberRowCount(Map<String,Object> map);
	public List<ChallengeJoinVO> selectJoinMemberList(Map<String,Object> map);
	public void deleteChallengeJoin(Long chal_joi_num);
    //챌린지 ID로 챌린지 참가 데이터 삭제
	public void deleteChallengeJoinsByChallengeId(Long chal_num);
    //리더 여부 확인
	public boolean isChallengeLeader(Long chal_num, Long mem_num);
	
	//챌린지 결제
    public void insertChallengePayment(ChallengePaymentVO chalPayVO);
	
	//챌린지 인증
    public void insertChallengeVerify(ChallengeVerifyVO chalVerifyVO);
    public Integer selectChallengeVerifyListRowCount(Map<String,Object> map);
    public List<ChallengeVerifyVO> selectChallengeVerifyList(Map<String, Object> map);
    public ChallengeVerifyVO selectChallengeVerify(Long chal_ver_num);
    public void updateChallengeVerify(ChallengeVerifyVO challengeVerify);
    public void deleteChallengeVerify(Long chal_ver_num);
    int countWeeklyVerify(Long chal_joi_num, LocalDate startDate, int weekNumber);//Integer?
    
	//챌린지 후기	
    
	//챌린지 채팅
	
	//챌린지...
}
