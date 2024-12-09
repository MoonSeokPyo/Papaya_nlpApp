package msp.papaya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="restauranttable")
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "location_phone")
  private String locationPhone;

  @Column(name = "location_zipcode")
  private String locationZipcode;

  @Column(name = "location_address")
  private String locationAddress;

  @Column(name = "road_address")
  private String roadAddress;

  @Column(name = "road_zipcode")
  private String roadZipcode;

  @Column(name = "business_name")
  private String businessName;

  @Column(name = "business_type")
  private String businessType;

  // Getters and Setters
//  public String getLocationPhone() {
//    return locationPhone;
//  }
}


