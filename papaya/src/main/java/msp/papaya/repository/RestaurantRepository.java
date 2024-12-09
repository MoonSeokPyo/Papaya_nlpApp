package msp.papaya.repository;

import msp.papaya.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  // 커스텀 쿼리 메서드 작성 가능 (예: 이름으로 검색)
  List<Restaurant> findByBusinessNameContainingIgnoreCase(String keyword);
}
