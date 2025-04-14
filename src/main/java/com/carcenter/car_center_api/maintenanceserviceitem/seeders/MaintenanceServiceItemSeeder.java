package com.carcenter.car_center_api.maintenanceserviceitem.seeders;

import com.carcenter.car_center_api.maintenanceserviceitem.entities.MaintenanceServiceItem;
import com.carcenter.car_center_api.maintenanceserviceitem.repositories.MaintenanceServiceItemRepository;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.service.entities.Service;
import com.carcenter.car_center_api.service.repositories.ServiceRepository;
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
    private final ServiceRepository serviceRepository;

    public MaintenanceServiceItemSeeder(
            MaintenanceServiceItemRepository maintenanceServiceItemRepository,
            MaintenanceRepository maintenanceRepository,
            ServiceRepository serviceRepository) {
        this.maintenanceServiceItemRepository = maintenanceServiceItemRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) {
        if (maintenanceServiceItemRepository.count() == 0 &&
                maintenanceRepository.count() > 0 &&
                serviceRepository.count() > 0) {

            List<Maintenance> maintenances = maintenanceRepository.findAll();
            List<Service> services = serviceRepository.findAll();
            Random random = new Random();

            List<MaintenanceServiceItem> items = IntStream.range(0, 20)
                    .mapToObj(i -> {
                        Maintenance maintenance = maintenances.get(random.nextInt(maintenances.size()));
                        Service service = services.get(random.nextInt(services.size()));

                        int estimatedTime = 1 + random.nextInt(5); // entre 1 y 5 horas

                        return MaintenanceServiceItem.builder()
                                .maintenance(maintenance)
                                .service(service)
                                .estimatedTime(estimatedTime)
                                .build();
                    })
                    .collect(Collectors.toList());

            maintenanceServiceItemRepository.saveAll(items);
            System.out.println("✅ Servicios asociados a mantenimientos insertados.");
        }
    }
}