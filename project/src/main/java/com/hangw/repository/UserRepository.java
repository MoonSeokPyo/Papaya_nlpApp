package com.hangw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangw.model.PageUser;

public interface UserRepository extends JpaRepository<PageUser, Long>{

	Optional<PageUser> findByUsername(String username);
}
