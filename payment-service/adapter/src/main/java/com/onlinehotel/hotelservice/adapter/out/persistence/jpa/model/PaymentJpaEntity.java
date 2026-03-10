package com.onlinehotel.hotelservice.adapter.out.persistence.jpa.model;

import com.onlinehotel.hotelservice.model.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Payment")
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long bookingId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.CREATED;

    @NotNull
    private LocalDateTime paymentDate = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    public void updateTimestamps() {
        if (paymentStatus == PaymentStatus.PROCEED) {
            this.paymentDate = LocalDateTime.now();
        }
    }
}

