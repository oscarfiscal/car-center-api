package com.carcenter.car_center_api.photo.seeders;

import com.carcenter.car_center_api.maintenance.entities.Maintenance;
import com.carcenter.car_center_api.maintenance.repositories.MaintenanceRepository;
import com.carcenter.car_center_api.photo.entities.Photo;
import com.carcenter.car_center_api.photo.repositories.PhotoRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PhotoSeeder implements CommandLineRunner {

    private final PhotoRepository photoRepository;
    private final MaintenanceRepository maintenanceRepository;

    public PhotoSeeder(PhotoRepository photoRepository, MaintenanceRepository maintenanceRepository) {
        this.photoRepository = photoRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public void run(String... args) {
        if (photoRepository.count() == 0 && maintenanceRepository.count() > 0) {
            Faker faker = new Faker(new Locale("es"));
            Random random = new Random();

            List<Maintenance> maintenances = maintenanceRepository.findAll();

            List<Photo> photos = IntStream.range(0, 15)
                    .mapToObj(i -> {
                        Maintenance maintenance = maintenances.get(random.nextInt(maintenances.size()));
                        String path = "uploads/photos/" + faker.file().fileName(null, null, "jpg", null);

                        return Photo.builder()
                                .path(path.substring(0, Math.min(path.length(), 200))) // prevenir overflow
                                .maintenance(maintenance)
                                .build();
                    })
                    .collect(Collectors.toList());

            photoRepository.saveAll(photos);
            System.out.println("✅ Fotos asociadas a mantenimientos insertadas.");
        }
    }
}
