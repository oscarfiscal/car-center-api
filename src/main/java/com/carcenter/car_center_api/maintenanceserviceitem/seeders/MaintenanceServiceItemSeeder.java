package com.carcenter.car_center_api.maintenanceserviceitem.seeders;

import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import com.carcenter.car_center_api.mechanicalservice.repositories.MechanicalServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MaintenanceServiceItemSeeder implements CommandLineRunner {

    private final MaintenanceServiceItemRepository maintenanceServiceItemRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final MechanicalServiceRepository mechanicalServiceRepository;

    public MaintenanceServiceItemSeeder(
            MaintenanceServiceItemRepository maintenanceServiceItemRepository,
            MaintenanceRepository maintenanceRepository,
            MechanicalServiceRepository mechanicalServiceRepository) {
        this.maintenanceServiceItemRepository = maintenanceServiceItemRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.mechanicalServiceRepository = mechanicalServiceRepository;
    }

    @Override
    public void run(String... args) {
        if (maintenanceServiceItemRepository.count() == 0 &&
                maintenanceRepository.count() > 0 &&
                mechanicalServiceRepository.count() > 0) {

            List<Maintenance> maintenances = maintenanceRepository.findAll();
            List<MechanicalService> mechanicalServices = mechanicalServiceRepository.findAll();
            Random random = new Random();

            List<MaintenanceServiceItem> items = IntStream.range(0, 20)
                    .mapToObj(i -> {
                        Maintenance maintenance = maintenances.get(random.nextInt(maintenances.size()));
                        MechanicalService mechanicalService = mechanicalServices.get(random.nextInt(mechanicalServices.size()));

                        int estimatedTime = 1 + random.nextInt(5); // entre 1 y 5 horas

                        return MaintenanceServiceItem.builder()
                                .maintenance(maintenance)
                                .mechanicalService(mechanicalService)
                                .estimatedTime(estimatedTime)
                                .build();
                    })
                    .collect(Collectors.toList());

            maintenanceServiceItemRepository.saveAll(items);
            System.out.println("✅ Servicios asociados a mantenimientos insertados.");
        }
    }
}