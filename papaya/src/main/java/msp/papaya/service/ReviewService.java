package msp.papaya.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewService {
  private final RestTemplate restTemplate;

    private final String FASTAPI_URL = "http://127.0.0.1:8000/analyze_review/";
//  private final String FASTAPI_URL = "http://analyze_review:8000/analyze_review/";

  public ReviewService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String getReviewScore(String review) {

    // HTTP 요청 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // 요청 본문 생성
    Map<String, String> body = new HashMap<>();
    body.put("review", review);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

    try {
      Map response = restTemplate.postForObject(FASTAPI_URL, request, Map.class);
      return response.get("predicted_score").toString();
    } catch (Exception e) {
      return "FastAPI 서버와 통신 중 오류 발생: " + e.getMessage();
    }

//    String url = UriComponentsBuilder.fromHttpUrl("http://analyze_review:8000/analyze_review/")
//    String url = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8000/analyze_review/")
//        .queryParam("text", review)
//        .toUriString();

//    return restTemplate.getForObject(url, String.class);

//    try {
//      return restTemplate.getForObject(url, String.class);
//    } catch (HttpClientErrorException e) {
//      if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
//        return "입력 데이터가 올바르지 않습니다.";
//      } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//        return "리뷰 점수를 계산하는 서버를 찾을 수 없습니다.";
//      } else {
//        return "알 수 없는 오류가 발생했습니다.";
//      }
//    } catch (Exception e) {
//      return "서버와의 통신 중 오류가 발생했습니다.";
//    }
  }
}
