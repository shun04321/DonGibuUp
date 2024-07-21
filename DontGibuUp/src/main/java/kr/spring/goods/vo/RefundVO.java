package kr.spring.goods.vo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RefundVO {
    private int refundNum;
    private long memNum;
    private String impUid;
    private String reason;
    private Date refundDate;
}