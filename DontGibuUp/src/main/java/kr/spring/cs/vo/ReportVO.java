package kr.spring.cs.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportVO {
	private long report_num;
	private long mem_num;
	private long reported_mem_num;
	private Integer report_type;
	private String report_content;
	private String report_filename;
	private String report_reply;
	private Date report_rdate;
	private Integer report_status;
	private Date report_date;
	
	private String mem_nick;
	private String reported_mem_nick;
	private int reported_mem_status;

	private long report_source;
	private long chal_num;
	private long chal_rev_num;
	private long chal_ver_num;
	private long dbox_re_num;
	
	private MultipartFile upload;
}
