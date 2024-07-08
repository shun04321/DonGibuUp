package kr.spring.goods.service;

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
	public void insertGoods(GoodsVO goodsVO) {
		goodsMapper.insertGoods(goodsVO);
		
	}
	
	
}
