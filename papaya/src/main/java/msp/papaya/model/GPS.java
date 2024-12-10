package msp.papaya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="gpscoordinates")
public class GPS {

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long restaurantId;
//
//  @Column(precision = 10, scale = 6)
//  private double latitude;
//
//  @Column(precision = 10, scale = 6)
//  private double longitude;

  @Id
  @Column(name = "restaurant_id")
  private Integer restaurantId; // MySQL의 INT에 매핑

  @Column(precision = 10, scale = 6) // DECIMAL(10,6)에 정확히 매핑
  private BigDecimal latitude;
//  private Double latitude;

  @Column(precision = 10, scale = 6) // DECIMAL(10,6)에 정확히 매핑
  private BigDecimal longitude;
//  private Double longitude;

  // Getters and Setters
}
