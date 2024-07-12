package kr.spring.cart.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.cart.dao.CartMapper;
import kr.spring.cart.vo.CartVO;
@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	CartMapper cartMapper;
	
	@Override
	public void insertCart(CartVO cartVO) {
		cartMapper.insertCart(cartVO);
		
	}

	@Override
	public List<CartVO> cartList(Map<String, Object> map) {
		return cartMapper.cartList(map);
	}

	@Override
	public Integer cartRowCount(Map<String, Object> map) {
		return cartMapper.cartRowCount(map);
	}

}
