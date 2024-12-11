package msp.papaya.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantDTO {

  private Integer id;
  private String locationPhone;
  private String locationZipcode;
  private String locationAddress;
  private String roadAddress;
  private String roadZipcode;
  private String businessName;
  private String businessType;

  private GPSDTO gps;
  private List<ReviewDTO> reviews;
  private ScoreDTO scores;

  @Override
  public String toString() {
    return "RestaurantDTO{" +
        "id=" + id +
        ", businessName='" + businessName + '\'' +
        ", businessType='" + businessType + '\'' +
        ", roadAddress='" + roadAddress + '\'' +
        ", locationPhone='" + locationPhone + '\'' +
        ", locationZipcode='" + locationZipcode + '\'' +
        ", gps=" + gps +
        ", reviews=" + reviews +
        ", scores=" + scores +
        '}';
  }
}
