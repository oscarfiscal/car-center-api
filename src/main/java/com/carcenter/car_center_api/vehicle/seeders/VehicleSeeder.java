package com.carcenter.car_center_api.vehicle.seeders;

import com.carcenter.car_center_api.brand.entities.Brand;
import com.carcenter.car_center_api.brand.repositories.BrandRepository;
import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.carcenter.car_center_api.vehicle.entities.Vehicle;
import com.carcenter.car_center_api.vehicle.repositories.VehicleRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class VehicleSeeder implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;
    private final BrandRepository brandRepository;

    public VehicleSeeder(VehicleRepository vehicleRepository, ClientRepository clientRepository, BrandRepository brandRepository) {
        this.vehicleRepository = vehicleRepository;
        this.clientRepository = clientRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public void run(String... args) {
        if (vehicleRepository.count() == 0 && clientRepository.count() > 0 && brandRepository.count() > 0) {
            Faker faker = new Faker(new Locale("es"));
            Random random = new Random();

            List<Client> clients = clientRepository.findAll();
            List<Brand> brands = brandRepository.findAll();

            List<Vehicle> vehicles = clients.stream().map(client -> {
                String plate = faker.bothify("???###").toUpperCase();
                String model = safeSubstring(faker.bothify("Model-???"), 50);
                int year = random.nextInt(20) + 2005;
                String color = safeSubstring(faker.color().name(), 20);
                Brand brand = brands.get(random.nextInt(brands.size())); // Elige una marca aleatoria

                return Vehicle.builder()
                        .plate(plate)
                        .brand(brand)
                        .model(model)
                        .year(year)
                        .color(color)
                        .client(client)
                        .build();
            }).collect(Collectors.toList());

            vehicleRepository.saveAll(vehicles);
            System.out.println("✅ Vehículos generados con marcas asociadas.");
        }
    }

    private String safeSubstring(String input, int maxLength) {
        if (input == null) return "";
        return input.length() > maxLength ? input.substring(0, maxLength) : input;
    }
}

