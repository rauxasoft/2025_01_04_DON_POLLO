package com.sinensia.donpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.donpollo.business.model.dtos.EstablecimientoDTO3;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_testing.sql", "/data/h2/data_testing.sql"})
public class EstablecimientoPLRepositoryTest {
	
	@Autowired
	EstablecimientoPLRepository establecimientoRepository;
	
	@Test
	void getDTO1Test() {
		
		List<String> resultados = establecimientoRepository.getDTO1().stream()
				.map(x -> {
					
					return new StringBuilder()
								.append((String) x[0])
								.append((String) x[1])
								.append((String) x[2])
								.toString();	
				}).toList();
		
		String resultado1 = "GRAN VIA 2+34 93224707808034";
		String resultado2 = "VAGUADA+34 91368282828029";
		
		List<String> resultadosEsperados = Arrays.asList(resultado1, resultado2);
		
		assertTrue(resultados.containsAll(resultadosEsperados));
		
	}
	
	@Test
	void getDTO2Test() {
		
		Object[] resultado = establecimientoRepository.getDTO2().get(0);
		
		String nombre = (String) resultado[0];
		String provincia = (String) resultado[1];
		String telefono1 = (String) resultado[2];
		String email = (String) resultado[3];

		assertTrue(nombre.equals("Gran Via 2"));
		assertTrue(provincia.equals("Barcelona"));
		assertTrue(telefono1.equals("+34 932247078"));
		assertTrue(email.equals("granvia2@pollosfelices.com"));
		
	}
	
	@Test
	void getDTO3Test() {
		
		EstablecimientoDTO3 resultado = establecimientoRepository.getDTO3().get(0);

		assertTrue(resultado.nombre().equals("GRAN VIA 2"));
		assertTrue(resultado.datos().equals("BARCELONA [BARCELONA] - +34 932247078"));
		
	}

}
