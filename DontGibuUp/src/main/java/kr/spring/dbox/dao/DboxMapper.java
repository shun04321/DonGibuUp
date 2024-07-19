package kr.spring.dbox.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.dbox.vo.DboxBudgetVO;
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
}