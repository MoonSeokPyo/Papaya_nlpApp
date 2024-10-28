package com.hangw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hangw.model.UserRole;
import com.hangw.model.PageUser;
import com.hangw.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService{
	private final UserRepository userRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<PageUser> _User = userRepository.findByUsername(username);
		if(_User.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없음");
			}
		PageUser Users = _User.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User(Users.getUsername(),Users.getPassword(), authorities);
	}
}
