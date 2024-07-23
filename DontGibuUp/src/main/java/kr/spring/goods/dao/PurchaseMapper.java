package kr.spring.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.spring.cart.vo.CartVO;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;
@Mapper
public interface PurchaseMapper {
	 void insertPurchase(PurchaseVO purchaseVO);
	    void insertRefund(RefundVO refundVO);
	    List<PurchaseVO> getPurchaseListByMember(long memNum);
	    void updateRefundStatus(String impUid, int status);
	    List<PurchaseVO> getAllPurchases();
	    void updateDeliveryStatus(int purchaseNum, String deliveryStatus);
	    void insertPurchaseForCart(PurchaseVO purchaseVO);
	    void insertPurchaseItem(CartVO cartVO);
	    List<CartVO> getPurchaseItems(long purchase_num);
    
}