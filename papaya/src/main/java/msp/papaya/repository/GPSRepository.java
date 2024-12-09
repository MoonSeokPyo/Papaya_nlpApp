package msp.papaya.repository;

import msp.papaya.model.GPS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GPSRepository extends JpaRepository<GPS, Long> {
}