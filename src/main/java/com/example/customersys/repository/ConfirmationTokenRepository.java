package com.example.customersys.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customersys.entity.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
	
	/**
	 * メール、コード、未使用、かつ有効期限内のトークンを取得
	 * 
	 * @param email     検索対象メールアドレス
	 * @param code      検索対象コード
	 * @param usedFlag  false: 未使用のみ
	 * @param now       現在時刻 (期限チェック用)
	 * @return トークン情報 (存在しない場合、empty)
	 */
	Optional<ConfirmationToken> findByEmailAndCodeAndUsedFlagAndExpiresAtAfter(
			String email, String code, Boolean usedFlag, LocalDateTime now);
}
