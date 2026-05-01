package jp.co.sss.equipment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * spring Security用のconfig
 */
@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable()) // とりあえずOFF

        .authorizeHttpRequests(auth -> auth

            .anyRequest().permitAll() // 全部許可

        )

        .formLogin(form -> form.disable()); // ログイン画面OFF

    return http.build();
	}
	
}
