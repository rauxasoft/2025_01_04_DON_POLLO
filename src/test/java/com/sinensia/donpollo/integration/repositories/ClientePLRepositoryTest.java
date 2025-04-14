package com.sinensia.donpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_testing.sql", "/data/h2/data_testing.sql"})
public class ClientePLRepositoryTest {

	@Autowired
	private ClientePLRepository clienteRepository;
	
	@Test
	void existsByNifTest() {
		
		boolean prueba1 = clienteRepository.existsByNif("66666666P");
		boolean prueba2 = clienteRepository.existsByNif("45899812L");
		
		assertFalse(prueba1);
		assertTrue(prueba2);	
	}
}
