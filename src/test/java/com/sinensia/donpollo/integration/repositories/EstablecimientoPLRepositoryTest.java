package com.sinensia.donpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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
		
		String establecimiento1 = "GRAN VIA 2BARCELONA [BARCELONA] - +34 932247078";
		String establecimiento2 = "VAGUADAMADRID [MADRID] - +34 913682828";
		
		List<String> establecimientosEsperados = Arrays.asList(establecimiento1, establecimiento2);
		List<String> establecimientosObtenidos = new ArrayList<>();
		
		List<Object[]> resultado = establecimientoRepository.getDTO3();

		resultado.stream().forEach(fila -> {
			establecimientosObtenidos.add((String) fila[0] + (String) fila[1]);
		});
		
		assertEquals(2, establecimientosObtenidos.size());
		assertTrue(establecimientosEsperados.containsAll(establecimientosObtenidos));
		
	}

}
