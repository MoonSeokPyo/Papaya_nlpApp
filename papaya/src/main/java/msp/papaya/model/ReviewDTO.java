package msp.papaya.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO {

  private Integer id;           // Review의 고유 ID
  private Integer restaurantId; // 리뷰가 속한 Restaurant ID
  private String reviewText;    // 리뷰 내용
  private Double score;         // 리뷰 점수

  @Override
  public String toString() {
    return "ReviewDTO{" +
        "id=" + id +
        ", restaurantId=" + restaurantId +
        ", reviewText='" + reviewText + '\'' +
        ", score=" + score +
        '}';
  }
}
