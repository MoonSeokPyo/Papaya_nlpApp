package com.hangw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hangw.model.Restaurant;
import com.hangw.model.RestaurantDTO;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Optional<Restaurant> findByName(String name);

	// DB자체에서 현재 검색된 위치의 위도, 경도를 중심으로 일정 거리 내에 있는 음식점들만 반환
	@Query("SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude, r.category, "
			+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * "
			+ "cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) AS distance) " + "FROM Restaurant r "
			+ "WHERE r.latitude IS NOT NULL AND r.longitude IS NOT NULL AND "
			+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * "
			+ "cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) < :distance " + "ORDER BY r.score DESC")
	List<RestaurantDTO> findRestaurantsInBoundary(@Param("latitude") double latitude,
			@Param("longitude") double longitude, @Param("distance") double distance, Pageable pageable);

	@Query("SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude, r.category,"
			+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) AS distance) " 
			+ "FROM Restaurant r " 
			+ "WHERE r.latitude IS NOT NULL AND r.longitude IS NOT NULL AND "
			+ " r.address LIKE %:address% ")
	List<RestaurantDTO> findRestaurantsByLocation(@Param("address") String address, @Param("latitude") double latitude,
			@Param("longitude") double longitude, Pageable pageable);

	@Query("SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude, r.category,"
			+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) AS distance) " + "FROM Restaurant r " 
			+ "WHERE r.latitude IS NOT NULL AND r.longitude IS NOT NULL AND "
			+ "r.name LIKE %:name% ")
	List<RestaurantDTO> searchRestaurantsByName(@Param("name") String name, @Param("latitude") double latitude,
			@Param("longitude") double longitude, Pageable pageable);

	@Query("SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude, r.category,"
			+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) AS distance) " + "FROM Restaurant r " + "WHERE r.latitude IS NOT NULL AND r.longitude IS NOT NULL "
			+ "ORDER BY r.score DESC")
	List<RestaurantDTO> getBestRestaurants();
}