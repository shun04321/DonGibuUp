package kr.spring.subscription.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.member.vo.MemberVO;
import kr.spring.subscription.vo.SubscriptionVO;

@Mapper
public interface SubscriptionMapper {
	//정기기부 등록
	public void insertSubscription(SubscriptionVO subscriptionVO);
	//정기기부 종료
	public void endSubscription(long mem_num, long sub_num);
	//정기기부 수정 (결제일, 기부금)
	
	//정기기부 수정 (결제 수단)
	
	//자신의 정기기부 현황 확인
	
	//모든 사용자의 정기기부 현황 확인
}
