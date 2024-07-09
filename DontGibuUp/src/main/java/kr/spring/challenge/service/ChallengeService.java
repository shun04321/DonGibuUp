package kr.spring.challenge.service;

import java.util.List;
import java.util.Map;

import kr.spring.challenge.vo.ChallengeVO;

public interface ChallengeService {
	//챌린지 개설
	public void insertChallenge(ChallengeVO chalVO);
	public List<ChallengeVO> selectList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public ChallengeVO selectChallenge(Long chal_num);
	public void updateChallenge(Long chal_num);
	public void deleteChallenge(Long chal_num);
	public void deleteChalPhoto(Long chal_num);
	
	
	//챌린지 신청 및 결제, 현황
	
	//챌린지 인증
	
	//챌린지 톡방
	
	//챌린지 후기
	
	//챌린지...
}
