package com.onlinehotel.bookingservice.adapter.out.grpc;

import com.onlinehotel.bookingservice.adapter.mapper.grpc.HotelRoomGrpcMapper;
import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;

import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomRequest;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import io.grpc.ManagedChannel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HotelRoomGrpcClient implements HotelRoomServicePort {

    private HotelRoomServiceGrpc.HotelRoomServiceBlockingStub blockingStub;
    private final HotelRoomGrpcMapper hotelRoomGrpcMapper;

    @Autowired
    ManagedChannel managedChannel;

    @PostConstruct
    private void initializeClient() {

        blockingStub = HotelRoomServiceGrpc.newBlockingStub(managedChannel);

    }

    public HotelRoomGrpcClient(
            HotelRoomServiceGrpc.HotelRoomServiceBlockingStub blockingStub,
            HotelRoomGrpcMapper hotelRoomGrpcMapper) {
        this.blockingStub = blockingStub;
        this.hotelRoomGrpcMapper = hotelRoomGrpcMapper;
    }

    @Override
    public HotelRoomDetailsDto getHotelDetails(Long hotelId, Long roomId) {
        HotelRoomRequest request = HotelRoomRequest.newBuilder()
                .setHotelId(hotelId)
                .setRoomId(roomId)
                .build();
        return hotelRoomGrpcMapper.toDto(
                blockingStub.getHotelRoomDetails(request));
    }

    @Override
    public boolean checkAvailability(Long hotelId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return false;
    }

    @Override
    public void reserveRoom(Long roomId) {
        HotelRoomRequest request = HotelRoomRequest.newBuilder()
                .setRoomId(roomId)
                .build();
        blockingStub.reserveRoom(request);
    }

    @Override
    public void cancelReservation(Long roomId) {
        HotelRoomRequest request = HotelRoomRequest.newBuilder()
                .setRoomId(roomId)
                .build();
        blockingStub.cancelReservation(request);
    }
}

package com.onlinehotel.bookingservice.application.port.out.grpc;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;

import java.time.LocalDate;

public interface HotelRoomServicePort {
    boolean checkAvailability(
            Long hotelId,
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    );

    HotelRoomDetailsDto getHotelDetails(Long hotelId, Long roomId);

    void reserveRoom(Long roomId);

    void cancelReservation(Long roomId);
}
package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BookingSagaOrchestrator {
    private final HotelRoomServicePort hotelRoomServicePort;
    private final BookingRepositoryPort bookingRepositoryPort;
    private final NotificationPort notificationPort;

    public Booking executeSaga(Booking booking) {
        try {
            // Step 1: Reserve room
            hotelRoomServicePort.reserveRoom(booking.getHotelRoomId());
            log.info("Room {} reserved", booking.getHotelRoomId());

            // Step 2: Create booking
            Booking savedBooking = bookingRepositoryPort.save(booking);
            log.info("Booking {} created", savedBooking.getId());

            // Step 3: Process payment
            BookingCreatedEvent event = BookingCreatedEvent.from(savedBooking);
            notificationPort.sendBookingCreated(event);
            log.info("Payment event sent for booking {}", savedBooking.getId());

            return savedBooking;
        } catch (Exception e) {
            log.error("Saga failed, compensating", e);
            compensate(booking);
            throw e;
        }
    }

    private void compensate(Booking booking) {
        try {
            // Cancel room reservation
            hotelRoomServicePort.cancelReservation(booking.getHotelRoomId());
            log.info("Room {} reservation cancelled", booking.getHotelRoomId());

            // If booking was created, delete it
            if (booking.getId() != null) {
                bookingRepositoryPort.deleteById(booking.getId());
                log.info("Booking {} deleted", booking.getId());
            }
        } catch (Exception e) {
            log.error("Compensation failed", e);
        }
    }
}
package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.CancelBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.BookingStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelBookingService implements CancelBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final HotelRoomServicePort hotelRoomServicePort;

    public CancelBookingService(BookingRepositoryPort bookingRepositoryPort, HotelRoomServicePort hotelRoomServicePort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.hotelRoomServicePort = hotelRoomServicePort;
    }

    @Override
    public void execute(Long bookingId) {
        Booking booking = bookingRepositoryPort.findById(bookingId);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepositoryPort.save(booking);

        // Compensate: cancel room reservation
        hotelRoomServicePort.cancelReservation(booking.getHotelRoomId());
    }
}
package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CreateBookingService implements CreateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final NotificationPort notificationPort;
    private final HotelRoomServicePort hotelRoomServicePort;
    private final BookingSagaOrchestrator sagaOrchestrator;

    public CreateBookingService(BookingRepositoryPort bookingRepositoryPort, NotificationPort notificationPort, HotelRoomServicePort hotelRoomServicePort, BookingSagaOrchestrator sagaOrchestrator) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.notificationPort = notificationPort;
        this.hotelRoomServicePort = hotelRoomServicePort;
        this.sagaOrchestrator = sagaOrchestrator;
    }

    @Override
    public Booking execute(Booking booking) {

        HotelRoomDetailsDto hotelRoomDetailsDto = hotelRoomServicePort.getHotelDetails(
                booking.getHotelId(), booking.getHotelRoomId()
        );

        BigDecimal totalPrice = hotelRoomDetailsDto.price()
                .multiply(BigDecimal.valueOf(booking.getDateRange().nights()));

        booking.setTotalPrice(totalPrice);

        return sagaOrchestrator.executeSaga(booking);
    }
}
package com.onlinehotel.hotelservice.in.grpc;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomDetails;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomRequest;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import com.onlinehotel.hotelservice.adapter.in.grpc.RoomType;
import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.CancelReservationUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetHotelRoomDetailsUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.ReserveHotelRoomUseCase;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;


