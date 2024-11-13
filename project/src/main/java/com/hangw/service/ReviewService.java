package com.hangw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hangw.model.DataNotFoundException;
import com.hangw.model.PageUser;
import com.hangw.model.Restaurant;
import com.hangw.model.Review;
import com.hangw.repository.RestaurantRepository;
import com.hangw.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final RestaurantRepository restaurantRepository;
	
	public void addReview(Review review) {			//review db에 저장
		reviewRepository.save(review);
	}
	
	public List<Review> viewReview(long restaurantId) {		//restaurant이랑 연결되어있는 리뷰 모두 출력
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new DataNotFoundException("Restaurnat not found"));
		List<Review> reviews = restaurant.getReviews();
		return reviews;
	}
	
	public Review getReview(long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new DataNotFoundException("Review not found"));
		return review;
	}
	
	public List<Review> getReviewsByWriter(PageUser user){
		List<Review> reviews = reviewRepository.findReviewByWriter(user);
		return reviews;
	}
}
