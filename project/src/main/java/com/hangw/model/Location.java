package com.hangw.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {					//user의 현재위치를 저장할때 사용
	private String address;
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
    	this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location(String address, double latitude, double longitude) {
    	this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
