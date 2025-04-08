package com.sinensia.donpollo.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
