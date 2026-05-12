package jp.co.sss.equipment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * spring Security用のconfig
 */
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // とりあえずOFF
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll() // 全部許可
				)

				.formLogin(form -> form.disable()); // ログイン画面OFF
		return http.build();
	}
	
	/**

	 * パスワードのハッシュ化・照合に使う部品
	 *
	 * LoginServiceなどから共通利用できるインスタンスを
	 * Springに管理させる設定
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
