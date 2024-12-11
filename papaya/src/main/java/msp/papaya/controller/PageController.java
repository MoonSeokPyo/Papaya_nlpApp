package msp.papaya.controller;

import java.security.Principal;
import java.util.Optional;

import msp.papaya.service.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import msp.papaya.model.PageUser;
import msp.papaya.model.Restaurant;
import msp.papaya.repository.UserRepository;

@RequiredArgsConstructor
@Controller
public class PageController {

  private final String API_URL = "http://localhost:8080/api/restaurants/";
//  private final String API_URL = "https://papaya.re.kr/api/restaurants/";

//  @Value("${google.api.key}")
//  private String googleApiKey;
  @Value("${kakao.api.key}")
  private String kakaoApiKey;

  private final RestTemplate restTemplate;
  private final UserRepository userRepository;
  private final RestaurantServiceImpl restaurantService;

  @Autowired
  public PageController(RestTemplate restTemplate, RestaurantServiceImpl restaurantService, UserRepository userRepository) {
    this.restTemplate = restTemplate;
    this.restaurantService = restaurantService;
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  public String mainPage() {
    return "index"; // index.html 템플릿으로 이동
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

  @GetMapping("/userinfo")
  public String userinfoPage(Model model, Principal principal) {
    String email = principal.getName();
    Optional<PageUser> user = userRepository.findByEmail(email);
    PageUser user1 = user.get();
    String name = user1.getName();
    model.addAttribute("name", name);
    model.addAttribute("email", email);
    return "userinfo";
  }

  // 상세 음식점 페이지 id로 호출
  @GetMapping("/restaurant/{id}")
  public String restaurantPage(@PathVariable Integer id, Model model) {
    System.out.println("요청된 ID: " + id); // 요청 ID를 출력
    Restaurant restaurant = restaurantService.getRestaurantDetails(id);
    // 디버깅용 데이터 확인
    System.out.println("Restaurant JSON Data: " + restaurant);
    // 모델에 데이터 추가
    model.addAttribute("restaurant", restaurant);
    model.addAttribute("kakaoApiKey", kakaoApiKey);

    // Model 데이터를 디버깅 출력
    if (model instanceof org.springframework.ui.ModelMap) {
      System.out.println("ModelMap: " + ((org.springframework.ui.ModelMap) model).toString());
    } else {
      System.out.println("Model contains: restaurant=" + model.getAttribute("restaurant"));
      System.out.println("Model contains: kakaoApiKey=" + model.getAttribute("kakaoApiKey"));
    }

    System.out.println("Ready to restaurant.html");
    // 템플릿 반환
    return "restaurant"; // restaurant.html 템플릿
  }
  /////////////////////////////////////////////////////////////////////////////////
  // 가림막

















/////////////////////////////////////////////////////////////////////////////////
//  @GetMapping("/login")
//  public String loginPage() {
//    return "login"; // login.html 템플릿
//  }

//  // HTML 페이지 반환
//  @GetMapping("/restaurant/{id}")
//  public String restaurantPage(@PathVariable Integer id, Model model) {
//    // API 호출 경로
//    String apiUrl = API_URL + id;
//
//    // API 호출
//    Restaurant restaurant = restTemplate.getForObject(apiUrl, Restaurant.class);
//
//    // 디버깅용 데이터 확인
//    System.out.println("Restaurant from API: " + restaurant);
//
//    // 모델에 데이터 추가
//    model.addAttribute("restaurant", restaurant);
//    model.addAttribute("kakaoApiKey", kakaoApiKey);
//
//    // 템플릿 반환
//    return "restaurant"; // restaurant.html 템플릿
//  }


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
