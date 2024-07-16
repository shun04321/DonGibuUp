package kr.spring.goods.controller;

import kr.spring.goods.service.PortOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PortOneService portOneService;

    @Autowired
    public PaymentController(PortOneService portOneService) {
        this.portOneService = portOneService;
    }

    @PostMapping("/request")
    public Map<String, Object> requestPayment(@RequestParam String merchantUid, @RequestParam int amount, @RequestParam String cardNumber, @RequestParam String expiry, @RequestParam String birth, @RequestParam String pwd2digit) {
        return portOneService.requestPayment(merchantUid, amount, cardNumber, expiry, birth, pwd2digit);
    }

    @PostMapping("/refund")
    public Map<String, Object> requestRefund(@RequestParam String impUid, @RequestParam int amount, @RequestParam String reason) {
        return portOneService.requestRefund(impUid, amount, reason);
    }
}
