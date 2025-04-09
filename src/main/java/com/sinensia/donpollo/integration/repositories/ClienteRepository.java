package com.sinensia.donpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.business.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	boolean existsByNif(String nif);
}

