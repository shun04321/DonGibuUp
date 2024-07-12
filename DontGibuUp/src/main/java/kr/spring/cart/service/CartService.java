package kr.spring.cart.service;

import java.util.List;
import java.util.Map;

import kr.spring.cart.vo.CartVO;

public interface CartService {
	public void insertCart(CartVO cartVO);
	public List<CartVO> cartList(Map<String,Object>map);
	public Integer cartRowCount (Map<String,Object>map);
}
