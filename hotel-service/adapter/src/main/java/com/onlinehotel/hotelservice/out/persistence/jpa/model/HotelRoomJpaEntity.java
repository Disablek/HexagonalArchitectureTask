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
}
