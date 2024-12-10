package msp.papaya.controller;

import msp.papaya.model.Restaurant;
import msp.papaya.service.RestaurantService;
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
public class RestaurantController {

  private final RestaurantService restaurantService;

  @Autowired
  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

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

  @GetMapping("/restaurant/{id}")
  public String restaurantPage(@PathVariable Long id, Model model) {
    Restaurant restaurant = restaurantService.getRestaurantById(id);
    model.addAttribute("restaurant", restaurant);
    return "restaurant"; // restaurant.html 템플릿
  }
}
