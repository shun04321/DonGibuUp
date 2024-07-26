package kr.spring.challenge.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.challenge.vo.ChallengeChatVO;
import kr.spring.challenge.vo.ChallengeFavVO;
import kr.spring.challenge.vo.ChallengeJoinVO;
import kr.spring.challenge.vo.ChallengePaymentVO;
import kr.spring.challenge.vo.ChallengeReviewVO;
import kr.spring.challenge.vo.ChallengeVO;
import kr.spring.challenge.vo.ChallengeVerifyRptVO;
import kr.spring.challenge.vo.ChallengeVerifyVO;

@Mapper
public interface ChallengeMapper {
	
	//*챌린지 개설*//
	@Select("SELECT challenge_seq.nextval FROM dual")
	public Long selectChal_num(); 
	public void insertChallenge(ChallengeVO chalVO);
	public List<ChallengeVO> selectList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public ChallengeVO selectChallenge(Long chal_num);
	public void updateChallenge(Long chal_num);
	public void deleteChallenge(Long chal_num);
	public void deleteChalPhoto(Long chal_num);
	//참가 인원수 조회
    @Select("SELECT COUNT(*) FROM chal_join WHERE chal_num = #{chal_num}")
    public int countCurrentParticipants(long chal_num);
	
	//*챌린지 참가*//
	@Select("SELECT chal_join_seq.nextval FROM dual")
	public Long selectChal_joi_num();
    public void insertChallengeJoin(ChallengeJoinVO chalJoinVO);
    public Integer selectChallengeJoinListRowCount(Map<String,Object> map);
    public List<ChallengeJoinVO> selectChallengeJoinList(Map<String,Object> map);
    public ChallengeJoinVO selectChallengeJoin(Long chal_num);
    //챌린지 참가 회원 목록
    public Integer selectJoinMemberRowCount(Map<String,Object> map);
    public List<ChallengeJoinVO> selectJoinMemberList(Map<String,Object> map);    
    public void deleteChallengeJoin(Long chal_joi_num);
    //챌린지 리더 참가 정보 불러오기
    @Select("SELECT MIN(chal_joi_num) FROM chal_join WHERE chal_num=#{chal_num}")
    public Long selectLeaderJoiNum(Long chal_num);
    //챌린지 ID로 챌린지 참가 데이터 삭제
    public void deleteChallengeJoinsByChallengeId(Long chal_num);
    //후기 작성 여부
    @Select("SELECT * FROM CHAL_REVIEW WHERE chal_num = #{chal_num} AND mem_num = #{mem_num}")
    public ChallengeReviewVO selectChallengeReviewByMemberAndChallenge(Map<String, Object> map);
    
    //*챌린지 결제*//
    public void insertChallengePayment(ChallengePaymentVO chalPayVO);
    
    //*챌린지 인증*//
    public void insertChallengeVerify(ChallengeVerifyVO chalVerifyVO);
    @Select("SELECT COUNT(*) FROM chal_verify WHERE chal_joi_num=#{chal_joi_num}")
    public Integer selectChallengeVerifyListRowCount(Map<String,Object> map);
    public List<ChallengeVerifyVO> selectChallengeVerifyList(Map<String, Object> map);
    public List<ChallengeVerifyVO> selectChallengeVerifyListPage(Map<String, Object> map);
    public ChallengeVerifyVO selectChallengeVerify(Long chal_ver_num);
    public void updateChallengeVerify(ChallengeVerifyVO challengeVerify);
    public void deleteChallengeVerify(Long chal_ver_num);    
    //주별 인증 횟수 확인
    public int countWeeklyVerify(Map<String, Object> params);
    //챌린지 인증 상태 변경
    @Update("UPDATE chal_verify SET chal_ver_status=#{chal_ver_status} WHERE chal_ver_num=#{chal_ver_num}")
    public void updateVerifyStatus(Map<String,Long> map);
    
