package kr.spring.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
	

	    @PostMapping("/add-to-cart")
	    public String addToCart(@RequestParam("selectedGoods") List<String> selectedGoods, HttpSession session) {
	        List<String> cart = (List<String>) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new ArrayList<>();
	            session.setAttribute("cart", cart);
	        }

	        cart.addAll(selectedGoods);
	        session.setAttribute("cart", cart);

	        return "redirect:/cart"; // 장바구니 목록으로 리다이렉트
    }
    
}