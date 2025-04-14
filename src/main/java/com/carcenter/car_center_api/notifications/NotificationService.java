package com.carcenter.car_center_api.notifications;

public interface NotificationService {
    void sendSms(String toPhoneNumber, String message);
}
