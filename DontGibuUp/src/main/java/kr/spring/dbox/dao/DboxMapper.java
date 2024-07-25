package kr.spring.dbox.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.dbox.vo.DboxBudgetVO;
import kr.spring.dbox.vo.DboxDonationVO;
import kr.spring.dbox.vo.DboxResultVO;
import kr.spring.dbox.vo.DboxVO;

@Mapper 
public interface DboxMapper {
	//Dbox 번호 생성
	@Select("SELECT dbox_seq.nextval FROM dual")
	public Long selectDboxNum();
	//Dbox 입력
	public Long insertDbox(DboxVO dbox);
	//Dbox 모금액 사용 계획 입력
	@Insert("INSERT INTO dbox_budget (dbox_bud_num,dbox_num,dbox_bud_purpose,dbox_bud_price) "
							+ "VALUES(dbox_budget_seq.nextval,dbox_seq.currval,#{dbox_bud_purpose},#{dbox_bud_price})")
	public void insertDboxBudget(DboxBudgetVO dboxBudget);
	//Dbox 현재 번호 선택
	@Select("SELECT dbox_seq.currval FROM dual")
	public Long curDboxNum();
	
	//Dbox 목록
	public List<DboxVO> selectList(Map<String, Object> map);
	
	//Dbox 개수
	public Integer selectListCount(Map<String, Object> map);
	
	//Dbox 선택
	@Select("SELECT * FROM dbox JOIN dona_category USING(dcate_num) WHERE dbox_num=#{dbox_num}")
	public DboxVO selectDbox(long dbox_num);
	//Dbox 기부계획 선택
	@Select("SELECT * FROM dbox_budget WHERE dbox_num=#{dbox_num}")
	public List<DboxBudgetVO> selectDboxBudgets(long dbox_num);
	//Dbox_Donation
	public void insertDboxDonation(DboxDonationVO dboxDonationVO);
	@Select("SELECT * FROM dbox_donation JOIN member USING(mem_num) JOIN member_detail USING(mem_num) WHERE dbox_num=#{dbox_num} ORDER BY dbox_do_num DESC")
	public List<DboxDonationVO> selectDboxDonations(long dbox_num);
	@Select("SELECT COUNT(*) FROM dbox_donation JOIN member USING(mem_num) JOIN member_detail USING(mem_num) WHERE dbox_num=#{dbox_num}")
	public Integer selectDboxDonationsCount(long dbox_num);
	@Select("SELECT SUM(dbox_do_price) FROM dbox_donation WHERE dbox_num=#{dbox_num}")
	public Long selecDoantionTotal(long dbox_num);
	//Dbox_Result
	@Select("SELECT * FROM dbox_result WHERE dbox_num=#{dbox_num}")
	public DboxResultVO selectDboxResult(long dbox_num);
}