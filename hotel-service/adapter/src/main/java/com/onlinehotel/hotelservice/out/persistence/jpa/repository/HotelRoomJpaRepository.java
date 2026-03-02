package com.onlinehotel.hotelservice.out.persistence.jpa.repository;

import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HotelRoomJpaRepository extends JpaRepository<HotelRoomJpaEntity,Long> {
    List<HotelRoomJpaEntity> findAllByHotel_Id(Long hotelId);
}
