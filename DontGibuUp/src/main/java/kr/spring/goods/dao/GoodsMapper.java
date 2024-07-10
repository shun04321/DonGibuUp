package kr.spring.goods.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.goods.vo.GoodsVO;

@Mapper
public interface GoodsMapper {
	public List<GoodsVO> selectList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	//상품 등록 - xml
	public void insertGoods(GoodsVO goods);
	//상품 상세
	public void detailGoods(long item_num);
	
	
}
