package kr.spring.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestBody List<Map<String, Object>> goodsData, HttpSession session) {
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        // goodsData에는 상품 번호와 수량 정보가 담겨 있음
        for (Map<String, Object> item : goodsData) {
            String item_num = (String) item.get("item_num");
            int quantity = (int) item.get("quantity");
            // 기존 장바구니에 있는 수량에 추가
            cart.put(item_num, cart.getOrDefault(item_num, 0) + quantity);
        }

        session.setAttribute("cart", cart);

        // JSON 형식의 응답을 보낼 수 있지만 여기서는 리다이렉트로 처리하는 예시를 보여주고 있음
        return "redirect:/cart"; // 장바구니 목록으로 리다이렉트
    }
    
}