package com.hangw.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewScoreService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL = "http://127.0.0.1:8000/analyze-sentiment";

    public double getScore(String reviewContent) {
        // 요청 데이터 생성
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("review", reviewContent);

        // FastAPI에 POST 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(FASTAPI_URL, requestBody, Map.class);

        // 응답 데이터에서 점수 추출
        if (response.getBody() != null && response.getBody().containsKey("score")) {
            return Double.parseDouble(response.getBody().get("score").toString());
        } else {
            throw new RuntimeException("FastAPI 서버에서 점수를 받지 못했습니다.");
        }
    }
}