@GrpcService
@AllArgsConstructor
public class HotelRoomGrpcService extends HotelRoomServiceGrpc.HotelRoomServiceImplBase {
    private final GetHotelRoomDetailsUseCase getHotelRoomDetailsUseCase;
    private final ReserveHotelRoomUseCase reserveHotelRoomUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;

    @Override
    public void getHotelRoomDetails(HotelRoomRequest request, StreamObserver<HotelRoomDetails> response){
        HotelRoomDetailsDto details = getHotelRoomDetailsUseCase.details(request.getRoomId());

        RoomType protoRoomType = RoomType.valueOf(details.roomType().name());

        HotelRoomDetails roomDetails = HotelRoomDetails.newBuilder()
                .setRoomType(protoRoomType)
                .setCapacity(details.capacity())
                .setSerialNumber(details.serialNumber())
                .setPrice(details.price().toString())
                .build();

        response.onNext(roomDetails);
        response.onCompleted();
    }

    @Override
    public void reserveRoom(HotelRoomRequest request, StreamObserver<HotelRoomDetails> response) {
        reserveHotelRoomUseCase.execute(request.getRoomId());
        HotelRoomDetailsDto details = getHotelRoomDetailsUseCase.details(request.getRoomId());

        RoomType protoRoomType = RoomType.valueOf(details.roomType().name());

        HotelRoomDetails roomDetails = HotelRoomDetails.newBuilder()
                .setRoomType(protoRoomType)
                .setCapacity(details.capacity())
                .setSerialNumber(details.serialNumber())
                .setPrice(details.price().toString())
                .build();

        response.onNext(roomDetails);
        response.onCompleted();
    }

    @Override
    public void cancelReservation(HotelRoomRequest request, StreamObserver<HotelRoomDetails> response) {
        cancelReservationUseCase.execute(request.getRoomId());
        HotelRoomDetailsDto details = getHotelRoomDetailsUseCase.details(request.getRoomId());

        RoomType protoRoomType = RoomType.valueOf(details.roomType().name());

        HotelRoomDetails roomDetails = HotelRoomDetails.newBuilder()
                .setRoomType(protoRoomType)
                .setCapacity(details.capacity())
                .setSerialNumber(details.serialNumber())
                .setPrice(details.price().toString())
                .build();

        response.onNext(roomDetails);
        response.onCompleted();
    }
}

package com.onlinehotel.hotelservice.out.persistence.jpa;

