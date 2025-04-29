package com.carcenter.car_center_api.notifications.impl;

import com.carcenter.car_center_api.config.TwilioProperties;
import com.carcenter.car_center_api.notifications.NotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TwilioNotificationService implements NotificationService {

    private final TwilioProperties twilioProperties;

    @Override
    public void sendSms(String toPhoneNumber, String message) {
        try {
            Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioProperties.getPhoneNumber()),
                    message
            ).create();
            log.info("SMS sent successfully to {}", toPhoneNumber);
        } catch (Exception ex) {
            log.error("Failed to send SMS to {}: {}", toPhoneNumber, ex.getMessage());
        }
    }
}

