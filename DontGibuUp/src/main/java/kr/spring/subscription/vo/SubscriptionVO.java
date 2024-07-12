package kr.spring.subscription.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubscriptionVO {
	private long sub_num;
	private long mem_num;
	private long dcate_num;
	private String sub_name;
	private boolean sub_annoy;
	private int sub_price;
	private String sub_ndate;
	private int sub_status;
	private String sub_method;
	private String easypay_method;
}
