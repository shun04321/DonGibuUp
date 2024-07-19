package kr.spring.point.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.point.dao.PointMapper;
import kr.spring.point.vo.PointVO;

@Service
@Transactional
public class PointServiceImpl implements PointService {
	
	@Autowired
	PointMapper pointMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	//포인트 로그
	public void insertPointLog(PointVO pointVO) {
		pointMapper.insertPointLog(pointVO);
	}

	@Override
	public List<PointVO> getMemberPointList(Map<String, Object> map) {
		return pointMapper.getMemberPointList(map);
	}

	@Override
	public Integer getMPointRowCount(Map<String, Object> map) {
		return pointMapper.getMPointRowCount(map);
	}

	//관리자
	@Override
	public void updateMemPoint(PointVO pointVO) {
		pointMapper.insertPointLog(pointVO);
		memberMapper.updateMemPoint(pointVO);
	}
	
}
