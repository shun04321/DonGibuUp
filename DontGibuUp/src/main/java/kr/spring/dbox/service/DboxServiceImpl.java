package kr.spring.dbox.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.dbox.dao.DboxMapper;
import kr.spring.dbox.vo.DboxBudgetVO;
import kr.spring.dbox.vo.DboxDonationVO;
import kr.spring.dbox.vo.DboxResultVO;
import kr.spring.dbox.vo.DboxVO;

@Service
@Transactional
public class DboxServiceImpl implements DboxService {
	@Autowired
	DboxMapper dboxMapper;
	//Dbox 입력
	@Override
	public Long insertDbox(DboxVO dbox) {
		dbox.setDbox_num(dboxMapper.selectDboxNum());
		//Dbox 입력
		dboxMapper.insertDbox(dbox);
		//Dbox 모금액 사용 계획 입력
		for(DboxBudgetVO dboxBudget : dbox.getDboxBudgets()) {
			dboxMapper.insertDboxBudget(dboxBudget);
		}
		return dboxMapper.curDboxNum();
	}
	//Dbox 목록
	@Override
	public List<DboxVO> selectList(Map<String, Object> map) {
		return dboxMapper.selectList(map);
	}
	//Dbox 개수
	@Override
	public Integer selectListCount(Map<String, Object> map) {
		return dboxMapper.selectListCount(map);
	}
	//Dbox 선택	
	@Override
	public DboxVO selectDbox(long dbox_num) {
		return dboxMapper.selectDbox(dbox_num);
	}
	//Dbox 기부계획 선택
	@Override
	public List<DboxBudgetVO> selectDboxBudgets(long dbox_num) {
		return dboxMapper.selectDboxBudgets(dbox_num);
	}
	//Dbox_Donation	
	@Override
	public void insertDboxDonation(DboxDonationVO dboxDonationVO) {//기부하기 등록
		dboxMapper.insertDboxDonation(dboxDonationVO);
	}

	@Override
	public List<DboxDonationVO> selectDboxDonations(long dbox_num) {//기부하기 목록
		return dboxMapper.selectDboxDonations(dbox_num);
	}
	@Override
	public Integer selectDboxDonationsCount(long dbox_num) {//기부하기 횟수
		return dboxMapper.selectDboxDonationsCount(dbox_num);
	}
	@Override
	public Long selecDoantionTotal(long dbox_num) {//기부하기 총합
		return dboxMapper.selecDoantionTotal(dbox_num);
	}
	//Dbox_Result
	@Override
	public DboxResultVO selectDboxResult(long dbox_num) {
		return dboxMapper.selectDboxResult(dbox_num);
	}
}