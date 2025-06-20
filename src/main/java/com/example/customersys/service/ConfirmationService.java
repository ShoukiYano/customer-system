package com.example.customersys.service;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.customersys.entity.ConfirmationToken;
import com.example.customersys.repository.ConfirmationTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 確認コードの生成、検証を行なうサービス (ConfirmationToken)

@Service
@RequiredArgsConstructor
public class ConfirmationService {
	
	private final ConfirmationTokenRepository tokenRepo;
	private final JavaMailSender mailSender;
	
	
	/**
	 * 指定メールへ 6 桁コードを生成、保存し（将来）メール送信する
	 * 
	 * @param email 送信先メールアドレス
	 */
	@Transactional
	public void sendConfirmationCode(String email) {
		// 6桁のランダムコードを生成
		String code = String.valueOf((int)(Math.random()*900000) + 100000);
		
		// トークンエンティティ組立
		ConfirmationToken token = new ConfirmationToken();
		token.setEmail(email);
		token.setCode(code);
		
		// 24時間後を有効期限に設定
		token.setExpiresAt(LocalDateTime.now().plusHours(24));
		token.setUsedFlag(false);
		
		// DBに保存
		tokenRepo.save(token);
		
		// TODO: メール送信コンポーネントへcodeとemailを渡して通知
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setFrom("yanopitrombone@gmail.com");
		message.setSubject("【CustomerSystem】会員登録案内 確認コード");
		message.setText(new StringBuilder()
				.append(email).append("様\n\n")
				.append("会員登録の確認コードを送ります。\n")
				.append("以下の6桁コードを入力画面にご入力ください。\n\n")
				.append("確認コード").append(code).append("\n\n")
				.append("※本メールの有効期限は24時間です。")
				.toString());
		// メール送信
		mailSender.send(message);
	}
	
	/**
	 * 入力されたメール＋コードが有効か検証し、成功時に使用済フラグを立てる
	 * 
	 * @param email 検証対象メールアドレス
	 * @param code  入力された 6 桁コード
	 * @return true: 検証成功、 false: 検証失敗
	 */
	@Transactional
	public boolean verifyCode(String email, String code) {
		// DBから条件に合うトークンを取得
		var opt = tokenRepo.findByEmailAndCodeAndUsedFlagAndExpiresAtAfter(
				email, code, false, LocalDateTime.now());
		
		// トークンが見つからない or 期限切れ or 使用済 -> 検証失敗
		if(opt.isEmpty()) {
			return false;
		}
		
		// 使用済に更新して次回使用できないよう処理
		ConfirmationToken token = opt.get();
		token.setUsedFlag(false);
		tokenRepo.save(token);
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
