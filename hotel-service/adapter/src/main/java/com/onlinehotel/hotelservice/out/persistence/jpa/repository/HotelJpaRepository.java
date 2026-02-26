package com.onlinehotel.hotelservice.out.persistence.jpa.repository;

import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelJpaRepository extends JpaRepository<HotelJpaEntity, Long> {
    Optional<HotelJpaEntity> getHotelJpaEntityById(Long id);
}
