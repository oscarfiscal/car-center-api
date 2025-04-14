package com.carcenter.car_center_api.brand.seeders;

import com.carcenter.car_center_api.brand.entities.Brand;
import com.carcenter.car_center_api.brand.repositories.BrandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeder que carga marcas iniciales en la base de datos si no existen.
 */
@Component
public class BrandSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;

    public BrandSeeder(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void run(String... args) {
        if (brandRepository.count() == 0) {
            brandRepository.saveAll(List.of(
                    Brand.builder().name("Toyota").build(),
                    Brand.builder().name("Chevrolet").build(),
                    Brand.builder().name("Mazda").build(),
                    Brand.builder().name("Renault").build()
            ));
            System.out.println("✅ Marcas insertadas con éxito.");
        }
    }
}