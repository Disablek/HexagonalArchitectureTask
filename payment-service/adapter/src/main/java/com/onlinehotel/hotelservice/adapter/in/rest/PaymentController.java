package com.onlinehotel.hotelservice.adapter.in.rest;

import com.onlinehotel.hotelservice.adapter.mapper.jpa.PaymentJpaMapper;
import com.onlinehotel.hotelservice.application.dto.PaymentFailedEvent;
import com.onlinehotel.hotelservice.application.port.in.*;
import com.onlinehotel.hotelservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.hotelservice.model.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
@Tag(name = "Payments", description = "Payment management operations")
public class PaymentController {

    private final CancelPaymentUseCase cancelPaymentUseCase;
    private final CreatePaymentUseCase createPaymentUseCase;
    private final FindAllPaymentsUseCase findAllPaymentsUseCase;
    private final FindPaymentByIdUseCase findPaymentByIdUseCase;
    private final PaymentInfoUseCase paymentInfoUseCase;
    private final ProceedPaymentUseCase proceedPaymentUseCase;
    private final PaymentJpaMapper paymentJpaMapper;
    private final NotificationPort notificationPort;

    @DeleteMapping("/{paymentId}")
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Void> cancelPayment(@PathVariable("paymentId") Long paymentId) {
        cancelPaymentUseCase.cancelPayment(paymentId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{paymentId}/fail")
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Void> failPayment(@PathVariable("paymentId") Long paymentId) {
        PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent(paymentId);
        notificationPort.sendPaymentCreationFailed(paymentFailedEvent);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Payment> createPayment(@RequestBody CreatePaymentUseCase.CreatePaymentCommand payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                        createPaymentUseCase.execute(paymentJpaMapper.toDomain(payment))
        );
    }

    @GetMapping
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Set<Payment>> getAllPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(
                findAllPaymentsUseCase.execute()
        );
    }

    @GetMapping("/{paymentId}")
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Payment> findPaymentById(@PathVariable("paymentId") Long paymentId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                findPaymentByIdUseCase.execute(paymentId)
        );
    }

    @GetMapping("/{paymentId}/info")
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<PaymentInfoUseCase.PaymentInfo> paymentInfo(@PathVariable("paymentId") Long paymentId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                paymentInfoUseCase.execute(paymentId)
        );
    }

    @PatchMapping("/{paymentId}/proceed")
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Void> proceedPayment(@PathVariable("paymentId") Long paymentId) {
        proceedPaymentUseCase.execute(paymentId);
        return ResponseEntity.ok().build();
    }
}
