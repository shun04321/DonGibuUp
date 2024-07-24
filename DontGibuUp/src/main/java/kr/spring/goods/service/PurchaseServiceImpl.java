package kr.spring.goods.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.cart.vo.CartVO;
import kr.spring.goods.dao.PurchaseMapper;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;
    
    @Override
    public void insertPurchase(PurchaseVO purchaseVO) {
        purchaseMapper.insertPurchase(purchaseVO);
    }

    @Override
    @Transactional
    public void insertPurchaseWithCartItems(PurchaseVO purchaseVO) {
        // 1. purchase 테이블에 데이터 삽입
        purchaseMapper.insertPurchaseWithCartItems(purchaseVO);

   
    }
    @Override
    public List<CartVO> getPurchaseItems(long purchase_num) {
        return purchaseMapper.getPurchaseItems(purchase_num);
    }

    @Override
    public void processRefund(RefundVO refundVO) {
        purchaseMapper.insertRefund(refundVO);
    }

    @Override
    public List<PurchaseVO> getPurchaseListByMember(long memNum) {
        return purchaseMapper.getPurchaseListByMember(memNum);
    }

    @Override
    public List<PurchaseVO> getAllPurchases() {
        return purchaseMapper.getAllPurchases();
    }

    @Override
    public void updateDeliveryStatus(int purchaseNum, String deliveryStatus) {
        purchaseMapper.updateDeliveryStatus(purchaseNum, deliveryStatus);
    }

    @Override
    public void updateRefundStatus(String impUid, int status) {
        purchaseMapper.updateRefundStatus(impUid, status);
    }

	@Override
	public void insertPurchaseWithCartItems(PurchaseVO purchaseVO, List<CartVO> cartItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getSeq() {
		return purchaseMapper.getSeq();
	}

	
}