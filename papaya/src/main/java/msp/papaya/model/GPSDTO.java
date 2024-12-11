package msp.papaya.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GPSDTO {

  private Double latitude;
  private Double longitude;

  @Override
  public String toString() {
    return "GPSDTO{" +
        "latitude=" + latitude +
        ", longitude=" + longitude +
        '}';
  }
}
