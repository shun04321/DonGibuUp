package kr.spring.challenge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.category.vo.DonationCategoryVO;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.challenge.vo.ChallengeVerifyVO;

@Mapper
public interface ChallengeMapper {
	
	//챌린지 개설
	@Select("SELECT challenge_seq.nextval FROM dual")
	public Long selectChal_num(); 
	public void insertChallenge(ChallengeVO chalVO);
	public List<ChallengeVO> selectList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public ChallengeVO selectChallenge(Long chal_num);
	public void updateChallenge(Long chal_num);
	public void deleteChallenge(Long chal_num);
	public void deleteChalPhoto(Long chal_num);
	
	//챌린지 참가
	@Select("SELECT chal_join_seq.nextval FROM dual")
	public Long selectChal_joi_num();
    public void insertChallengeJoin(ChallengeJoinVO chalJoinVO);
    public List<ChallengeJoinVO> selectChallengeJoinList(Map<String,Object> map);
    public ChallengeJoinVO selectChallengeJoin(Long chal_joi_num);
    public void deleteChallengeJoin(Long chal_joi_num);
    //기부 카테고리 목록 가져오기
    @Select("SELECT dcate_num,dcate_name,dcate_charity FROM DONA_CATEGORY")
    List<DonationCategoryVO> selectDonaCategories();
    //챌린지 ID로 챌린지 참가 데이터 삭제
    public void deleteChallengeJoinsByChallengeId(Long chal_num);
    
	//챌린지 결제
    public void insertChallengePayment(ChallengePaymentVO chalPayVO);
	
	//챌린지 인증
    public void insertChallengeVerify(ChallengeVerifyVO chalVerifyVO);
    public List<ChallengeVerifyVO> selectChallengeVerifyList(Map<String, Object> map);
    //public ChallengeVerifyVO selectChallengeVerify(Long chal_ver_num);
    //주별 인증 횟수 확인
    int countWeeklyVerifications(Map<String, Object> params);
    
	//챌린지 후기
    
	//챌린지 채팅	
    
	//챌린지...
}
