package msp.papaya.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="restauranttable")
public class Restaurant {

  @Id
  @Column(name = "id")
  private Integer id;

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

//  @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
//  @JsonManagedReference
//  private GPS gps;
//
//  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//  @JsonManagedReference
//  private List<Review> reviews;
//
//  @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
//  @JsonManagedReference
//  private Score scores;


  @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
  @JsonManagedReference
  private GPS gps;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Review> reviews;

  @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
  @JsonManagedReference
  private Score scores;


  @Override
  public String toString() {
    return "Restaurant{" +
        "id=" + id +
        ", businessName='" + businessName + '\'' +
        ", businessType='" + businessType + '\'' +
        ", roadAddress='" + roadAddress + '\'' +
        ", locationPhone='" + locationPhone + '\'' +
        ", locationZipcode='" + locationZipcode + '\'' +
        ", gps=" + gps +
        ", reviews=" + reviews +
        ", scores" + scores +
        '}';
  }

//  @OneToOne
//  @JoinColumn(name = "id", referencedColumnName = "restaurant_id")
//  private GPS gps; // GPS 좌표 연결
//
//  @OneToOne
//  @JoinColumn(name = "id", referencedColumnName = "restaurant_id")
//  private Review review; // GPS 좌표 연결
//
//  @OneToOne
//  @JoinColumn(name = "id", referencedColumnName = "restaurant_id")
//  private Score score; // GPS 좌표 연결

  // Getters and Setters
//  public String getLocationPhone() {
//    return locationPhone;
//  }
}


