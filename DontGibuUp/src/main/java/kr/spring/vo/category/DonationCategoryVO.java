package kr.spring.vo.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DonationCategoryVO {
	private int dcate_num;
	private String dcate_name;
	private String dcate_charity;
	private String dcate_icon;
	private String dcate_content;
}
