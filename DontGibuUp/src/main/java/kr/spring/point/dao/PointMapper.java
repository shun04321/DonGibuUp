package kr.spring.point.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.spring.point.vo.PointVO;

@Mapper
public interface PointMapper {
	//포인트 로그
	@Insert("INSERT INTO point_log(point_num,mem_num,pevent_type,point_amount) VALUES(point_log_seq.nextval,#{mem_num},#{pevent_type},#{point_amount})")
	public void insertPointLog(PointVO pointVO);
}
