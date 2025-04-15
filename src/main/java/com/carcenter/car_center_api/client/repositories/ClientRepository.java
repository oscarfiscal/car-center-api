package com.carcenter.car_center_api.client.repositories;

import com.carcenter.car_center_api.client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByDocumentTypeAndDocument(String documentType, Integer document);
    Optional<Client> findByDocument(Integer document);
}