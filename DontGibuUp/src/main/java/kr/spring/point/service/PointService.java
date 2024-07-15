package kr.spring.point.service;

import java.util.List;

import kr.spring.point.vo.PointVO;

public interface PointService {
	//포인트 로그찍기
	public void insertPointLog(PointVO pointVO);
	//회원별 포인트 목록
	public List<PointVO> getMemberPointList(long mem_num);
}
