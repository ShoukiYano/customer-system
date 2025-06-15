package com.example.customersys.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.customersys.entity.Customer;
import com.example.customersys.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final CustomerRepository customerRepo;
	
	/**
	 * 会員TOP画面を表示
	 * 
	 * @param session  セッションからcustomerIdを取得
	 * @param model    Thymeleafに会員情報を渡す
	 * @return member_top.html
	 */
	@GetMapping("/member/top")
	public String showMemberTop(HttpSession session, Model model) {
		// セッションに保存したcustomerIdを取得
		Long customerId = (Long) session.getAttribute("customerId");
		
		// セッション切れ or 未ログイン時は再度ログイン画面へ
		if(customerId == null) {
			return "redirect:/login";
		}
		
		// DBから会員情報を取得
		Optional<Customer> opt = customerRepo.findById(customerId);
		// 何らかの理由で存在しない場合ログイン画面へ
		if(opt.isEmpty()) {
			return "redirect:/login";
		}
		
		Customer customer = opt.get();
		// Thymeleafへ会員情報を渡す
		model.addAttribute("customer", customer);
		
		// 会員TOP画面へリダイレクト
		return "member_top";
	}
}
