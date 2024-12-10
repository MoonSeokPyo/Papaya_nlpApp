package msp.papaya.repository;

import msp.papaya.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
  // 필요한 경우 추가 쿼리 메서드 정의
}
