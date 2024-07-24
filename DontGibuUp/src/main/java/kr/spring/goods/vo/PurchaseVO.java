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
    private long mem_num;
    private long item_num;
    private String item_name;
    private String imp_uid;
    private String Merchant_uid;
    private int amount;
    private int pay_price;
    private String buyer_name;
    private String buyer_email;
    private String item_photo;
    private Date payDate;
    private int pay_status;
    private String deliveryStatus;
    
    
    private List<CartVO> cart_items = new ArrayList<>();
    
    public List<CartVO> getCart_items() {
        return cart_items;
    }
    public void setCart_items(List<CartVO> cart_items) {
        this.cart_items = cart_items;
    }
    public long getMemNum() {
        return mem_num;
    }

    public void setMemNum(long memNum) {
        this.mem_num = memNum;
    }
}