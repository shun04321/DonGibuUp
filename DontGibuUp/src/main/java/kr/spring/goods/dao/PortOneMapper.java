package kr.spring.goods.dao;

import org.apache.ibatis.annotations.Mapper;
import kr.spring.goods.vo.PaymentVO;

@Mapper
public interface PortOneMapper {
    void insertPaymentInfo(PaymentVO paymentVO);
}
