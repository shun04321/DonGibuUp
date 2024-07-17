package kr.spring.goods.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentVO {
    private String impUid;         // Iamport 결제 고유 번호
    private String merchantUid;    // 상점에서 생성한 고유 주문번호
    private int amount;            // 결제 금액
    private long memNum;           // 회원 번호
    private Date payDate;         // 결제 날짜
    private int payStatus;         // 결제 상태 (0: 결제 완료, 1: 결제 취소)
}