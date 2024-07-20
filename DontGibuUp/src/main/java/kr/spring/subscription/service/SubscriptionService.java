package kr.spring.subscription.service;

import kr.spring.subscription.vo.SubscriptionVO;

public interface SubscriptionService {
    // 정기기부 번호 생성
    long getSub_num();

    // 정기기부 등록
    void insertSubscription(SubscriptionVO subscriptionVO);

    // 정기기부 종료
    void endSubscription(long sub_num);

    // 정기기부 조회
    SubscriptionVO getSubscription(long sub_num);

    // 정기기부 삭제
    void deleteSubscription(long sub_num);

    // 오늘 날짜 구하기
    String getTodayDateString();
    
    String getToken();
}
