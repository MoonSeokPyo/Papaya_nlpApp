package msp.papaya.repository;

import msp.papaya.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  // 필요한 경우 추가 쿼리 메서드 정의
}
