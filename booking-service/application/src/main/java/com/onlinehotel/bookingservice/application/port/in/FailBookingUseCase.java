package com.onlinehotel.bookingservice.application.port.in;

import reactor.core.publisher.Mono;

public interface FailBookingUseCase {
    Mono<Void> execute(Long bookingId);
}
