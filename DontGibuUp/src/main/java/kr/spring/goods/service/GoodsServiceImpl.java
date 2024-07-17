package kr.spring.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.goods.dao.GoodsMapper;
import kr.spring.goods.vo.GoodsVO;
import kr.spring.goods.vo.PaymentVO;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    private IamportClient iamportClient;
    private String apiKey = "2501776226527075";
    private String secretKey = "ICUCy6Nusg8jMZZsAdnXqpTvBPr2Xecu6m4lnbWzegwMDwaPJ46SKznjj8oOCjovXNXeG0xOBKQ7Jfs";

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    @Override
    public void insertGoods(GoodsVO goods) {
        goodsMapper.insertGoods(goods);
    }

    @Override
    public List<GoodsVO> selectList(Map<String, Object> map, Integer mem_status) {
        if (mem_status == null || mem_status != 9) {
            map.put("item_status", 1); // 일반 사용자라면 item_status가 1인 상품만 조회하도록 필터링
        }
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
        goodsMapper.deleteCartItems(item_num); // 자식 레코드 먼저 삭제
        goodsMapper.deleteGoods(item_num); // 부모 레코드 삭제
    }

    @Override
    public void insertPayment(PaymentVO paymentVO) {
        goodsMapper.insertPayment(paymentVO);
    }


    // 결제 처리 로직
    @Override
    public void processPayment(PaymentVO paymentVO) throws Exception {
        try {
            IamportResponse<Payment> response = iamportClient.paymentByImpUid(paymentVO.getImpUid());
            if (!response.getResponse().getStatus().equals("paid")) {
                throw new RuntimeException("결제에 실패하였습니다.");
            }
            paymentVO.setPayStatus(0);
            paymentVO.setPayDate(new java.sql.Date(System.currentTimeMillis()));
            goodsMapper.insertPayment(paymentVO);
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException("결제 중 오류가 발생했습니다.", e);
        }
    }

    // 환불 처리 로직
    @Override
    public void processRefund(String impUid, int amount, String reason) throws Exception {
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        CancelData cancelData = new CancelData(impUid, true, bigDecimalAmount);
        cancelData.setReason(reason);

        try {
            IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);
            if (!response.getResponse().getStatus().equals("cancelled")) {
                throw new RuntimeException("환불에 실패하였습니다.");
            }
            
            // 결제 상태를 '환불 완료'로 업데이트
            Map<String, Object> params = new HashMap<>();
            params.put("impUid", impUid);
            params.put("status", 1);
            goodsMapper.updatePaymentStatus(params);
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException("환불 중 오류가 발생했습니다.", e);
        }
    }

   
}