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
	public void updateGoods(GoodsVO goodsVO) {
		goodsMapper.updateGoods(goodsVO);
		
	}

	@Override
	public GoodsVO detailGoods(long item_num) {
		return goodsMapper.detailGoods(item_num);
	}

	 @Override
	    public void deleteGoods(long item_num) {
	        // 자식 레코드 먼저 삭제
	        goodsMapper.deleteCartItems(item_num);
	        // 부모 레코드 삭제
	        goodsMapper.deleteGoods(item_num);
	    }


	
}
