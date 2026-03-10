package com.onlinehotel.hotelservice.adapter.mapper.kafka;

import com.onlinehotel.hotelservice.application.dto.CreatePaymentDto;
import com.onlinehotel.hotelservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentKafkaMapper {

    @Mapping(target = "bookingId", source = "bookingId")
    Payment toDomain(CreatePaymentDto dto);

    CreatePaymentDto toDto(Payment payment);
}


