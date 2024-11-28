package com.hangw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/spring")
public class PythonApiController {

  @Autowired
  private RestTemplate restTemplate;

  private final String pythonApiUrl = "http://127.0.0.1:8000/analyze_review";
  private final String pythonApiTestUrl = "http://127.0.0.1:8000/test";

  @PostMapping("/analyze_review")
  public ResponseEntity<String> analyze_review(@RequestBody Review review) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Review> request = new HttpEntity<>(review, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(pythonApiUrl, request, String.class);
//    console.log(response.review);
    return response;
  }

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    ResponseEntity<String> response = restTemplate.getForEntity(pythonApiTestUrl, String.class);
    return response;
  }

}

class Review {
  private String review = "음식이 맛있어요.";

  // Getters and setters
}

