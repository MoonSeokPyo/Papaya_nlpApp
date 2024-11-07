package com.hangw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hangw.model.Location;
import com.hangw.model.Restaurant;
import com.hangw.model.RestaurantDTO;
import com.hangw.model.Review;
import com.hangw.service.GeocodingService;
import com.hangw.service.RestaurantService;
import com.hangw.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
	private final RestaurantService restaurantService;
	private final GeocodingService geocodingService;
	private final ReviewService reviewService;

	@GetMapping("/") // 맨 처음 메인 화면 출력
	public String showHomepage() {
		return "mainpage";
	}

	@GetMapping("/search/boundary") // 검색결과 처리(현재 사용자 위치와 그 주위 음식점들의 데이터를 페이지에 넘김)
	public String searchRestaurants(@RequestParam String address, Model model) {
		try {
			List<RestaurantDTO> restaurants = restaurantService.getNearbyRestaurants(address);
			Location location = geocodingService.getCoordinates(address);
			restaurants = restaurantService.sortRByScore(restaurants);
			model.addAttribute("restaurants", restaurants);
			model.addAttribute("location", location);

			if (restaurants.isEmpty())
				model.addAttribute("errorMessage", "주위의 음식점을 찾을 수 없습니다.");
		} catch (Exception e) {
			model.addAttribute("errorMessage", "주위의 음식점을 찾을 수 없습니다.");
		}
		return "result";
	}

	@GetMapping("/search/location") // 검색결과처리(특정 동네 이름을 검색하면 주소에 그 동네 이름이 들어있는 음식점들의 데이터만 넘김)
	public String searchRestaurantsByLocation(@RequestParam String address, Model model) {
		try {
			Location location = geocodingService.getCoordinates(address);
			List<RestaurantDTO> restaurants = restaurantService.getRestaurantByLocation(address, location.getLatitude(),
					location.getLongitude());
			restaurants = restaurantService.sortRByScore(restaurants);
			model.addAttribute("restaurants", restaurants);
			model.addAttribute("location", location);

			if (restaurants.isEmpty())
				model.addAttribute("errorMessage", "동네의 맛집을 찾을 수 없습니다.");
		} catch (Exception e) {
			model.addAttribute("errorMessage", "동네의 맛집을 찾을 수 없습니다.");
		}
		return "result";
	}

	@GetMapping("/restaurant/detail") // 음식점 상세정보 처리
	public ModelAndView viewRestaurant(@RequestParam(value = "id") Long restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		List<Review> reviews = reviewService.viewReview(restaurantId);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("restaurantDetail");
		mv.addObject("restaurant", restaurant);
		mv.addObject("reviews", reviews);
		return mv;
	}

	@GetMapping("/search/restaurant") // 검색을 통한 음식점 상세정보페이지 연결
	public ModelAndView searchRestaurant(@RequestParam String address, @RequestParam String name) {
		ModelAndView mv = new ModelAndView();
		try {
			Location location = geocodingService.getCoordinates(address);
			List<RestaurantDTO> restaurants = restaurantService.searchRestaurantByName(name, location.getLatitude(),location.getLongitude());

			if (restaurants.size() == 1) {
				Restaurant restaurant = restaurantService.getRestaurant(name);
				List<Review> reviews = reviewService.viewReview(restaurant.getId());

				mv.setViewName("restaurantDetail");
				mv.addObject("restaurant", restaurant);
				mv.addObject("reviews", reviews);
			} else {
				restaurants = restaurantService.sortRByScore(restaurants);
				mv.setViewName("result");
				mv.addObject("restaurants", restaurants);
				mv.addObject("location", location);
			}

		} catch (Exception e) {
			mv.addObject(e);
		}

		return mv;

	}

	@GetMapping("/ranking") // 평점이 좋은 음식점 랭킹
	public String ranking(Model model) {
		List<RestaurantDTO> restaurants = restaurantService.cutTop10(null);
		return "result";
	}
}