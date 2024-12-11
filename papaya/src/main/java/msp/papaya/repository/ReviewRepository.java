package msp.papaya.repository;

import msp.papaya.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
  // 필요한 경우 추가 쿼리 메서드 정의
  List<Review> findByRestaurantId(Integer restaurantId);
}
