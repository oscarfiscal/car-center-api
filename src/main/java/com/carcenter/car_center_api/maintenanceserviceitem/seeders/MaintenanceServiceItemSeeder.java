package com.carcenter.car_center_api.maintenanceserviceitem.seeders;

import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import com.carcenter.car_center_api.mechanicalservice.repositories.MechanicalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Seeder class to generate random MaintenanceServiceItem records for testing and development purposes.
 */
@Component
@RequiredArgsConstructor
public class MaintenanceServiceItemSeeder implements CommandLineRunner {

    private final MaintenanceServiceItemRepository maintenanceServiceItemRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final MechanicalServiceRepository mechanicalServiceRepository;

    @Override
    public void run(String... args) {
        if (shouldSeed()) {
            List<Maintenance> maintenances = maintenanceRepository.findAll();
            List<MechanicalService> mechanicalServices = mechanicalServiceRepository.findAll();
            Random random = new Random();

            List<MaintenanceServiceItem> items = IntStream.range(0, 20)
                    .mapToObj(i -> createRandomItem(maintenances, mechanicalServices, random))
                    .collect(Collectors.toList());

            maintenanceServiceItemRepository.saveAll(items);
            System.out.println("✅ Servicios asociados a mantenimientos insertados.");
        }
    }

    private boolean shouldSeed() {
        return maintenanceServiceItemRepository.count() == 0 &&
                maintenanceRepository.count() > 0 &&
                mechanicalServiceRepository.count() > 0;
    }

    private MaintenanceServiceItem createRandomItem(List<Maintenance> maintenances, List<MechanicalService> mechanicalServices, Random random) {
        Maintenance maintenance = maintenances.get(random.nextInt(maintenances.size()));
        MechanicalService mechanicalService = mechanicalServices.get(random.nextInt(mechanicalServices.size()));

        int estimatedTime = 1 + random.nextInt(5); // between 1 and 5 hours

        return MaintenanceServiceItem.builder()
                .maintenance(maintenance)
                .mechanicalService(mechanicalService)
                .estimatedTime(estimatedTime)
                .build();
    }
}
