package com.onlinehotel.hotelservice.out.persistence.jpa;

import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.out.persistence.jpa.mapper.HotelMapper;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelJpaEntity;
import com.onlinehotel.hotelservice.out.persistence.jpa.repository.HotelJpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class HotelPersistenceAdapter implements HotelRepositoryPort {
    private final HotelJpaRepository hotelJpaRepository;
    private final HotelMapper hotelMapper;

    public HotelPersistenceAdapter(HotelJpaRepository hotelJpaRepository, HotelMapper hotelMapper) {
        this.hotelJpaRepository = hotelJpaRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public Optional<Hotel> findById(Long id) throws HotelNotFoundException {
        return Optional.of(hotelMapper.toDomain(hotelJpaRepository.findById(id)
                .orElseThrow(
                        () -> new HotelNotFoundException("Hotel not found with id " + id))));
    }

    @Override
    public Hotel save(Hotel hotel) {

        HotelJpaEntity entity = hotelMapper.toJpaEntity(hotel);
        HotelJpaEntity savedEntity = hotelJpaRepository.save(entity);

        return hotelMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        hotelMapper.toDomain(hotelJpaRepository.findById(id)
            .orElseThrow(
                    () -> new HotelNotFoundException("Hotel not found with id " + id)
            ));
        hotelJpaRepository.deleteById(id);
    }

    @Override
    public Set<Hotel> findAll() {
        return hotelMapper.toDomainSet(
                new HashSet<>(hotelJpaRepository.findAll())
        );
    }
}
