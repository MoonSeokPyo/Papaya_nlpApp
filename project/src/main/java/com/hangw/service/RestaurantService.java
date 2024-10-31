package com.hangw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hangw.model.DataNotFoundException;
import com.hangw.model.Location;
import com.hangw.model.Restaurant;
import com.hangw.model.RestaurantDTO;
import com.hangw.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final GeocodingService geocodingService;
	
	public Restaurant getRestaurant(String rName) {
		Optional<Restaurant> restaurant = restaurantRepository.findByName(rName);
		if(restaurant.isPresent())
			return restaurant.get();
		else
			throw new DataNotFoundException();
	}
	
	public Restaurant getRestaurantById(long id) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if(restaurant.isPresent())
			return restaurant.get();
		else
			throw new DataNotFoundException();
	}
	
	public List<RestaurantDTO> getNearbyRestaurants(String address) throws Exception {
        // Geocoding API를 통해 주소를 위도와 경도로 변환
        Location location = geocodingService.getCoordinates(address);
        // DB에서 위도와 경도를 기준으로 1km 반경 내 음식점 찾기
        return restaurantRepository.findRestaurantsByLocation(location.getLatitude(), location.getLongitude(), 1.0);
    }
}
