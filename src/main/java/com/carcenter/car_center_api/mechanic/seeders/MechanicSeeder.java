package com.carcenter.car_center_api.mechanic.seeders;

import com.carcenter.car_center_api.mechanic.entities.Mechanic;
import com.carcenter.car_center_api.mechanic.entities.MechanicStatus;
import com.carcenter.car_center_api.mechanic.repositories.MechanicRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MechanicSeeder implements CommandLineRunner {

    private final MechanicRepository mechanicRepository;

    public MechanicSeeder(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    @Override
    public void run(String... args) {
        if (mechanicRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));
            Random random = new Random();

            List<Mechanic> mechanics = IntStream.range(0, 15)
                    .mapToObj(i -> {
                        String documentType = random.nextBoolean() ? "CC" : "TI";
                        int document = 10000000 + random.nextInt(90000000);
                        String firstName = safeSubstring(faker.name().firstName(), 30);
                        String secondName = safeSubstring(faker.name().firstName(), 30);
                        String firstLastName = safeSubstring(faker.name().lastName(), 30);
                        String secondLastName = safeSubstring(faker.name().lastName(), 30);
                        String cellphone = "3" + (100000000 + random.nextInt(899999999));
                        String address = safeSubstring(faker.address().streetAddress(), 20);
                        String email = safeSubstring(faker.internet().emailAddress(), 100);
                        MechanicStatus status = MechanicStatus.values()[random.nextInt(MechanicStatus.values().length)];

                        return Mechanic.builder()
                                .documentType(documentType)
                                .document(document)
                                .firstName(firstName)
                                .secondName(secondName)
                                .firstLastName(firstLastName)
                                .secondLastName(secondLastName)
                                .cellphone(cellphone)
                                .address(address)
                                .email(email)
                                .status(status)
                                .build();
                    })
                    .collect(Collectors.toList());

            mechanicRepository.saveAll(mechanics);
            System.out.println("✅ Mecánicos generados exitosamente.");
        }
    }

    private String safeSubstring(String input, int maxLength) {
        if (input == null) return "";
        return input.length() > maxLength ? input.substring(0, maxLength) : input;
    }
}