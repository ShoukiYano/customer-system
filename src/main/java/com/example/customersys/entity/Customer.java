package com.example.customersys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	// 顧客ID (PK)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	// 苗字
	@Column(nullable = false, length = 50)
	private String lastName;
	
	// 名前
	@Column(nullable = false, length = 50)
	private String firstName;
	
	// メールアドレス
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	// 電話番号
	@Column(length = 20)
	private String phone;
	
	// パスワードハッシュ
	@Column(nullable = false, length = 100)
	private String passwordHash;
	
	// 会員ID (8桁)
	@Column(nullable = false, unique = true, length = 8)
	private String memberCode;
	
	// ポイント
	private Integer points = 0;
	
	// 性別 0=男性, 1=女性
	@Column(nullable = false)
	private Short gender;
	
	// 削除フラグ 0=未, 1=済
	private Short deletedFlag = 0;
	
	// 会員ステータス 0=レギュラー, 1=ゴールド
	private Short statusCode = 0;
}
