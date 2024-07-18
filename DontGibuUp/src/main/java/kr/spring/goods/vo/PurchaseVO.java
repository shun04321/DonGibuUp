package kr.spring.goods.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseVO {
    private int purchaseNum;
    private long memNum;
    private String odImpUid;
    private int payPrice;
    private Date payDate;
    private int payStatus;
}