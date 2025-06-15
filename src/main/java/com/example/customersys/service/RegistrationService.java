package com.example.customersys.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.customersys.entity.Customer;
import com.example.customersys.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {

	private final CustomerRepository repo;
	
	/**
	 * 確認コードを生成してメール送信する
	 * 
	 * @param email 送信先メールアドレス
	 */
	@Transactional
	public void sendConfirmationCode(String email) {
		// 6桁ランダムコード生成
		String code = String.valueOf((int)(Math.random()*900000) + 100000);
		// 確認コードトークンを登録
		// TODO: メール送信処理呼び出し
	}
	
	/**
	 * 本登録用にCustomerエンティティを保存
	 * 
	 * @param customer 入力された顧客情報
	 */
	@Transactional
	public Customer register(Customer customer) {
		// 会員コード8桁UUID
		customer.setMemberCode(UUID.randomUUID().toString().substring(0,8));
		
		// パスワードハッシュ化
		// TODO: パスワードハッシュ化処理
		
		return repo.save(customer);
	}
}
