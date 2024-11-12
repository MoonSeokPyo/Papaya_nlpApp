package com.hangw.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangw.model.DataNotFoundException;
import com.hangw.model.PageUser;
import com.hangw.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final BCryptPasswordEncoder passwordEncoder2;
	
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
	
	public boolean emailExist(String email) {
		Optional<PageUser> user = userRepository.findByEmail(email);
	      return user.isPresent();
	  }

	  public String getTmpPassword() {
	      char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	              'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
	              'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	      String newPassword = "";

	      for (int i = 0; i < 10; i++) {
	          int idx = (int) (charSet.length * Math.random());
	          newPassword += charSet[idx];
	      }

	      return newPassword;
	  }

	  @Transactional
	  public void updatePassword(String tmpPassword, String email) {
	      String encryptedPassword = passwordEncoder2.encode(tmpPassword);
	      PageUser user = userRepository.findByEmail(email).orElseThrow(() ->
	              new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

	      user.setPassword(encryptedPassword);
	      userRepository.save(user);
	  }
	  
	  public boolean isValidUser(String email, String name, String phone) {
		    Optional<PageUser> user = userRepository.findByEmail(email);
		    if (user.isPresent()) {
		        PageUser foundUser = user.get();
		        return foundUser.getName().equals(name) && foundUser.getPhone().equals(phone);
		    }
		    return false;
		}
}