package com.carcenter.car_center_api.client.seeders;

import com.carcenter.car_center_api.client.entities.Client;
import com.carcenter.car_center_api.client.repositories.ClientRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ClientSeeder implements CommandLineRunner {

    private final ClientRepository clientRepository;

    public ClientSeeder(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) {
        if (clientRepository.count() == 0) {
            Faker faker = new Faker(new java.util.Locale("es"));
            Random random = new Random();

            List<Client> clients = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                clients.add(Client.builder()
                        .documentType(random.nextBoolean() ? "CC" : "TI")
                        .document(1000000000 + random.nextInt(999999999))
                        .firstName(faker.name().firstName())
                        .secondName(faker.name().firstName())
                        .firstLastName(faker.name().lastName())
                        .secondLastName(faker.name().lastName())
                        .cellphone("3" + (100000000 + random.nextInt(899999999)))
                        .address(faker.address().streetAddress().substring(0, 10))
                        .email(faker.internet().emailAddress())
                        .vehicles(null)
                        .build()
                );
            }

            clientRepository.saveAll(clients);
            System.out.println("✅ 10 clientes generados automáticamente.");
        }
    }
}
