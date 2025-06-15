package com.example.customersys.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.customersys.entity.Customer;
import com.example.customersys.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	// 会員情報取得用リポジトリ
	private final CustomerRepository customerRepo;
	
	/**
	 * メールアドレスとパスワードでログイン認証する
	 * 
	 * @param email    入力されたメールアドレス
	 * @param password 入力されたパスワード (平文パスワード)
	 * @return 認証成功時はCustomer、失敗時はempty
	 */
	public Optional<Customer> authenticate(String email, String password) {
		// メールアドレスで会員を検索
		Optional<Customer> opt = customerRepo.findByEmail(email);
		
		// メールアドレスが存在しない -> 認証失敗
		if(opt.isEmpty()) {
			return Optional.empty();
		}
		
		Customer customer = opt.get();
		
		// TODO: BCryptでのハッシュ比較実装
		
		// パスワード不一致 -> 認証失敗
		if(!customer.getPasswordHash().equals(password)) {
			return Optional.empty();
		}
		
		// 削除フラグ・ステータスチェック
		// 論理削除済 -> 認証失敗
		if(customer.getDeletedFlag() == 1) {
			return Optional.empty();
		}
		
		// 認証成功
		return Optional.of(customer);
	}
}
