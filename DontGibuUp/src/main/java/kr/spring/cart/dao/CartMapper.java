package kr.spring.cart.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.spring.cart.vo.CartVO;

@Mapper
public interface CartMapper {
	//장바구니 목록 출력
	public List<CartVO>cartList(Map<String,Object>map);
	public Integer cartRowCount(Map<String, Object> map);
	//장바구니 담기 - xml
	public void insertCart(CartVO cart);
	//장바구니 수정
	@Update("UPDATE cart SET cart_quantity=#{cart_quantity}WHERE cart_num=#{cart_num}")
	public void updateCart(CartVO cart);
	//장바구니 아이템 삭제
	@Delete("DELETE FROM cart WHERE cart_num")
	public void deleteCart(int cart_num);
}
