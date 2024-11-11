package com.hangw.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {
	private long id;
	private String name;
	private String address;
	private String category;
	private double score;
	private double distance;
	private double lat;
	private double lng;
	
	public RestaurantDTO(long id, String name, String address, double score, double lat, double lng, String category, double distance) {
		this.id = id;
        this.name = name;
        this.address = address;
        this.score = score;
        this.lat = lat;
        this.lng = lng;
        this.distance = roundToOneDecimalPlace(distance);
        this.category = category;
    }
	
	private double roundToOneDecimalPlace(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
