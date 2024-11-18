package com.hangw.model;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Component
@Table(name="restaurant_table")
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	@Column(name = "business_name")
	private String name;
	
	@Column(name = "location_address")
	private String address;
	
	@Column(name = "road_address")
	private String roadAdress;
	
	@Column(nullable = true)
	private double latitude;
	
	@Column(nullable = true)
    private double longitude;
    
	@Column(name = "business_type")
	private String category;
	
	@ColumnDefault("0")
    private double score;
    
	@Column(name = "location_phone")
	private String phone;
	
	private String locationZipcode;
	
	private String roadZipcode;
	
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Review> Reviews;
}