package com.hangw.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hangw.model.PageUser;
import com.hangw.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService { // 소셜 로그인 처리 기능

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		if ("naver".equals(registrationId)) {
			// 네이버 사용자 응답 처리
			Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");

			String email = response.get("email").toString();
			String name = response.get("name").toString();

			// 사용자 저장 또는 조회
			PageUser user = userRepository.findByEmail(email)
					.orElseGet(() -> userRepository.save(new PageUser(name, email, "naver")));

			// 새로운 attributes Map 생성하여 email 필드 포함
			Map<String, Object> attributes = Map.of("id", response.get("id"), "name", name, "email", email, "response",
					response // 전체 response 객체를 포함할 수도 있음
			);

			// OAuth2User 반환(principal에 사용)
			return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, 
					"email" 
			);
		} else if ("google".equals(registrationId)) {
			// 구글 사용자 응답 처리
			Map<String, Object> attributes = oAuth2User.getAttributes();
			String email = (String) attributes.get("email");
			String name = (String) attributes.get("name");

			// 사용자 저장 또는 조회
			PageUser user = userRepository.findByEmail(email)
					.orElseGet(() -> userRepository.save(new PageUser(name, email, registrationId)));

			// OAuth2User를 반환(principal에 사용)
			return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes,
					"email");
		} else {
			throw new OAuth2AuthenticationException("지원되지 않는 OAuth2 로그인 방식입니다.");
		}
	}
}
