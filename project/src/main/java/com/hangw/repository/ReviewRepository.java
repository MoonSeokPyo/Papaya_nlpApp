package com.hangw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.hangw.model.PageUser;
import com.hangw.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	List<Review> findReviewByWriter(@Param("user") PageUser user);
}
