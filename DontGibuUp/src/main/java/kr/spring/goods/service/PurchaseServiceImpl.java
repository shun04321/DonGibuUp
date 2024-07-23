package kr.spring.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.cart.vo.CartVO;
import kr.spring.goods.dao.PurchaseMapper;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.goods.vo.RefundVO;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;
    
    @Override
    public void insertPurchase(PurchaseVO purchaseVO) {
        purchaseMapper.insertPurchase(purchaseVO);
    }

    @Override
    public void insertPurchaseWithCartItems(PurchaseVO purchaseVO, List<CartVO> cartItems) {
        purchaseMapper.insertPurchaseForCart(purchaseVO);

        // purchaseNum이 제대로 설정되었는지 확인
        if (purchaseVO.getPurchaseNum() == null) {
            throw new IllegalStateException("purchaseNum is null after insert");
        }

        for (CartVO item : cartItems) {
            item.setPurchase_num(purchaseVO.getPurchaseNum()); // purchaseNum 설정
            purchaseMapper.insertPurchaseItem(item);
        }
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
}