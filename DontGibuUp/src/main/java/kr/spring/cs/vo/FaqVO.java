package kr.spring.cs.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FaqVO {
	private long faq_num;
	private int faq_category;
	private String faq_question;
	private String faq_answer;
}
