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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Component
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	private String name;
	
	private String address;
	
	private String roadAdress;
	
	@Column(nullable = true)
	private double latitude;
	
	@Column(nullable = true)
    private double longitude;
    
	private String category;
	
	@ColumnDefault("0")
    private double score;
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Review> Reviews;
}
