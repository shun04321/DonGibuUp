package kr.spring.goods.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {

    void insertPaymentInfo(@Param("merchantUid") String merchantUid,
                           @Param("amount") int amount,
                           @Param("cardNumber") String cardNumber,
                           @Param("expiry") String expiry,
                           @Param("birth") String birth,
                           @Param("pwd2digit") String pwd2digit);

    void insertRefundInfo(@Param("impUid") String impUid,
                          @Param("amount") int amount,
                          @Param("reason") String reason);

}