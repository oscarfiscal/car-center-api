package com.carcenter.car_center_api.maintenance.seeders;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.entities.MaintenanceStatus;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.mechanic.repositories.MechanicRepository;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import com.carcenter.car_center_api.vehicle.repositories.VehicleRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MaintenanceSeeder implements CommandLineRunner {

    private final MaintenanceRepository maintenanceRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final MechanicRepository mechanicRepository;

    public MaintenanceSeeder(MaintenanceRepository maintenanceRepository,
                             ClientRepository clientRepository,
                             VehicleRepository vehicleRepository,
                             MechanicRepository mechanicRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public void run(String... args) {
        if (maintenanceRepository.count() == 0 &&
                clientRepository.count() > 0 &&
                vehicleRepository.count() > 0) {

            Faker faker = new Faker(new Locale("es"));
            Random random = new Random();

            List<Client> clients = clientRepository.findAll();
            List<Vehicle> vehicles = vehicleRepository.findAll();
            List<Mechanic> mechanics = mechanicRepository.findAll();

            List<Maintenance> maintenances = IntStream.range(0, 20)
                    .mapToObj(i -> {
                        Client client = clients.get(random.nextInt(clients.size()));
                        Vehicle vehicle = vehicles.stream()
                                .filter(v -> v.getClient().getId().equals(client.getId()))
                                .findAny()
                                .orElse(null);

                        if (vehicle == null) return null;

                        String description = faker.lorem().sentence(5);

                        BigDecimal limitBudget = BigDecimal.valueOf(
                                Math.round((500000.0 + random.nextDouble() * 4500000.0) / 1000.0) * 1000.0
                        );

                        MaintenanceStatus status = MaintenanceStatus.values()[random.nextInt(MaintenanceStatus.values().length)];
                        int hoursWorked = 1 + random.nextInt(10);
                        LocalDate startDate = LocalDate.now().minusDays(random.nextInt(15));
                        LocalDate endDate = startDate.plusDays(random.nextInt(10));

                        Mechanic mechanic = (random.nextBoolean() && !mechanics.isEmpty())
                                ? mechanics.get(random.nextInt(mechanics.size()))
                                : null;

                        return Maintenance.builder()
                                .description(description)
                                .limitBudget(limitBudget)
                                .status(status)
                                .hoursWorked(hoursWorked)
                                .startDate(startDate)
                                .endDate(endDate)
                                .client(client)
                                .vehicle(vehicle)
                                .mechanic(mechanic)
                                .build();
                    })
                    .filter(m -> m != null)
                    .collect(Collectors.toList());

            maintenanceRepository.saveAll(maintenances);
            System.out.println("✅ 20 maintenances generated successfully.");
        }
    }
}
