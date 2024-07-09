package kr.spring.challenge.vo;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChallengeVO {
	private long chal_num;
	private long mem_num;
	@NotEmpty
	private int chal_person;//0:개인,1:단체
	@NotEmpty
	private int chal_public;//0:공개,1:비공개
	@NotEmpty
	private int chal_type;
	@NotBlank
	private String chal_title;
	private String chal_content;
	private MultipartFile upload;//파일
	private String chal_photo;//파일명
	@NotBlank
	private String chal_verify;
	@NotEmpty
	private int chal_freq;
	@NotEmpty
	private String chal_sdate;
	@NotEmpty
	private String chal_edate;
	@NotBlank
	private int chal_fee;
	private int chal_max;
	private Date chal_rdate;
	
	private String mem_nick;
	private String mem_photo;
}
