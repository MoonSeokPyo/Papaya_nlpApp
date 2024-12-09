package msp.papaya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (새로운 방식)   http.csrf(csrf -> csrf.ignoringRequestMatchers("/public/**"));
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll() // 모든 요청 허용
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        );
//        .authorizeHttpRequests(auth -> auth
//            .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
//            .anyRequest().authenticated()
//        )
//        .oauth2Login(oauth2 -> oauth2
//            .defaultSuccessUrl("/", true)
//        )
//        .logout(logout -> logout
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/")
//            .permitAll()
//        );

    return http.build();
  }

//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests()
//        .requestMatchers("/", "/login", "/css/**", "/js/**").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .oauth2Login()
//        .defaultSuccessUrl("/", true);
//
//    return http.build();
//  }
}
