package kr.spring.challenge.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.challenge.dao.ChallengeMapper;
import kr.spring.challenge.vo.ChallengeVO;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService{
	@Autowired
	ChallengeMapper challengeMapper;
	
	@Override
	public void insertChallenge(ChallengeVO chalVO) {
		challengeMapper.insertChallenge(chalVO);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChallenge(Long chal_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteChallenge(Long chal_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteChalPhoto(Long chal_num) {
		// TODO Auto-generated method stub
		
	}

}
