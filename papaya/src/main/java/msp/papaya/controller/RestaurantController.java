package msp.papaya.controller;

import msp.papaya.model.Restaurant;
import msp.papaya.model.Review;
import msp.papaya.model.Score;
import msp.papaya.service.RestaurantService;
import msp.papaya.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
//@RequestMapping("/restaurants")
public class RestaurantController {

  @Autowired
  private RestaurantServiceImpl restaurantService;
//  private final RestaurantServiceImpl restaurantServiceImpl;
//
//  @Autowired
//  public RestaurantController(RestaurantServiceImpl restaurantServiceImpl) {
//    this.restaurantServiceImpl = restaurantServiceImpl;
//  }

  @GetMapping("/{id}")
  public Restaurant getRestaurantDetails(@PathVariable Integer id) {
    // Restaurant 데이터를 가져옴
    Restaurant restaurant = restaurantService.getRestaurantDetails(id);

    // JSON 데이터 간단히 출력
    System.out.println("Restaurant JSON Data: " + restaurant);

    return restaurant;
  }

//  @GetMapping("/{id}")
//  public String getRestaurantDetails(@PathVariable Long id, Model model) {
//    System.out.println("요청된 ID: " + id); // 요청 ID를 출력
//    Restaurant restaurant = restaurantServiceImpl.getRestaurantById(id);
//    Score score = restaurantServiceImpl.getScoreByRestaurantId(Math.toIntExact(id));
//    List<Review> reviews = restaurantServiceImpl.getReviewsByRestaurantId(Math.toIntExact(id));
//
//    model.addAttribute("restaurant", restaurant);
//    model.addAttribute("score", score);
//    model.addAttribute("reviews", reviews);
//
//    return "restaurant";
//  }

  @GetMapping("/search")
  public ResponseEntity<List<Map<String, Object>>> searchRestaurants(@RequestParam String keyword) {
    List<Restaurant> results = restaurantService.searchRestaurantsByKeyword(keyword);

    // 필요한 데이터만 선택하여 반환
    List<Map<String, Object>> response = results.stream().map(restaurant -> {
      Map<String, Object> map = new HashMap<>();
      map.put("name", restaurant.getBusinessName());
      map.put("address", restaurant.getRoadAddress() != null ? restaurant.getRoadAddress() : restaurant.getLocationAddress());
      map.put("phone", restaurant.getLocationPhone());
      map.put("latitude", restaurant.getGps().getLatitude());
      map.put("longitude", restaurant.getGps().getLongitude());
      return map;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

//  @GetMapping("/restaurant/{id}")
//  public String restaurantPage(@PathVariable Integer id, Model model) {
//    Restaurant restaurant = restaurantService.getRestaurantById(id);
//    model.addAttribute("restaurant", restaurant);
//    return "restaurant"; // restaurant.html 템플릿
//  }
}
