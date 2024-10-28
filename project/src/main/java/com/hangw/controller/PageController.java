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
import com.hangw.service.GeocodingService;
import com.hangw.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
	private final RestaurantService restaurantService;
	private final GeocodingService geocodingService;
	
	@GetMapping("/")
	public String showHomepage() {
		return "home";
	}
	
	@GetMapping("/search")
    public String searchRestaurants(@RequestParam("address") String address, Model model) {
		try {
            List<RestaurantDTO> restaurants = restaurantService.getNearbyRestaurants(address);
            Location location = geocodingService.getCoordinates(address);
            model.addAttribute("restaurants", restaurants);
            model.addAttribute("location", location);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "주위의 음식점을 찾을 수 없습니다.");
        }
        return "result";
    }

	@GetMapping("/restaurant/detail")
	public ModelAndView viewRestaurant(@RequestParam(value = "id") Long restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("restaurantDetail");
	    mv.addObject("restaurant", restaurant);
	    return mv;
	}

}
