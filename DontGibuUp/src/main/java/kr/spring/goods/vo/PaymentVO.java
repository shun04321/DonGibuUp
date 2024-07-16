package kr.spring.goods.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVO {
    private String merchantUid;
    private int amount;
    private String status;
    private String cardNumber;
    private String expiry;
    private String birth;
    private String pwd2digit;
}
