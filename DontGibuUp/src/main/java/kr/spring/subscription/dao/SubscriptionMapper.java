package kr.spring.subscription.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.subscription.vo.SubscriptionVO;

@Mapper
public interface SubscriptionMapper {
	//정기기부 등록
	public void insertSubscription(SubscriptionVO subscriptionVO);
	//등록된 정기기부 번호 가져오기
	@Select("SELECT sub_num FROM subscription WHERE mem_num=#{mem_num} AND dcate_num=#{dcate_num}")
	public long getSubscriptionNum(long mem_num, long dcate_num);
	//정기기부 종료
	public void endSubscription(long sub_num);
	//정기기부 수정 (결제일, 기부금)
	
	//정기기부 수정 (결제 수단)
	
	//자신의 정기기부 현황 확인
	
	//모든 ,사용자의 정기기부 현황 확인
}
