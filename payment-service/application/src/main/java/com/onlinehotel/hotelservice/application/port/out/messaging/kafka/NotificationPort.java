package com.onlinehotel.hotelservice.application.port.out.messaging.kafka;

import com.onlinehotel.hotelservice.application.dto.PaymentFailedEvent;
import com.onlinehotel.hotelservice.application.dto.PaymentProceedEvent;

public interface NotificationPort {
    void sendPaymentProceed(PaymentProceedEvent event);

    void sendPaymentCreationFailed(PaymentFailedEvent event);

    void sendPaymentCancelled(PaymentFailedEvent event);
}
