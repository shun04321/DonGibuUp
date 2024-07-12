package kr.spring.challenge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeVO;

@Mapper
public interface ChallengeMapper {
	
	//챌린지 개설
	public void insertChallenge(ChallengeVO chalVO);
	public List<ChallengeVO> selectList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public ChallengeVO selectChallenge(Long chal_num);
	public void updateChallenge(Long chal_num);
	public void deleteChallenge(Long chal_num);
	public void deleteChalPhoto(Long chal_num);
	
	//챌린지 참가 및 현황
    public void insertChallengeJoin(ChallengeJoinVO chalJoinVO);
    public List<ChallengeJoinVO> selectChallengeJoinList(Map<String,Object> map);
    //public ChallengeJoinVO selectChallengeJoin(Long chal_joi_num);
    //public void deleteChallengeJoin(Long chal_joi_num);
    //기부 카테고리 목록 가져오기
    @Select("SELECT dcate_num,dcate_name,dcate_charity FROM DONA_CATEGORY")
    List<DonationCategoryVO> selectDonaCategories();
    
	//챌린지 결제
    public void insertChallengePayment(ChallengePaymentVO chalPayVO);
	
	//챌린지 인증
	
	//챌린지 톡방
	
	//챌린지 후기
	
	//챌린지...
}
