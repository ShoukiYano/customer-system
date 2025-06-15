package com.example.customersys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.customersys.entity.Customer;
import com.example.customersys.service.ConfirmationService;
import com.example.customersys.service.RegistrationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
	
	private final ConfirmationService confirmationService;
	private final RegistrationService registrationService;
	
	// メールアドレス入力画面
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		// 初期化用に空のCustomerをセット
		model.addAttribute("customer", new Customer());
		return "registration";
	}
	
	// 確認コード送信処理
	@PostMapping("/register")
	public String sendCode(@RequestParam("email") String email, Model model) {
		// 入力されたemailに対しワンタイムコードを生成、DB登録
		confirmationService.sendConfirmationCode(email);
		
		// 確認コード入力画面にemailを渡す
		model.addAttribute("email", email);
		return "confirm_code";
	}
	
	// 確認コード検証 & 本登録用フォーム表示
	@PostMapping("register/confirm")
	public String confirmCode(
			@RequestParam("email") String email,
			@RequestParam("code") String code,
			Model model) {
		
		// コード検証。成功の場合、本登録処理。失敗なら再度入力画面
		if(!confirmationService.verifyCode(email, code)) {
			model.addAttribute("error", "確認コードが間違っているか期限切れです。");
			model.addAttribute("email", email);
			return "registration_code";
		}
		
		// 本登録フォーム用にemailをセット。Customerバインド用オブジェクトも追加
		Customer customer = new Customer();
		customer.setEmail(email);
		model.addAttribute("customer", customer);
		return "registration_form";		
	}
	
	// 本登録完了処理
	@PostMapping("register/form")
	public String completeRegistration(
			@ModelAttribute Customer costomer) {
		
		// 入力値をもとに顧客情報を保存
		registrationService.register(costomer);
		
		// 保存後ログイン画面にリダイレクト
		return "redirect:/login";
	}
	
	
	
	
	
	
	
	
	
}
