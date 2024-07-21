package kr.spring.subscription.service;

import java.util.List;

import kr.spring.subscription.vo.SubscriptionVO;

public interface SubscriptionService {
    // 정기기부 번호 생성
    public long getSub_num();

    // 정기기부 등록
    public void insertSubscription(SubscriptionVO subscriptionVO);

    // 정기기부 종료
    public void endSubscription(long sub_num);

    // 정기기부 조회
    public SubscriptionVO getSubscription(long sub_num);

    // 정기기부 삭제
    public void deleteSubscription(long sub_num);

    // 오늘 날짜 구하기
    public int getTodayDate();
    
    String getToken();
    //정기 결제 날짜가 오늘인 구독 목록 반환
    public List<SubscriptionVO> getSubscriptionByDay(int today);
    
    //정기기부 중단
    public void updateSub_status(long sub_num);
    
    //정기기부 목록
    public List<SubscriptionVO> getSubscriptionByMem_numWithCategories(long mem_num);
    
    public int getSubscriptionCount(long mem_num);
}
