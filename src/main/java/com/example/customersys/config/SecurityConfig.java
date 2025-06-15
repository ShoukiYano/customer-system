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
          // ── どの URL を認証不要にするかを定義 ─────────────────
          .authorizeHttpRequests(authz -> authz
              .requestMatchers("/register/**", "/css/**", "/js/**").permitAll()
              .anyRequest().authenticated()
          )
          // ── CSRF は必要なければ無効に ─────────────────────────
          .csrf(csrf -> csrf.disable())
          // ── フォームログインの設定 ─────────────────────────────
          .formLogin(login -> login
              // GET /login は自前コントローラのビュー表示
              .loginPage("/login")
              // POST /login をセキュリティ側で受ける
              .loginProcessingUrl("/login")
              // フォーム<input name="email">をユーザ名として使う
              .usernameParameter("email")
              // <input name="password">をパスワードとして使う
              .passwordParameter("password")
              // 認証成功→会員TOP
              .defaultSuccessUrl("/member/top", true)
              // 認証失敗→?error が自動付加される
              .failureUrl("/login?error")
              .permitAll()
          )
          // ── ログアウトの設定 ─────────────────────────────────
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/login")
              .invalidateHttpSession(true)
          );
        return http.build();
    }
}
