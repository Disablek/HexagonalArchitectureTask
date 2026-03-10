package com.onlinehotel.hotelservice.adapter.mapper.jpa;

import com.onlinehotel.hotelservice.adapter.out.persistence.jpa.model.PaymentJpaEntity;
import com.onlinehotel.hotelservice.application.port.in.CreatePaymentUseCase;
import com.onlinehotel.hotelservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PaymentJpaMapper{

    Set<Payment> toDomain(Set<PaymentJpaEntity> paymentJpaEntities);

    Payment toDomain(PaymentJpaEntity paymentJpaEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    Payment toDomain(CreatePaymentUseCase.CreatePaymentCommand createPaymentCommand);

    PaymentJpaEntity toJpaEntity(Payment payment);

    PaymentJpaEntity toJpaEntity(CreatePaymentUseCase.CreatePaymentCommand createPaymentRequest);
}
