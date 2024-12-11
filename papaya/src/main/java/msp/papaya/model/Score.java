package msp.papaya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "scores")
public class Score {

  @Id
  @Column(name = "restaurant_id") //, nullable = false)
  private Integer restaurantId;

  @Column(name = "review_count", nullable = false)
  private Integer reviewCount;

  @Column(name = "average_score", precision = 3, scale = 2)
  private BigDecimal averageScore;

  @OneToOne
  @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
  @JsonManagedReference
  private Restaurant restaurant;

  @Override
  public String toString() {
    return "Score{" +
        "reviewCount=" + reviewCount +
        ", averageScore=" + averageScore +
        '}';
  }

}
