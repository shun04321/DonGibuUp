package kr.spring.subscription.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
	//정기기부 수정 (결제일, 기부금)
	
	//정기기부 수정 (결제 수단)
	
	//자신의 정기기부 현황 확인
	
	//모든 ,사용자의 정기기부 현황 확인
	
	//정기기부 삭제 (결제수단 등록 실패시)
	@Delete("DELETE FROM subscription WHERE sub_num=#{sub_num}")
	public void deleteSubscription(long sub_num);
	//정기결제를 위한 getToken
	public String getToken();
	//오늘 날짜 구하기
	public String getTodayDateString();
	
	
}
