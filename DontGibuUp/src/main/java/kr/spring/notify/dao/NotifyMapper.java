package kr.spring.notify.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.notify.vo.NotifyVO;

@Mapper
public interface NotifyMapper {
	/*---------------------------------------
				알림
	---------------------------------------*/
	//알림 log 찍기
	public void insertNotifyLog(NotifyVO notifyVO);
	//알림 목록(안 읽은 것, 최근 2주)
	public List<NotifyVO> selectNotListByMemNum(long mem_num);
	//알림 확인
	@Update("UPDATE notify_log SET not_read_datetime = SYSDATE WHERE not_num=#{not_num}")
	public void readNotifyLog(long not_num);
	//알림 템플릿 가져오기
	@Select("SELECT notify_template FROM db_notify_template WHERE notify_type=#{notify_type}")
	public String selectNotifyTemplate(int notify_type);
}
