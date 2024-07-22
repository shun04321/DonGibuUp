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

	@Override
	public List<DboxVO> selectList(Map<String, Object> map) {
		return dboxMapper.selectList(map);
	}

	@Override
	public Integer selectListCount(Map<String, Object> map) {
		return dboxMapper.selectListCount(map);
	}
	
	@Override
	public DboxVO selectDbox(long dbox_num) {
		return dboxMapper.selectDbox(dbox_num);
	}

	@Override
	public List<DboxBudgetVO> selectDboxBudgets(long dbox_num) {
		return dboxMapper.selectDboxBudgets(dbox_num);
	}
	
	@Override
	public void insertDboxDonation(long dbox_num) {
		dboxMapper.insertDboxDonation(dbox_num);
	}

	@Override
	public List<DboxDonationVO> selectDboxDonations(long dbox_num) {
		return dboxMapper.selectDboxDonations(dbox_num);
	}
	
	@Override
	public Integer selectDboxDonationsCount(long dbox_num) {
		return dboxMapper.selectDboxDonationsCount(dbox_num);
	}

	@Override
	public DboxResultVO selectDboxResult(long dbox_num) {
		return dboxMapper.selectDboxResult(dbox_num);
	}



}