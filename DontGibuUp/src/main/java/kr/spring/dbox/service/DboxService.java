package kr.spring.dbox.service;

import java.util.List;
import java.util.Map;

import kr.spring.dbox.vo.DboxBudgetVO;
import kr.spring.dbox.vo.DboxVO;

public interface DboxService {
	//Dbox 입력
	public Long insertDbox(DboxVO dbox);
	//Dbox 목록
	public List<DboxVO> selectList(Map<String, Object> map);
	//Dbox 개수
	public Integer selectListCount(Map<String, Object> map);
}