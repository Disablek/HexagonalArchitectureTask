package com.onlinehotel.hotelservice.out.persistence.jpa.repository;

import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelJpaRepository extends JpaRepository<HotelJpaEntity, Long> {
    @Query("SELECT h FROM HotelJpaEntity h LEFT JOIN FETCH h.rooms")
    List<HotelJpaEntity> findAllWithRooms();
}
