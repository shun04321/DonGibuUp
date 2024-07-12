package kr.spring.payuid.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayuidVO {
	private String pay_uid;
	private int mem_num;
	private String card_nickname;
	private int easypay_method;
}
