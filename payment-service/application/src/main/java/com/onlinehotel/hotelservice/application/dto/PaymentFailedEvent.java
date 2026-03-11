package com.onlinehotel.hotelservice.application.dto;

public record PaymentFailedEvent (
    Long bookingId
){}
