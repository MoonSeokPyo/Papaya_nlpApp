package msp.papaya.repository;

import msp.papaya.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
  // 커스텀 쿼리 메서드 작성 가능 (예: 이름으로 검색)
  List<Restaurant> findByBusinessNameContainingIgnoreCase(String keyword);

  // 기본 CRUD 메서드는 JpaRepository에서 제공
  // 추가적인 쿼리를 작성할 수도 있음

  // 특정 이름으로 레스토랑 찾기
  List<Restaurant> findByBusinessName(String businessName);

  // 특정 지역으로 레스토랑 찾기
  List<Restaurant> findByLocationZipcode(String zipcode);
}
