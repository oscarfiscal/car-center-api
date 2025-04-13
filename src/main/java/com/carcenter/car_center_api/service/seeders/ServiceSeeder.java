package com.carcenter.car_center_api.service.seeders;

import com.carcenter.car_center_api.service.entities.Service;
import com.carcenter.car_center_api.service.repositories.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceSeeder implements CommandLineRunner {

    private final ServiceRepository serviceRepository;

    public ServiceSeeder(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) {
        if (serviceRepository.count() == 0) {
            List<Service> services = List.of(
                    Service.builder().name("Cambio de aceite").price(50000.0).build(),
                    Service.builder().name("Revisión de frenos").price(80000.0).build(),
                    Service.builder().name("Alineación y balanceo").price(65000.0).build(),
                    Service.builder().name("Cambio de bujías").price(40000.0).build(),
                    Service.builder().name("Diagnóstico de motor").price(120000.0).build(),
                    Service.builder().name("Lavado general").price(30000.0).build(),
                    Service.builder().name("Cambio de filtros").price(55000.0).build(),
                    Service.builder().name("Revisión suspensión").price(90000.0).build(),
                    Service.builder().name("Ajuste de frenos").price(75000.0).build(),
                    Service.builder().name("Revisión eléctrica").price(95000.0).build()
            );

            serviceRepository.saveAll(services);
            System.out.println("✅ Servicios insertados correctamente.");
        }
    }
}
