package kr.spring.point.vo;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PointVO {
	public long point_num;
	public long mem_num;
	public int pevent_type;
	public int point_amount;
	public Date point_date;
}