package com.onlinehotel.hotelservice.adapter.in.messaging.kafka.consumer;


import com.onlinehotel.hotelservice.adapter.mapper.kafka.PaymentKafkaMapper;
import com.onlinehotel.hotelservice.application.dto.CreatePaymentDto;
import com.onlinehotel.hotelservice.application.port.in.CreatePaymentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentEventConsumer {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final PaymentKafkaMapper paymentKafkaMapper;

    @KafkaListener(topics = "booking-created")
    public void handlePayment(CreatePaymentDto dto) {
        createPaymentUseCase.execute(paymentKafkaMapper
                .toDomain(dto));
    }
}
