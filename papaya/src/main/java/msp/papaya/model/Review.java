package msp.papaya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "restaurant_id", nullable = false)
  private Integer restaurantId;

  @Column(name = "review", columnDefinition = "TEXT")
  private String review;

  @Column(name = "score", precision = 3, scale = 1)
  private BigDecimal score;

  // Getters and Setters
}
