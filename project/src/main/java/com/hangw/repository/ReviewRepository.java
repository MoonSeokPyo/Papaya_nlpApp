package com.hangw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangw.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}
