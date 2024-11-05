package com.hangw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hangw.model.Restaurant;
import com.hangw.model.RestaurantDTO;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Optional<Restaurant> findByName(String name);

	// DB자체에서 현재 검색된 위치의 위도, 경도를 중심으로 일정 거리 내에 있는 음식점들만 반환
	@Query(value = "SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude,"
			+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) AS distance) " 
			+ "FROM Restaurant r "
			+ "WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * "
			+ "cos(radians(r.longitude) - radians(:longitude)) + sin(radians(:latitude)) * "
			+ "sin(radians(r.latitude)))) < :distance ")
	List<RestaurantDTO> findRestaurantsInBoundary(@Param("latitude") double latitude,@Param("longitude") double longitude, @Param("distance") double distance);

	@Query("SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude)"
			+ "FROM Restaurant r " 
			+ "WHERE r.address LIKE %:address% ")
	List<RestaurantDTO> findRestaurantsByLocation(@Param("address") String address);
	
	@Query("SELECT new com.hangw.model.RestaurantDTO(r.id, r.name, r.address, r.score, r.latitude, r.longitude)"
			+ "FROM Restaurant r " 
			+ "WHERE r.name LIKE %:name% ")
	List<RestaurantDTO> searchRestaurantsByName(@Param("name") String name);
	
}
