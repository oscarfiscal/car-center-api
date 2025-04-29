package com.carcenter.car_center_api.maintenance.services.impl;

import com.carcenter.car_center_api.maintenance.services.MaintenanceCostServiceInterface;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MaintenanceCostServiceImpl implements MaintenanceCostServiceInterface {

    private final MaintenanceSparePartRepository mspRepo;
    private final MaintenanceServiceItemRepository msiRepo;



    @Override
    public BigDecimal calculateTotal(Long maintenanceId) {
        BigDecimal sparePartsTotal = mspRepo.findByMaintenanceId(maintenanceId).stream()
                .map(sp -> BigDecimal.valueOf(sp.getSparePart().getUnitPrice())
                        .multiply(BigDecimal.valueOf(sp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal serviceItemsTotal = msiRepo.findByMaintenanceId(maintenanceId).stream()
                .map(si -> BigDecimal.valueOf(si.getMechanicalService().getPrice())
                        .multiply(BigDecimal.valueOf(si.getEstimatedTime())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sparePartsTotal.add(serviceItemsTotal);
    }

}
