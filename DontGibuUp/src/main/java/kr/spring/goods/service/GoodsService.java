package kr.spring.goods.service;

import java.util.List;
import java.util.Map;



import kr.spring.goods.vo.GoodsVO;


public interface GoodsService {
	void insertGoods(GoodsVO goods);
    List<GoodsVO> selectList(Map<String, Object> map, Integer mem_status);
    Integer selectRowCount(Map<String, Object> map);
    void updateGoods(GoodsVO goodsVO);
    GoodsVO detailGoods(long item_num);
    void deleteGoods(long item_num);
    
}