package kr.spring.challenge.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChallengeVO {
	private long chal_num;
	private long mem_num;
	private int chal_person;//0:개인,1:단체
	private int chal_public;//0:공개,1:비공개
	private int chal_type;
	private String chal_title;
	private String chal_content;
	private String chal_photo;
	private String chal_verify;
	private int chal_freq;
	private String chal_sdate;
	private String chal_edate;
	private int chal_fee;
	private int chal_max;
	private Date chal_rdate;	
}
