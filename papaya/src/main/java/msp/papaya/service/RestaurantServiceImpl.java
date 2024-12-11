package msp.papaya.service;

import msp.papaya.model.*;
import msp.papaya.repository.GPSRepository;
import msp.papaya.repository.RestaurantRepository;
import msp.papaya.repository.ReviewRepository;
import msp.papaya.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
  /**
   * private final RestaurantRepository restaurantRepository;
   * <p>
   * public RestaurantService(RestaurantRepository restaurantRepository) {
   * this.restaurantRepository = restaurantRepository;
   * }
   * <p>
   * public List<Restaurant> getPopularRestaurants() {
   * return restaurantRepository.findAll(); // 평점 순 정렬을 추가할 수 있음
   * }
   * <p>
   * public Restaurant getRestaurantById(Long id) {
   * return restaurantRepository.findById(id)
   * .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));
   * }
   **/

//  private final RestaurantRepository restaurantRepository;
//  private final GPSRepository gpsRepository;
//
//  public RestaurantServiceImpl(RestaurantRepository restaurantRepository, GPSRepository gpsRepository) {
//    this.restaurantRepository = restaurantRepository;
//    this.gpsRepository = gpsRepository;
//  }

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private ScoreRepository scoreRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private GPSRepository gpsRepository;

  public Restaurant getRestaurantDetails(Integer id) {
//    Optional<Restaurant> restaurant = restaurantRepository.findById(id);
//    return restaurant.orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    // ID로 레스토랑 검색
    Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
    Restaurant restaurant = restaurantOptional.orElseThrow(() ->
        new RuntimeException("Restaurant not found with id: " + id)
    );

    // 필드 값이 없으면 기본값으로 치환
    if (restaurant.getBusinessName() == null || restaurant.getBusinessName().isEmpty()) {
      restaurant.setBusinessName("이름 없음");
    }
    if (restaurant.getBusinessType() == null || restaurant.getBusinessType().isEmpty()) {
      restaurant.setBusinessType("업종 없음");
    }
    if (restaurant.getRoadAddress() == null || restaurant.getRoadAddress().isEmpty()) {
      restaurant.setRoadAddress("도로명 주소 없음");
    }
    if (restaurant.getLocationPhone() == null || restaurant.getLocationPhone().isEmpty()) {
      restaurant.setLocationPhone("전화번호 없음");
    }
    if (restaurant.getLocationZipcode() == null || restaurant.getLocationZipcode().isEmpty()) {
      restaurant.setLocationZipcode("우편번호 없음");
    }
    if (restaurant.getLocationAddress() == null || restaurant.getLocationAddress().isEmpty()) {
      restaurant.setLocationAddress("주소 없음");
    }
    if (restaurant.getRoadZipcode() == null || restaurant.getRoadZipcode().isEmpty()) {
      restaurant.setRoadZipcode("도로명 우편번호 없음");
    }
    if (restaurant.getGps() == null) {
      GPS defaultGps = new GPS();
      defaultGps.setLatitude(BigDecimal.valueOf(0.0));
      defaultGps.setLongitude(BigDecimal.valueOf(0.0));
      restaurant.setGps(defaultGps);
    }
    if (restaurant.getScores() == null) {
      Score defaultScore = new Score();
      defaultScore.setReviewCount(0);
      defaultScore.setAverageScore(BigDecimal.valueOf(0.0));
      restaurant.setScores(defaultScore);
    }
    if (restaurant.getReviews() == null || restaurant.getReviews().isEmpty()) {
      restaurant.setReviews(List.of(new Review("리뷰 없음", BigDecimal.valueOf(0.0))));
    }

    // 모든 필드가 정상인 경우 레스토랑 반환
    return restaurant;
  }

  @Override
  public Restaurant getRestaurantById(Integer id) {
    return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
  }

  @Override
  public Score getScoreByRestaurantId(Integer restaurantId) {
    return scoreRepository.findByRestaurantId(restaurantId).orElseThrow(() -> new RuntimeException("Score not found"));
  }

  @Override
  public List<Review> getReviewsByRestaurantId(Integer restaurantId) {
    return reviewRepository.findByRestaurantId(restaurantId);
  }


  public List<Restaurant> getAllRestaurants() {
    return restaurantRepository.findAll();
  }



  public GPS getCoordinatesByRestaurantId(Integer restaurantId) {
    return gpsRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Coordinates not found for restaurant id: " + restaurantId));
  }

//  public List<Restaurant> searchRestaurantsByKeyword(String keyword) {
//    return restaurantRepository.findByBusinessNameContainingIgnoreCase(keyword);
//  }

  public List<Restaurant> searchRestaurantsByKeyword(String keyword) {
    List<Restaurant> results = restaurantRepository.findByBusinessNameContainingIgnoreCase(keyword);

//    // Null-safe 처리
//    results.forEach(restaurant -> {
//      if (restaurant.getLocationPhone() == null) {
//        restaurant.setLocationPhone("전화번호 없음");
//      }
//      if (restaurant.getLocationZipcode() == null) {
//        restaurant.setLocationZipcode("우편번호 없음");
//      }
//      if (restaurant.getLocationAddress() == null) {
//        restaurant.setLocationAddress("주소 없음");
//      }
//      if (restaurant.getRoadAddress() == null) {
//        restaurant.setRoadAddress("도로명 주소 없음");
//      }
//      if (restaurant.getRoadZipcode() == null) {
//        restaurant.setRoadZipcode("도로명 우편번호 없음");
//      }
//      if (restaurant.getBusinessName() == null) {
//        restaurant.setBusinessName("이름 없음");
//      }
//      if (restaurant.getBusinessType() == null) {
//        restaurant.setBusinessType("업종 없음");
//      }
//
//      // GPS 처리
//      GPS gps = restaurant.getGps();
//      if (gps == null || gps.getLatitude() == null || gps.getLongitude() == null ||
//          gps.getLatitude().compareTo(BigDecimal.valueOf(-1)) == 0 ||
//          gps.getLongitude().compareTo(BigDecimal.valueOf(-1)) == 0) {
//        // GPS가 없거나 좌표가 -1인 경우 기본값 설정
//        if (gps == null) {
//          gps = new GPS();
//          restaurant.setGps(gps);
//        }
//        gps.setLatitude(BigDecimal.ZERO); // 기본값 설정 (0.0)
//        gps.setLongitude(BigDecimal.ZERO); // 기본값 설정 (0.0)
//      }
//    });
//
//    return results;

    return results.stream()
        .filter(restaurant -> {
          GPS gps = restaurant.getGps();
          return gps != null && gps.getLatitude() != null && gps.getLongitude() != null &&
              gps.getLatitude().compareTo(BigDecimal.valueOf(-1)) != 0 &&
              gps.getLongitude().compareTo(BigDecimal.valueOf(-1)) != 0;
        })
        .peek(restaurant -> {
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
        })
        .collect(Collectors.toList());
  }
}
