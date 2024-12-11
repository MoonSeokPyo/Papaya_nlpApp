package msp.papaya.repository;

import msp.papaya.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
  // 필요한 경우 추가 쿼리 메서드 정의
  Optional<Score> findByRestaurantId(Integer restaurantId);
}
