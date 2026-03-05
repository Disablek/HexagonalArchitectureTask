package com.onlinehotel.bookingservice.adapter.out.persistence.jpa;

import com.onlinehotel.bookingservice.adapter.mapper.jpa.BookingJpaMapper;
import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.model.BookingJpaEntity;
import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.repository.BookingJpaRepository;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.exception.BookingNotFoundException;
import com.onlinehotel.bookingservice.model.Booking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class JpaBookingAdapter implements BookingRepositoryPort {
    private final BookingJpaRepository bookingJpaRepository;
    private final BookingJpaMapper bookingMapper;

    @Override
    public Booking findById(Long id) throws BookingNotFoundException {
        return bookingMapper.toDomain(
                bookingJpaRepository.findById(id)
                    .orElseThrow(() -> new BookingNotFoundException("Booking not found with id " + id)));
    }

    @Override
    public Set<Booking> findAll() {
        return bookingMapper.toDomain(
                new HashSet<>(bookingJpaRepository.findAll()));
    }

    @Override
    public Booking save(Booking booking) {
        BookingJpaEntity bookingJpaEntity = bookingMapper.toJpaEntity(booking);
        return bookingMapper.toDomain(
                bookingJpaRepository.save(bookingJpaEntity));
    }
}
