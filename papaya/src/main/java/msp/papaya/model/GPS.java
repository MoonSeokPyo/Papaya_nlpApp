package msp.papaya.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

  @OneToOne
  @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
  @JsonManagedReference
  private Restaurant restaurant;

//  @ManyToOne
//  @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
//  @JsonBackReference
//  private Restaurant restaurant;


  @Override
  public String toString() {
    return "GPS{" +
        "latitude=" + latitude +
        ", longitude=" + longitude +
        '}';
  }

}
