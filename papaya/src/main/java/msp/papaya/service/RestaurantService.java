package msp.papaya.service;

import msp.papaya.model.GPS;
import msp.papaya.model.Restaurant;
import msp.papaya.repository.GPSRepository;
import msp.papaya.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
/**
  private final RestaurantRepository restaurantRepository;

  public RestaurantService(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  public List<Restaurant> getPopularRestaurants() {
    return restaurantRepository.findAll(); // 평점 순 정렬을 추가할 수 있음
  }

  public Restaurant getRestaurantById(Long id) {
    return restaurantRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));
  }
**/

  private final RestaurantRepository restaurantRepository;
  private final GPSRepository gpsRepository;

  public RestaurantService(RestaurantRepository restaurantRepository, GPSRepository gpsRepository) {
    this.restaurantRepository = restaurantRepository;
    this.gpsRepository = gpsRepository;
  }

  public List<Restaurant> getAllRestaurants() {
    return restaurantRepository.findAll();
  }

  public Restaurant getRestaurantById(Long id) {
    return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
  }

  public GPS getCoordinatesByRestaurantId(Long restaurantId) {
    return gpsRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Coordinates not found for restaurant id: " + restaurantId));
  }

//  public List<Restaurant> searchRestaurantsByKeyword(String keyword) {
//    return restaurantRepository.findByBusinessNameContainingIgnoreCase(keyword);
//  }

  public List<Restaurant> searchRestaurantsByKeyword(String keyword) {
    List<Restaurant> results = restaurantRepository.findByBusinessNameContainingIgnoreCase(keyword);

    // Null-safe 처리
    results.forEach(restaurant -> {
      if (restaurant.getLocationPhone() == null) {
        restaurant.setLocationPhone("전화번호 없음");
      }
      if (restaurant.getLocationZipcode() == null) {
        restaurant.setLocationZipcode("우편번호 없음");
      }
      if (restaurant.getLocationAddress() == null) {
        restaurant.setLocationAddress("주소 없음");
      }
      if (restaurant.getRoadAddress() == null) {
        restaurant.setRoadAddress("도로명 주소 없음");
      }
      if (restaurant.getRoadZipcode() == null) {
        restaurant.setRoadZipcode("도로명 우편번호 없음");
      }
      if (restaurant.getBusinessName() == null) {
        restaurant.setBusinessName("이름 없음");
      }
      if (restaurant.getBusinessType() == null) {
        restaurant.setBusinessType("업종 없음");
      }
    });

    return results;
  }
}
