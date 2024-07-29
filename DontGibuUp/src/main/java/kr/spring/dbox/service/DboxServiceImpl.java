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
import kr.spring.subscription.vo.SubscriptionVO;

@Service
@Transactional
public class DboxServiceImpl implements DboxService {
	@Autowired
	DboxMapper dboxMapper;
	
	//Dbox 등록
	@Override
	public Long insertDbox(DboxVO dbox) {
		dbox.setDbox_num(dboxMapper.selectDboxNum());//Dbox 번호 생성(nextval)
		dboxMapper.insertDbox(dbox);//Dbox 등록
		for(DboxBudgetVO dboxBudget : dbox.getDboxBudgets()) {
			dboxMapper.insertDboxBudget(dboxBudget);//Dbox 모금액 사용 계획 등록
		}
		return dboxMapper.curDboxNum();//dbox번호 반환(curval)
	}
	//Dbox 수정
	@Override
	public void updateDboxStatus(long dbox_num, int dbox_status) {//Dbox Status 수정
		dboxMapper.updateDboxStatus(dbox_num, dbox_status);
	}
	@Override
	public void updateDboxAcomment(long dbox_num,String dbox_acomment) {//Dbox Acomment 수정
		dboxMapper.updateDboxAcomment(dbox_num,dbox_acomment);
	}
	//Dbox 데이터 가져오기
	@Override
	public Integer selectListCount(Map<String, Object> map) {//Dbox 개수
		return dboxMapper.selectListCount(map);
	}	
	@Override
	public List<DboxVO> selectList(Map<String, Object> map) {//Dbox 목록
		return dboxMapper.selectList(map);
	}
	@Override
	public Integer selectAdminListCount(Map<String, Object> map) {//Dbox 관리자개수
		return dboxMapper.selectAdminListCount(map);
	}	
	@Override
	public List<DboxVO> selectAdminList(Map<String, Object> map) {//Dbox 관리자목록
		return dboxMapper.selectAdminList(map);
	}
	@Override
	public List<DboxVO> selectStatusUpdateList(int dbox_status) {//Dbox 업데이트 목록
		return dboxMapper.selectStatusUpdateList(dbox_status);
	}
	@Override
	public DboxVO selectDbox(long dbox_num) {
		return dboxMapper.selectDbox(dbox_num);//Dbox 개별 선택	
	}
	//Dbox 기부계획
	@Override
	public List<DboxBudgetVO> selectDboxBudgets(long dbox_num) {//기부계획 목록 불러오기
		return dboxMapper.selectDboxBudgets(dbox_num);
	}
	//Dbox 기부하기
	@Override
	public void insertDboxDonation(DboxDonationVO dboxDonationVO) {//기부하기 등록
		dboxMapper.insertDboxDonation(dboxDonationVO);
	}
	@Override
	public List<DboxDonationVO> selectDboxDonations(long dbox_num) {//기부하기 목록
		return dboxMapper.selectDboxDonations(dbox_num);
	}
	@Override
	public Integer selectDboxDonationsCount(long dbox_num) {//기부하기 개수
		return dboxMapper.selectDboxDonationsCount(dbox_num);
	}
	@Override
	public Long selecDoantionTotal(long dbox_num) {//기부하기 총합
		return dboxMapper.selecDoantionTotal(dbox_num);
	}
	//Dbox 결과보고
	@Override
	public DboxResultVO selectDboxResult(long dbox_num) {//기부 결과 개별 데이터
		return dboxMapper.selectDboxResult(dbox_num);
	}
	@Override
	public void updatePayStatus(long dbox_do_num, long dbox_do_status) {
		dboxMapper.updatePayStatus(dbox_do_num, dbox_do_status);
	}

	//마이페이지
	@Override
	public int getDboxCountbyMem_num(Map<String, Object> map) {
		return dboxMapper.getDboxCountbyMem_num(map);
	}
	@Override
	public List<DboxVO> getDboxByMem_num(Map<String, Object> map) {
		return dboxMapper.getDboxByMem_num(map);
	}
	

}