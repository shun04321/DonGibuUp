package kr.spring.goods.dao;

import kr.spring.goods.vo.PaymentVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    @Insert("INSERT INTO payment_info (merchant_uid, amount, status, card_number, expiry, birth, pwd2digit) VALUES (#{merchantUid}, #{amount}, #{status}, #{cardNumber}, #{expiry}, #{birth}, #{pwd2digit})")
    void insertPaymentInfo(PaymentVO paymentVO);
}