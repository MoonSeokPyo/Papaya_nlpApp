package com.hangw.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
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
