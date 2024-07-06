package kr.spring.member.vo;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
	private long mem_num;
	private int auth_num;
	private String mem_social_id;
	@NotBlank
	@Email
	private String mem_email;
	@NotBlank
	private String mem_nick;
	private int mem_status;
	private int mem_reg_type;
	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,12}$")
	private String mem_pw;
	
	private int pref_num;
	private String mem_photo;
	private String mem_name;
	private String mem_phone;
	private String mem_birth;
	private Date mem_date;
	private Date mem_mdate;
	private String mem_rcode;
	private int recommend_status;
	private int mem_point;
	
}
