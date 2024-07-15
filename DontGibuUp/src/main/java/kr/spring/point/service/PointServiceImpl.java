package kr.spring.point.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.point.dao.PointMapper;
import kr.spring.point.vo.PointVO;

@Service
@Transactional
public class PointServiceImpl implements PointService {
	
	@Autowired
	PointMapper pointMapper;
	
	//포인트 로그
	public void insertPointLog(PointVO pointVO) {
		pointMapper.insertPointLog(pointVO);
	}

	@Override
	public List<PointVO> getMemberPointList(long mem_num) {
		return pointMapper.getMemberPointList(mem_num);
	}
	
}
