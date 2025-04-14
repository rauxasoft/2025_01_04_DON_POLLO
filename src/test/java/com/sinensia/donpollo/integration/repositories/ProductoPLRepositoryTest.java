package com.sinensia.donpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.integration.model.ProductoPL;

@DataJpaTest
@Sql(scripts= {"/data/h2/schema_testing.sql", "/data/h2/data_testing.sql"})
public class ProductoPLRepositoryTest {
	
	@Autowired
	ProductoPLRepository productoPLRepository;
	
	@Test
	void findByFamiliaIdTest() {
		
		List<ProductoPL> resultado = productoPLRepository.findByFamiliaId(1L);
		
		assertTrue(resultado.size() == 6);
		
	}
	
	@Test
	void findByPrecioBetweenOrderByPrecioTest() {
		
		List<ProductoPL> resultado = productoPLRepository.findByPrecioBetweenOrderByPrecio(5, 10);
		
		assertTrue(resultado.size() == 17);
		
	}
	
	@Test
	void findByDescatalogadoTrueTest() throws Exception {
		
		List<ProductoPL> resultado = productoPLRepository.findByDescatalogadoTrue();
		
		assertTrue(resultado.size() == 1);
		
	}
	
	@Test
	void findByFechaAltaBetweenOrderByFechaAltaTest() throws Exception {
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date desde = formato.parse("19/10/2017 09:10:00");
        Date hasta = formato.parse("28/10/2017 09:40:00");
        
        List<ProductoPL> resultado = productoPLRepository.findByFechaAltaBetweenOrderByFechaAlta(desde, hasta);
		
        assertTrue(resultado.size() == 58);
	}
	
	@Test
	void getDTO1Test() {
		
		Object[] resultado = productoPLRepository.getDTO1().get(0);
		
		String nombre = (String) resultado[0];
		double precio = (Double) resultado[1];

		assertTrue(nombre.equals("PATATAS BRAVAS [TAPA]"));
		assertTrue(precio == 6);
	}
	
	@Test
	void getDTO2Test() {
		
		// TODO
		
		List<Object[]> resultados = productoPLRepository.getDTO2();
		
		Object[] resultado1 = resultados.get(0);
		
		String nombre1 = (String) resultado1[0];
		String descripcion1 = (String) resultado1[1];

		assertTrue(nombre1.equals("PATATAS BRAVAS"));
		assertTrue(descripcion1.equals("Deliciosas patatas bra..."));
		
		Object[] resultado2 = resultados.get(3);
		
		String nombre2 = (String) resultado2[0];
		String descripcion2 = (String) resultado2[1];

		assertTrue(nombre2.equals("CERVEZA ESTRELLA GALICIA 33CL"));
		assertTrue(descripcion2.equals("Cerveza del Norte!"));
		
	}
	
	@Test
	void updatePreciosFamiliaTest() {
		
		Familia familia = new Familia();
		familia.setId(4L);
		productoPLRepository.updatePrecios(familia, 10);
		
		assertTrue(productoPLRepository.findById(100L).get().getPrecio() == 6.6);
		
	}
	
	@Test
	void updatePreciosArrayIdsTest() {
		
		Long[] ids = {100L};
		productoPLRepository.updatePrecios(ids, 10.0);
		
		Double precioActualizado = productoPLRepository.findById(100L).get().getPrecio();
		Double precioNoActualizado = productoPLRepository.findById(101L).get().getPrecio();
		
		assertEquals(6.6, precioActualizado);
		assertEquals(9.0, precioNoActualizado);
		
	}
	
	@Test
	void updatePreciosProductListTest() {
		
		ProductoPL producto = new ProductoPL();
		producto.setId(100L);
		
		List<ProductoPL> productos = new ArrayList<>();
		productos.add(producto);
		productoPLRepository.updatePrecios(productos, 10);
		
		assertTrue(productoPLRepository.findById(100L).get().getPrecio() == 6.6);
		
	}

}
