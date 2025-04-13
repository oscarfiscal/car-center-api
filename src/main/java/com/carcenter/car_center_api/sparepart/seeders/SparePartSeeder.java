package com.carcenter.car_center_api.sparepart.seeders;

import com.carcenter.car_center_api.sparepart.entities.SparePart;
import com.carcenter.car_center_api.sparepart.repositories.SparePartRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SparePartSeeder implements CommandLineRunner {

    private final SparePartRepository sparePartRepository;

    public SparePartSeeder(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public void run(String... args) {
        if (sparePartRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));
            Random random = new Random();

            List<String> partNames = List.of(
                    "Filtro de aceite", "Bujía", "Batería", "Pastillas de freno", "Filtro de aire",
                    "Alternador", "Amortiguador", "Radiador", "Bomba de agua", "Correa de distribución"
            );

            List<SparePart> parts = IntStream.range(0, partNames.size())
                    .mapToObj(i -> SparePart.builder()
                            .name(partNames.get(i))
                            .inventoryQuantity(10 + random.nextInt(40))
                            .supplier(safeSubstring(faker.company().name(), 200))
                            .unitPrice(100000.0 + random.nextDouble() * 900000.0) // $100.000 a $1.000.000
                            .build()
                    )
                    .collect(Collectors.toList());

            sparePartRepository.saveAll(parts);
            System.out.println("✅ Repuestos insertados correctamente.");
        }
    }
    private String safeSubstring(String input, int maxLength) {
        if (input == null) return "";
        return input.length() > maxLength ? input.substring(0, maxLength) : input;
    }

}
