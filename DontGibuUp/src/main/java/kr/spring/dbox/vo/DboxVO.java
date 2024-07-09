package kr.spring.dbox.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DboxVO {
	private long dbox_num;
	private long mem_num;
	private long dcate_num;
	private int dbox_team_type;
	private String dbox_team_name;
	private String dbox_team_detail;
	private String dbox_team_photo;
	private int dbox_business_rnum;
	private String dbox_title;
	private String dbox_photo;
	private String dbox_business_plan;
	private String dbox_budget_data;
	private String dbox_bank;
	private int dbox_account;
	private String dbox_account_name;
	private String dbox_content;
	private String dbox_comment;
	private long dbox_goal;
	private String dbox_sdate;
	private String dbox_edate;
	private Date dbox_rdate;
	private int dbox_status;
}