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
	public PageUser create(String username, String name, String email, String password, String gender) {
		PageUser user = new PageUser();
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));	
		user.setGender(gender);
		
		userRepository.save(user);
		return user;
	}
	
	public PageUser getUser(String username) {
		Optional<PageUser> boardUser = userRepository.findByUsername(username);
		if(boardUser.isPresent()) {
			return boardUser.get();
		}else {
			throw new DataNotFoundException("user not found");
		}
	}
}