    //*챌린지 인증 제보*//
    //챌린지 인증 제보
    @Select("INSERT INTO chal_verify_rpt (report_mem_num,chal_ver_num,reported_joi_num) VALUES (#{report_mem_num},#{chal_ver_num},#{reported_joi_num})")
    public void insertVerifyReport(ChallengeVerifyRptVO chalVerifyRptVO);
    //챌린지 인증 제보상태 변경
    @Update("UPDATE chal_verify SET chal_ver_report=1 WHERE chal_ver_num=#{chal_ver_num}")
    public void updateReportStatus(Long chal_ver_num);
    //각 챌린지의 인증별 제보된 개수
    @Select("SELECT COUNT(*) FROM chal_verify_rpt WHERE chal_ver_num=#{chal_ver_num}")
    public Integer selectReportedVerifyCount(Long chal_ver_num);
    //각 챌린지의 회원별 제보된 총 인증 개수
    @Select("SELECT COUNT(DISTINCT chal_ver_num) FROM chal_verify_rpt WHERE reported_joi_num=#{reported_joi_num}")
    public Integer selectReportedMemberCount(Long chal_joi_num);
    
    //*챌린지 후기*//
    @Select("SELECT chal_review_seq.nextval FROM dual")
    public Long selectChal_rev_num();
    public void insertChallengeReview(ChallengeReviewVO chalReviewVO);
    public List<ChallengeReviewVO> selectChallengeReviewList(Long chal_num);       
    public ChallengeReviewVO selectChallengeReview(Long chal_rev_num);   
    public void updateChallengeReview(ChallengeReviewVO chalReviewVO);
    public void deleteChallengeReview(Long chal_rev_num);	
    
    //*챌린지 채팅*//
    //채팅 메시지 번호 생성
    @Select("SELECT chal_chat_seq.nextval FROM dual")
    public Long selectChat_id();
    //채팅 메시지 등록
    public void insertChallengeChat(ChallengeChatVO chalChatVO);
    //읽지 않은 채팅 기록 저장
    @Insert("INSERT INTO chal_chat_read (chal_num,chat_id,mem_num) VALUES (#{chal_num},#{chat_id},#{mem_num})")
    public void insertChatRead(Map<String,Object> map);
    //채팅 메시지 읽기
    public List<ChallengeChatVO> selectChallengeChat(Map<String,Object> map);
    //읽은 채팅 기록 삭제
    @Delete("DELETE FROM chal_chat_read WHERE chal_num=#{chal_num} AND mem_num=#{mem_num}")
    public void deleteChatRead(Map<String,Object> map);
    //챌린지 종료시 채팅기록(chal_chat_read) 삭제
    @Delete("DELETE FROM chal_chat_read WHERE chal_num=#{chal_num}")
    public void deleteChalChatRead(Long chal_num);
    //챌린지 종료시 채팅(chal_chat) 삭제
    @Delete("DELETE FROM chal_chat WHERE chal_num=#{chal_num}")
    public void deleteChallengeChat(Long chal_num);
    
    //*챌린지 좋아요*//
    @Select("SELECT * FROM chal_fav WHERE chal_num=#{chal_num} AND mem_num=#{mem_num}")
    public ChallengeFavVO selectFav(ChallengeFavVO fav);
    @Select("SELECT COUNT(*) FROM chal_fav WHERE chal_num=#{chal_num}")
    public Integer selectFavCount(Long chal_num);
    @Insert("INSERT INTO chal_fav (chal_num, mem_num) VALUES (#{chal_num}, #{mem_num})")
    public void insertFav(ChallengeFavVO fav);
    @Delete("DELETE FROM chal_fav WHERE chal_num=#{chal_num} AND mem_num=#{mem_num}")
    public void deleteFav(ChallengeFavVO fav);
    
    //*챌린지 스케줄러*//
    //오늘 종료된 챌린지 조회
    @Select("SELECT * FROM challenge WHERE chal_edate = #{today}")
    public List<ChallengeVO> getTodayExpiredChallenges(LocalDate today);
    //참가상태 업데이트
    @Update("UPDATE CHAL_JOIN SET chal_joi_status = 1 WHERE chal_num = #{chal_num}")
    public void updateChallengeJoinStatusToCompleted(@Param("chal_num") long chal_num);
    //챌린지 종료시 환급 포인트 지급
    @Insert("INSERT INTO point_log (point_num, mem_num, pevent_type, point_amount, point_date) VALUES (point_log_seq.NEXTVAL, #{mem_num}, #{pevent_type}, #{pevent_amount}, SYSDATE)")
    public void insertRefundPoints(@Param("mem_num") Long mem_num, @Param("pevent_type") int pevent_type, @Param("pevent_amount") int pevent_amount);
}