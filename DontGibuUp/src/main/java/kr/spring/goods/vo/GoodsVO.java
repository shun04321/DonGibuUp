package kr.spring.goods.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Date;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class GoodsVO {
private long item_num; //상품번호
private long dcate_num;	//카테고리 고유번호
@NotEmpty
private String item_name;
private String item_photo;
@NotEmpty
private String item_detail;
private long item_price;
private long item_stock;
private Date item_reg_date;
private Date item_mdate;
private long item_status;
}
