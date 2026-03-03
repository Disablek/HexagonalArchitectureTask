package com.onlinehotel.bookingservice.adapter.out.persistence.jpa.model;

import com.onlinehotel.bookingservice.model.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@Table(name="Booking")
public class BookingJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @NotNull
    private Long hotelId;

    @NotNull
    private Long hotelRoomId;

    @Embedded
    private DateRangeJpaEntity dateRange;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BookingStatus bookingStatus;

    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        if (bookingStatus == null) {
            bookingStatus = BookingStatus.PENDING;
        }
    }
}
