package msp.papaya.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDTO {

  private Integer restaurantId;  // Restaurant ID
  private Integer reviewCount;   // 리뷰 개수
  private Double averageScore;   // 평균 점수

  @Override
  public String toString() {
    return "ScoreDTO{" +
        "restaurantId=" + restaurantId +
        ", reviewCount=" + reviewCount +
        ", averageScore=" + averageScore +
        '}';
  }
}
