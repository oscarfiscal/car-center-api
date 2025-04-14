package com.carcenter.car_center_api.notifications.impl;

import com.carcenter.car_center_api.notifications.NotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioNotificationService implements NotificationService {

    @Value("${twilio.from-number}")
    private String fromNumber;

    @Override
    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromNumber),
                message
        ).create();
    }
}
