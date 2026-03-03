package com.onlinehotel.bookingservice.adapter.out.persistence.jpa.repository;

import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.model.BookingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingJpaRepository extends JpaRepository<BookingJpaEntity,Long> {
}
