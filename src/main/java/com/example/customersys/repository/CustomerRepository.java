package com.example.customersys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customersys.entity.Customer;

/**
 * 顧客テーブル操作用リポジトリ
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// メールアドレスで顧客を検索
	Optional<Customer> findByEmail(String email);
}
