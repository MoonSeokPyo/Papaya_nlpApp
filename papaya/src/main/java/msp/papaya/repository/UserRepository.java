package msp.papaya.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import msp.papaya.model.PageUser;

public interface UserRepository extends JpaRepository<PageUser, Long> {
	Optional<PageUser> findByEmail(String email);
	
}
