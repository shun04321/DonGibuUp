package kr.spring.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import kr.spring.goods.service.PurchaseService;
import kr.spring.goods.vo.PurchaseVO;
import kr.spring.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/goods")
public class PurchaseController {

    private IamportClient impClient;

    @Value("${iamport.apiKey}")
    private String apiKey;

    @Value("${iamport.secretKey}")
    private String secretKey;
    
    @Autowired
    private PurchaseService purchaseService;
    
    @PostConstruct
    public void initImp() {
    	log.debug("API Key: " + apiKey);
        log.debug("Secret Key: " + secretKey);
        this.impClient = new IamportClient(apiKey, secretKey);
    }
    

    @PostMapping("/purchase")
    public String purchasePage(@RequestParam("imp_uid") String impUid, Model model) {
        model.addAttribute("imp_uid", impUid);
        return "goods/purchase";
    }

    @GetMapping("/purchase")
    public String getPurchasePage() {
        return "goods/purchase";
    }

    @PostMapping("/paymentVerify/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid, HttpSession session) throws IamportResponseException, IOException {
        log.debug("결제 검증 요청: imp_uid = " + imp_uid);
        
        IamportResponse<Payment> payment;
        try {
            payment = impClient.paymentByImpUid(imp_uid);
            if (payment == null || payment.getResponse() == null) {
                log.error("결제 정보 조회 실패: imp_uid = " + imp_uid);
                throw new IamportResponseException("결제 정보 조회 실패", null);
            }
        } catch (IamportResponseException | IOException e) {
            log.error("결제 검증 중 예외 발생: imp_uid = " + imp_uid, e);
            throw e;
        }
        // 실 결제 금액 가져오기
        long paidAmount = payment.getResponse().getAmount().longValue();

        log.debug("결제 금액: " + paidAmount);
        log.debug("payment: " + payment);

        return payment;
    }

    @PostMapping("/purchaseComplete")
    @ResponseBody
    public Map<String, String> savePurchaseInfo(@RequestBody Map<String, Object> data, HttpSession session, HttpServletRequest request)
            throws IllegalStateException, IOException {
        Map<String, String> mapJson = new HashMap<>();

        try {
            String impUid = (String) data.get("imp_uid");
            String merchantUid = (String) data.get("merchant_uid");
            int amount = (Integer) data.get("amount");
            String status = (String) data.get("status");
            int itemNum = (Integer) data.get("item_num");
            String itemName = (String) data.get("item_name");
            String buyerName = (String) data.get("buyer_name");

            log.debug("impUid : " + impUid);
            log.debug("merchantUid : " + merchantUid);
            log.debug("amount : " + amount);
            log.debug("status : " + status);
            log.debug("itemNum : " + itemNum);
            log.debug("itemName : " + itemName);
            log.debug("buyerName : " + buyerName);

            // 세션 데이터 가져오기
            MemberVO member = (MemberVO) session.getAttribute("user");

            if (member == null) {
                mapJson.put("result", "logout");
            } else {
                // 결제 정보 저장
                PurchaseVO purchaseVO = new PurchaseVO();
                purchaseVO.setImp_uid(impUid);
                purchaseVO.setMerchant_uid(merchantUid);
                purchaseVO.setAmount(amount);
                purchaseVO.setStatus(status);
                purchaseVO.setItem_num(itemNum);
                purchaseVO.setItem_name(itemName);
                purchaseVO.setBuyer_name(buyerName);
                purchaseVO.setMemNum(member.getMem_num()); // memNum 설정

                try {
                    purchaseService.insertPurchase(purchaseVO);
                    mapJson.put("result", "success");
                } catch (Exception e) {
                    log.error("결제 정보 저장 중 오류 발생", e);
                    mapJson.put("result", "error");
                    mapJson.put("message", "결제 정보 저장 중 오류가 발생했습니다. 관리자에게 문의하세요.");
                }
            }
        } catch (NumberFormatException e) {
            log.error("데이터 형식 오류: ", e);
            mapJson.put("result", "error");
            mapJson.put("message", "데이터 형식 오류가 발생했습니다. 관리자에게 문의하세요.");
        } catch (Exception e) {
            log.error("알 수 없는 오류 발생: ", e);
            mapJson.put("result", "error");
            mapJson.put("message", "알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요.");
        }

        return mapJson;
    }

}