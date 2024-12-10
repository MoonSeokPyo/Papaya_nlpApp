package msp.papaya.controller;

import msp.papaya.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

//  @GetMapping("/review")
//  public String review() {
//    return "review"; // review.html로 이동
//  }

  @PostMapping("/submitReview")
  @ResponseBody // JSON 응답을 위한 추가
  public ResponseEntity<?> submitReview(@RequestBody Map<String, String> request) {
    String review = request.get("review");

    if (review == null || review.trim().isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("error", "리뷰를 입력해주세요."));
    }

    if (review.length() > 500) {
      return ResponseEntity.badRequest().body(Map.of("error", "리뷰는 500자 이하로 작성해주세요."));
    }

    String score = reviewService.getReviewScore(review);

    return ResponseEntity.ok(Map.of("review", review, "score", score));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleException(Model model, Exception e) {
    model.addAttribute("error", "예기치 못한 오류가 발생했습니다: " + e.getMessage());
    return "error"; // 에러 페이지로 이동
  }
}
