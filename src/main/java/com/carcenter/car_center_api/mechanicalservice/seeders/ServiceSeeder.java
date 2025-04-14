package com.carcenter.car_center_api.mechanicalservice.seeders;

import com.carcenter.car_center_api.mechanicalservice.entities.MechanicalService;
import com.carcenter.car_center_api.mechanicalservice.repositories.MechanicalServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceSeeder implements CommandLineRunner {

    private final MechanicalServiceRepository mechanicalServiceRepository;

    public ServiceSeeder(MechanicalServiceRepository mechanicalServiceRepository) {
        this.mechanicalServiceRepository = mechanicalServiceRepository;
    }

    @Override
    public void run(String... args) {
        if (mechanicalServiceRepository.count() == 0) {
            List<MechanicalService> mechanicalServices = List.of(
                    MechanicalService.builder().name("Cambio de aceite").price(50000.0).build(),
                    MechanicalService.builder().name("Revisión de frenos").price(80000.0).build(),
                    MechanicalService.builder().name("Alineación y balanceo").price(65000.0).build(),
                    MechanicalService.builder().name("Cambio de bujías").price(40000.0).build(),
                    MechanicalService.builder().name("Diagnóstico de motor").price(120000.0).build(),
                    MechanicalService.builder().name("Lavado general").price(30000.0).build(),
                    MechanicalService.builder().name("Cambio de filtros").price(55000.0).build(),
                    MechanicalService.builder().name("Revisión suspensión").price(90000.0).build(),
                    MechanicalService.builder().name("Ajuste de frenos").price(75000.0).build(),
                    MechanicalService.builder().name("Revisión eléctrica").price(95000.0).build()
            );

            mechanicalServiceRepository.saveAll(mechanicalServices);
            System.out.println("✅ Servicios insertados correctamente.");
        }
    }
}
