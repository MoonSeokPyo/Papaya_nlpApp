package com.hangw.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hangw.model.Review;
import com.hangw.service.RestaurantService;
import com.hangw.service.ReviewService;
import com.hangw.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
	private final UserService userService;
	private final RestaurantService restaurantService;
	private final ReviewService reviewService;
	
	@PostMapping("/add")
	@PreAuthorize("isAuthenticated")
	public String addReview(@RequestParam long restId, @RequestParam String content, Principal principal, RedirectAttributes redirectAtt) {
		long restaurantId = restId;
		Review review = new Review();
		review.setContent(content);
		review.setWriter(userService.getUser(principal.getName()));
		review.setRestaurant(restaurantService.getRestaurantById(restaurantId));
		
		reviewService.addReview(review);
		
		redirectAtt.addAttribute("id", restId);
		return"redirect:/restaurant/detail";
	}
}
