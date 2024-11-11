package com.hangw.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangw.model.DataNotFoundException;
import com.hangw.model.PageUser;
import com.hangw.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	public PageUser create(String name, String email, String phone, String password) {
		PageUser user = new PageUser();
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(passwordEncoder.encode(password));	
		
		userRepository.save(user);
		return user;
	}
	
	public PageUser getUser(String email) {
		Optional<PageUser> boardUser = userRepository.findByEmail(email);
		if(boardUser.isPresent()) {
			return boardUser.get();
		}else {
			throw new DataNotFoundException("user not found");
		}
	}
	
	public String getUserId(String name, String phone) {
		Optional<PageUser> user = userRepository.findByNameAndPhone(name, phone);
		if(user.isPresent()) {
			return user.get().getEmail();
		}else {
			throw new DataNotFoundException("user not found"); 
		}
	}
}