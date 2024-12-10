package msp.papaya.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @Value("${kakao.api.key}")
  private String kakaoApiKey;

//  @Value("${google.api.key}")
//  private String googleApiKey;

//  private final RestaurantService restaurantService;
//  public PageController(RestaurantService restaurantService) {
//    this.restaurantService = restaurantService;
//  }

  @GetMapping("/")
  public String mainPage() {
    return "index"; // index.html 템플릿으로 이동
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login"; // login.html 템플릿
  }

  @GetMapping("/map")
  public String mapPage(Model model) {
    model.addAttribute("kakaoApiKey", kakaoApiKey);
    return "map"; // map.html 템플릿
  }

  @GetMapping("/notice")
  public String noticePage() {
    return "notice"; // notice.html 템플릿으로 이동
  }

  @GetMapping("/introduce")
  public String introducePage() {
    return "introduce"; // introduce.html 템플릿으로 이동
  }

  @GetMapping("/inquiry")
  public String inquiryPage() {
    return "inquiry"; // introduce.html 템플릿으로 이동
  }

  @GetMapping("/aitest")
  public String aitestPage() {
    return "aitest";
  }

//  @GetMapping("/restaurant/{id}")
//  public String restaurantPage(@PathVariable Long id, Model model) {
//    Restaurant restaurant = restaurantService.getRestaurantById(id);
//    model.addAttribute("restaurant", restaurant);
//    return "restaurant"; // restaurant.html 템플릿
//  }

//  @GetMapping("/search")
//  public String searchPage() {
//    return "search"; // search.html 템플릿으로 이동
//  }
//
//  @GetMapping("/search/results")
//  public String searchResults(@RequestParam("keyword") String keyword, Model model) {
//    List<Restaurant> results = restaurantService.searchRestaurantsByKeyword(keyword);
//    model.addAttribute("results", results != null ? results : List.of()); // null 방지
//    model.addAttribute("keyword", keyword);
//    return "search_results";
//  }

  //  @GetMapping("/restaurants")
//  public List<RestaurantTable> getAllRestaurants() {
//    return restaurantService.getAllRestaurants();
//  }
//
//  @GetMapping("/restaurants/{id}")
//  public RestaurantTable getRestaurantById(@PathVariable Long id) {
//    return restaurantService.getRestaurantById(id);
//  }
//
//  @GetMapping("/restaurants/{id}/coordinates")
//  public GpsCoordinates getCoordinatesByRestaurantId(@PathVariable Long id) {
//    return restaurantService.getCoordinatesByRestaurantId(id);
//  }
  @GetMapping("/test")
  public String test() {
    return "test"; // search.html 템플릿으로 이동
  }
}
