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
	int checkCartItem(CartVO cartVO); // 항목 존재 여부 확인
    void updateCartQuantity(CartVO cartVO); // 수량 업데이트
    void insertCart(CartVO cartVO); // 항목 삽입
	//장바구니 아이템 삭제
	@Delete("DELETE FROM cart WHERE cart_num")
	public void deleteCart(int cart_num);
}
