package kr.spring.subscription.service;

import kr.spring.subscription.vo.Sub_paymentVO;

public interface Sub_paymentService {
	//결제 예약
	public void insertSub_payment(Sub_paymentVO subpaymentVO);
	//결제 예약 내역 가져오기
	//예약된 결제 상태 변경
	//sub_pay_num 생성
	public long getSub_payment_num();
}
