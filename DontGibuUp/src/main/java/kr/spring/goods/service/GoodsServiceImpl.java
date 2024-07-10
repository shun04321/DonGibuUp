package kr.spring.goods.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.goods.dao.GoodsMapper;
import kr.spring.goods.vo.GoodsVO;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	GoodsMapper goodsMapper;

	@Override
	public void insertGoods(GoodsVO goods) {
		goodsMapper.insertGoods(goods);
		
	}

	@Override
	public List<GoodsVO> selectList(Map<String, Object> map) {
		return goodsMapper.selectList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {

		return goodsMapper.selectRowCount(map);
	}

	@Override
	public void updateGoods(GoodsVO goods) {
		// TODO Auto-generated method stub
		
	}


	
}
