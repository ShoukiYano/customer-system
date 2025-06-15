package com.example.customersys.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.customersys.entity.Customer;
import com.example.customersys.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	
	
	/**
	 *  ログイン画面表示処理
	 *  
	 *  @return login.html
	 */
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	/**
	 * ログインフォーム送信後の認証処理
	 * 
	 * @param email     フォームから受け取ったメールアドレス
	 * @param password  フォームから受け取ったパスワード
	 * @param model     エラーメッセージを渡すためのモデル
	 * @param session   認証後にsession情報を保持
	 * @return 認証成功->redirect:/member/top, 失敗->login.html
	 */
	@PostMapping("/login")
	public String doLogin(
			@RequestParam String email,
			@RequestParam String password,
			Model model, HttpSession session) {
		
		Optional<Customer> opt = loginService.authenticate(email, password);
		// サービスに認証を委譲し、Optional<Customer>を受け取る
		
		// 認証失敗時：エラーメッセージをmodelにセットしログイン画面に戻す
		if(opt.isEmpty()) {
			model.addAttribute("error", "メールアドレスまたはパスワードが違います。");
			return "login";
		}
		// 認証成功時：Customerをセッションに保存（次画面で参照）
		Customer customer = opt.get();
		
		// セッションに保存
		session.setAttribute("customerId", customer.getCustomerId());
		return "redirect:/member/top";
	}
	
	/**
	 * ログアウト処理
	 * 
	 * @param session セッション無効化
	 * @return ログイン画面へリダイレクト
	 */
	@GetMapping("/logout")
	public String doLogout(HttpSession session) {
		// セッション全属性破棄してログアウト
		session.invalidate();
		return "redirect:/login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
