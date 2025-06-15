package com.example.customersys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// 全てのリクエストを許可
			.authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
			// CSRFは開発時だけ無効化
			.csrf(csrf -> csrf.disable())
			// /loginをフォームログインページとして指定
			.formLogin(login -> login
					.loginPage("/login")
					.permitAll()
			)
			// ログアウトも自動で/logoutにフック
			.logout(logout -> logout.invalidateHttpSession(true));
		return http.build();
	}
}
