package com.carcenter.car_center_api.maintenance.services;

import java.math.BigDecimal;

public interface MaintenanceCostServiceInterface {

    BigDecimal calculateTotal(Long maintenanceId);
    //String formatCOP(BigDecimal amount);
}
