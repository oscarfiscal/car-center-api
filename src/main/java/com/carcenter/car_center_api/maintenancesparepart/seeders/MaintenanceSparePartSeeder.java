package com.carcenter.car_center_api.maintenancesparepart.seeders;

import com.carcenter.car_center_api.maintenancesparepart.entities.MaintenanceSparePart;
import com.carcenter.car_center_api.maintenancesparepart.repositories.MaintenanceSparePartRepository;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.sparepart.entities.SparePart;
import com.carcenter.car_center_api.sparepart.repositories.SparePartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MaintenanceSparePartSeeder implements CommandLineRunner {

    private final MaintenanceSparePartRepository maintenanceSparePartRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final SparePartRepository sparePartRepository;

    public MaintenanceSparePartSeeder(
            MaintenanceSparePartRepository maintenanceSparePartRepository,
            MaintenanceRepository maintenanceRepository,
            SparePartRepository sparePartRepository
    ) {
        this.maintenanceSparePartRepository = maintenanceSparePartRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public void run(String... args) {
        if (maintenanceSparePartRepository.count() == 0 &&
                maintenanceRepository.count() > 0 &&
                sparePartRepository.count() > 0) {

            List<Maintenance> maintenances = maintenanceRepository.findAll();
            List<SparePart> spareParts = sparePartRepository.findAll();
            Random random = new Random();

            List<MaintenanceSparePart> associations = IntStream.range(0, 20)
                    .mapToObj(i -> {
                        Maintenance maintenance = maintenances.get(random.nextInt(maintenances.size()));
                        SparePart sparePart = spareParts.get(random.nextInt(spareParts.size()));

                        int quantity = 1 + random.nextInt(5); // entre 1 y 5 unidades
                        int estimatedTime = 1 + random.nextInt(3); // entre 1 y 3 horas

                        return MaintenanceSparePart.builder()
                                .maintenance(maintenance)
                                .sparePart(sparePart)
                                .quantity(quantity)
                                .estimatedTime(estimatedTime)
                                .build();
                    })
                    .collect(Collectors.toList());

            maintenanceSparePartRepository.saveAll(associations);
            System.out.println("✅ Repuestos asociados a mantenimientos insertados.");
        }
    }
}