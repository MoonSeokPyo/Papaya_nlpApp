package msp.papaya.model;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "restaurant_id", nullable = false)
  private Integer restaurantId;

  @Column(name = "review_count", nullable = false)
  private Integer reviewCount;

  @Column(name = "average_score", precision = 3, scale = 2)
  private BigDecimal averageScore;

  // Getters and Setters
}
