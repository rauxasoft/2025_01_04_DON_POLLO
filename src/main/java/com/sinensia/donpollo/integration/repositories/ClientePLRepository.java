package com.sinensia.donpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.integration.model.ClientePL;

public interface ClientePLRepository extends JpaRepository<ClientePL, Long>{

	boolean existsByNif(String nif);
}

