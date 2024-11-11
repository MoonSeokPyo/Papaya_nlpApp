package com.hangw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(0, 50);
        return restaurantRepository.findRestaurantsInBoundary(location.getLatitude(), location.getLongitude(), 1.0, pageable);
    }
	
	public List<RestaurantDTO> getRestaurantByLocation(String address, double latitude, double longitude){
		Pageable pageable = PageRequest.of(0, 50);
		return restaurantRepository.findRestaurantsByLocation(address, latitude, longitude, pageable);
	}
	
	public List<RestaurantDTO> sortRByScore(List<RestaurantDTO> restaurants){
		restaurants.sort((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()));
		return restaurants;
	}
	
	public List<RestaurantDTO> searchRestaurantByName(String name, double latitude, double longitude){
		Pageable pageable = PageRequest.of(0, 50);
		return restaurantRepository.searchRestaurantsByName(name, latitude, longitude, pageable);
	}
	
	public List<RestaurantDTO> cutTop10(List<RestaurantDTO> restaurants){
		List<RestaurantDTO> topRestaurants = restaurants.size() > 10 ? restaurants.subList(0, 10) : restaurants;
		return topRestaurants;
	}
}