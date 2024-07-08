package kr.spring.category.vo;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DonationCategoryVO {
	private long dcate_num;
	@NotBlank
	private String dcate_name;
	@NotBlank
	private String dcate_charity;
	private String dcate_icon;
	@NotBlank
	private String dcate_content;
	private MultipartFile upload;	//파일
}
