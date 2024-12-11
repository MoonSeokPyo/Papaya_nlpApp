package msp.papaya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @Column(name = "restaurant_id") //, nullable = false)
  private Integer restaurantId;

  @Column(name = "review", columnDefinition = "TEXT")
  private String review;

  @Column(name = "score", precision = 3, scale = 1)
  private BigDecimal score;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
  @JsonManagedReference
  private Restaurant restaurant;

  public Review(String 리뷰_없음, BigDecimal bigDecimal) {
  }

  @Override
  public String toString() {
    return "Review{" +
        "review='" + review + '\'' +
        ", score=" + score +
        '}';
  }

  // Getters and Setters
}
