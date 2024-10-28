package com.hangw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests( (authorizeHttpRequest) ->  authorizeHttpRequest.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())	//모든 경로의 권한 허용
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))													//세샨항상 허용
		.csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**")))				//크로스 오리진을 허용
		.formLogin((formLogin) -> formLogin.loginPage("/user/login").defaultSuccessUrl("/"))
		.logout((logout) ->logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true));
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
