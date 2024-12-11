package msp.papaya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

  @Override
  public String toString() {
    return "Review{" +
        "review='" + review + '\'' +
        ", score=" + score +
        '}';
  }

  // Getters and Setters
}
