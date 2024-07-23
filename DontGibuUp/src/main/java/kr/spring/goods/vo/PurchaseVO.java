package kr.spring.goods.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.spring.cart.vo.CartVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseVO {
    private Long purchaseNum;
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

    
    private List<CartVO> cart_items = new ArrayList<>();
    
    public List<CartVO> getCart_items() {
        return cart_items;
    }
    public void setCart_items(List<CartVO> cart_items) {
        this.cart_items = cart_items;
    }
    public long getMemNum() {
        return memNum;
    }

    public void setMemNum(long memNum) {
        this.memNum = memNum;
    }
}