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
    private long item_num;
    private String item_name;
    private String imp_uid;
    private String Merchant_uid;
    private long amount;
    private String status;
    private String buyer_name;
    private String buyer_email;
    private String item_photo;
    private Date payDate;
    private int payStatus;
    private String deliveryStatus;  // 배송 상태 필드 추가
    
    
    public long getMemNum() {
        return memNum;
    }

    public void setMemNum(long memNum) {
        this.memNum = memNum;
    }
}