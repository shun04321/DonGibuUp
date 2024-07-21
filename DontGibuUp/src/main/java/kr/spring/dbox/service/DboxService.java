package kr.spring.dbox.service;

import java.util.List;
import java.util.Map;

import kr.spring.dbox.vo.DboxBudgetVO;
import kr.spring.dbox.vo.DboxDonationVO;
import kr.spring.dbox.vo.DboxResultVO;
import kr.spring.dbox.vo.DboxVO;

public interface DboxService {
	//Dbox 입력
	public Long insertDbox(DboxVO dbox);
	//Dbox 목록
	public List<DboxVO> selectList(Map<String, Object> map);
	//Dbox 개수
	public Integer selectListCount(Map<String, Object> map);
	//Dbox 선택
	public DboxVO selectDbox(long dbox_num);
	//Dbox 기부계획 선택
	public List<DboxBudgetVO> selectDboxBudgets(long dbox_num);
	//Dbox_Donation
	public List<DboxDonationVO> selectDboxDonations(long dbox_num);
	//Dbox_Result
	public DboxResultVO selectDboxResult(long dbox_num);
}