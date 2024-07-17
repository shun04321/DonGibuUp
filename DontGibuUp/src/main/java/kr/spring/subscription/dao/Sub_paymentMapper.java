package kr.spring.subscription.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.subscription.vo.Sub_paymentVO;

@Mapper
public interface Sub_paymentMapper {
	//결제 예약 번호 생성
	@Select("SELECT sub_payment_seq.nextval FROM dual")
	public long getSub_payment_num();
	//결제 예약 내역 저장
	@Insert("INSERT INTO sub_payment(sub_pay_num,mem_num,sub_num,sub_price,sub_pay_date) VALUES(#{sub_pay_num},#{mem_num},#{sub_num},#{sub_price},#{sub_pay_date})")
	public void insertSub_payment(Sub_paymentVO subpaymentVO);
	//결제 예약 내역 가져오기
	//예약된 결제 상태 변경

}
