package kr.spring.notify.service;

import java.util.List;
import java.util.Map;

import kr.spring.notify.vo.NotifyVO;

public interface NotifyService {
	/*---------------------------------------
	알림
	---------------------------------------*/
	//알림 log 찍기
	public void insertNotifyLog(NotifyVO notifyVO, Map<String, String> dynamicValues);
	//알림 목록(안 읽은 것, 최근 2주)
	public List<NotifyVO> selectNotListByMemNum(long mem_num);

	//알림 확인
	public void readNotifyLog(long not_num);
}
