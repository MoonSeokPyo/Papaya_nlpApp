package msp.papaya.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import msp.papaya.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class Config {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))	
				.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
				.formLogin(formLogin -> formLogin.loginPage("/user/login").usernameParameter("email")
						.defaultSuccessUrl("/"))												//로컬 로그인 설정 
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/").invalidateHttpSession(true))				
				.oauth2Login(oauth2 -> oauth2.loginPage("/user/login").defaultSuccessUrl("/")
						.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)
						));																		//소셜 로그인 설정 customOAuth2UserService에서 로그인 플렛폼에 따라 처리함

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
