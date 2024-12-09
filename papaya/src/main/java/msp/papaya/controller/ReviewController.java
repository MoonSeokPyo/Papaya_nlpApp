package msp.papaya.controller;

import msp.papaya.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/review")
  public String review() {
    return "review"; // review.html로 이동
  }

  @PostMapping("/submitReview")
  public String submitReview(@RequestParam("review") String review, Model model) {
    if (review == null || review.trim().isEmpty()) {
      model.addAttribute("error", "리뷰를 입력해주세요.");
      return "review";
    }
    if (review.length() > 500) {
      model.addAttribute("error", "리뷰는 500자 이하로 작성해주세요.");
      return "review";
    }

    String score = reviewService.getReviewScore(review);
    model.addAttribute("review", review);
    model.addAttribute("score", score);
    return "result"; // result.html로 이동
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleException(Model model, Exception e) {
    model.addAttribute("error", "예기치 못한 오류가 발생했습니다: " + e.getMessage());
    return "error"; // 에러 페이지로 이동
  }
}
