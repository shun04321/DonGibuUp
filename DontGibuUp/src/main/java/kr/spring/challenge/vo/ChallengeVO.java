package kr.spring.challenge.vo;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
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
	@NotNull
	private int chal_public;//0:공개,1:비공개
	@NotNull
	private int chal_type;
	@NotBlank
	private String chal_title;
	private String chal_content;
	private MultipartFile upload;//파일
	private String chal_photo;//파일명
	@NotBlank
	private String chal_verify;
	@NotNull
	private Integer chal_freq;
	@NotEmpty
	private String chal_sdate;
	@NotEmpty
	private String chal_edate;
	@Range(min=1000,max=200000)
	private Integer chal_fee;
	@Range(min=1)
	private Integer chal_max;
	private Date chal_rdate;
	private String chal_ip;
	
	private String mem_nick;
	private String mem_photo;
}
