package com.onlinehotel.hotelservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDto {
    private Long bookingId;
    private String bookingStatus;
    private BigDecimal totalPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private HotelRoomDetailsDto hotelRoomDetailsDto;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomDetailsDto {
        private Long roomId;
        private Integer serialNumber;
        private Integer capacity;
        private String roomType;
        private BigDecimal price;
        private Long hotelId;
    }
}