import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.out.persistence.jpa.mapper.HotelRoomMapper;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import com.onlinehotel.hotelservice.out.persistence.jpa.repository.HotelRoomJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class JpaHotelRoomAdapter implements HotelRoomRepositoryPort {
    private final HotelRoomJpaRepository hotelRoomJpaRepository;
    private final HotelRoomMapper hotelRoomMapper;

    @Override
    public HotelRoom findById(Long id) throws HotelRoomNotFoundException {
        HotelRoomJpaEntity entity = hotelRoomJpaRepository.findById(id)
                .orElseThrow(() -> new HotelRoomNotFoundException("Hotel room not found"));
        return hotelRoomMapper.toDomain(entity);
    }

    @Override
    public HotelRoom save(HotelRoom hotelRoom) {
        HotelRoomJpaEntity entity = hotelRoomMapper.toJpaEntity(hotelRoom);
        if (entity.getId() != null) {
            HotelRoomJpaEntity existing = hotelRoomJpaRepository.findById(entity.getId()).orElseThrow();
            hotelRoomMapper.updateEntityFromDomain(existing, hotelRoom);
            entity = existing;
        }
        HotelRoomJpaEntity saved = hotelRoomJpaRepository.save(entity);
        return hotelRoomMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        hotelRoomJpaRepository.deleteById(id);
    }

    @Override
    public Set<HotelRoom> findAllByHotelId(Long hotelId) {
        return new HashSet<>(hotelRoomMapper.roomsToDomain(new HashSet<>(hotelRoomJpaRepository.findAllByHotel_Id(hotelId))));
    }

    @Override
    public Set<HotelRoom> findAll() {
        return new HashSet<>(hotelRoomMapper.roomsToDomain(new HashSet<>(hotelRoomJpaRepository.findAll())));
    }
}
package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.in.web.HotelRoomController;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface HotelRoomMapper {
    @Named("toJpaRooms")
    @IterableMapping(qualifiedByName = "toJpaRoom")
    Set<HotelRoomJpaEntity> roomsToJpa(Set<HotelRoom> rooms);

    @Named("toDomainRooms")
    @IterableMapping(qualifiedByName = "toDomainRoom")
    Set<HotelRoom> roomsToDomain(Set<HotelRoomJpaEntity> rooms);

    @Named("toJpaRoom")
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isReserved", source = "isReserved")
    HotelRoomJpaEntity toJpaEntity(HotelRoom domain);

    @Named("toDomainRoom")
    @Mapping(target = "hotelId", expression = "java(jpa.getHotel().getId())")
    @Mapping(target = "isReserved", source = "isReserved")
    HotelRoom toDomain(HotelRoomJpaEntity jpa);

    @Named("toDomainRequest")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    HotelRoom toDomain(HotelRoomController.CreateHotelRoomRequest createHotelRoomRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "capacity", source = "capacity")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "roomType", source = "roomType")
    void updateEntityFromDomain(@MappingTarget HotelRoomJpaEntity entity, HotelRoom domain);
}

package com.onlinehotel.hotelservice.out.persistence.jpa.model;

import com.onlinehotel.hotelservice.model.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "hotel_room")
public class HotelRoomJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private Integer capacity;

    @Positive
    private Integer serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelJpaEntity hotel;

    @Positive
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(columnDefinition = "boolean default false")
    private boolean isReserved;
}
package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.model.HotelRoom;

public interface CancelReservationUseCase {
    HotelRoom execute(Long roomId);
}
package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.model.HotelRoom;

public interface ReserveHotelRoomUseCase {
    HotelRoom execute(Long roomId);
}
package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.CancelReservationUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.model.HotelRoom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class CancelReservationService implements CancelReservationUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    @Override
    public HotelRoom execute(Long roomId) {
        HotelRoom hotelRoom = hotelRoomRepositoryPort.findById(roomId);
        hotelRoom.setReserved(false);
        return hotelRoomRepositoryPort.save(hotelRoom);
    }
}
package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.ReserveHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.model.HotelRoom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ReserveHotelRoomService implements ReserveHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    @Override
    public HotelRoom execute(Long roomId) {
        HotelRoom hotelRoom = hotelRoomRepositoryPort.findById(roomId);
        if (hotelRoom.isReserved()) {
            throw new IllegalStateException("Room is already reserved");
        }
        hotelRoom.setReserved(true);
        return hotelRoomRepositoryPort.save(hotelRoom);
    }
}
package com.onlinehotel.hotelservice.model;

import java.math.BigDecimal;
import java.util.Objects;

public class HotelRoom {
    private Long id;
    private Integer serialNumber;
    private Integer capacity;
    private Long hotelId;
    private BigDecimal price;
    private RoomType roomType;
    private boolean isReserved;

    public HotelRoom(
            Long id,
            Integer serialNumber,
            Integer capacity,
            Long hotelId,
            BigDecimal price,
            RoomType roomType,
            boolean isReserved
    ) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.capacity = capacity;
        this.hotelId = hotelId;
        this.price = price;
        this.roomType = roomType;
        this.isReserved = isReserved;
    }
    public HotelRoom() {

    }


    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "HotelRoom{" +
                "id=" + id +
                "serialNumber=" + serialNumber.toString() +
                "capacity=" + capacity.toString() +
                "hotelId=" + hotelId.toString() +
                "price=" + price.toString() +
                "roomType=" + roomType.toString() +
                "}";

    }

    public Long getId() {
        return id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (HotelRoom) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.serialNumber, that.serialNumber) &&
                Objects.equals(this.capacity, that.capacity) &&
                Objects.equals(this.hotelId, that.hotelId) &&
                Objects.equals(this.price, that.price) &&
                Objects.equals(this.roomType, that.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, capacity, hotelId, price, roomType);
    }

}
