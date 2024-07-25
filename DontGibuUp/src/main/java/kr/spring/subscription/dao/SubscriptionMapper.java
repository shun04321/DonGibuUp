package kr.spring.subscription.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.subscription.vo.SubscriptionVO;

@Mapper
public interface SubscriptionMapper {
	//정기기부 번호 생성
	@Select("SELECT subscription_seq.nextval FROM dual")
	public long getSub_num();
	//정기기부 등록
	public void insertSubscription(SubscriptionVO subscriptionVO);
	//등록된 정기기부 정보 가져오기
	@Select("SELECT * FROM subscription WHERE sub_num=#{sub_num}")
	public SubscriptionVO getSubscription(long sub_num);
	//정기기부 종료
	public void endSubscription(long sub_num);
	//결제수단 변경
	public void modifyPayMethod(SubscriptionVO subscriptionVO);
	
	//정기기부 중단
	@Update("UPDATE subscription SET sub_status=1, cancel_date=SYSDATE WHERE sub_num=#{sub_num}")
	public void updateSub_status(long sub_num);
	
	
	//자신이 정기기부 개수
	@Select("SELECT COUNT(*) FROM subscription WHERE mem_num=#{mem_num}")
	public int getSubscriptionCount(long mem_num);
	//자신의 정기기부 현황 확인
	public List<SubscriptionVO> getSubscriptionByMem_num(long mem_num);
	//모든 ,사용자의 정기기부 현황 확인
	
	//정기기부 삭제 (결제수단 등록 실패시)
	@Delete("DELETE FROM subscription WHERE sub_num=#{sub_num}")
	public void deleteSubscription(long sub_num);
	//정기결제를 위한 getToken
	public String getToken();
	
	@Select("SELECT * FROM subscription WHERE sub_ndate = #{today} AND sub_status = 0")
	public List<SubscriptionVO> getSubscriptionByDay(int today);
	
	
}
