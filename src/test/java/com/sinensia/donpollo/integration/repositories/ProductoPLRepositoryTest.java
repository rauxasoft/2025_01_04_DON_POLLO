package com.sinensia.donpollo.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.sinensia.donpollo.integration.model.FamiliaPL;
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
		
		// TODO revisar poder testear sin tener en cuenta el orden y evitando assertTrue habiendo assertEquals
		
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
		
		FamiliaPL familiaPL = new FamiliaPL();
		familiaPL.setId(4L);
		productoPLRepository.updatePrecios(familiaPL, 10);
		
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
/*	
	@Test
	void updatePreciosProductListTest() {
		
		ProductoPL producto = new ProductoPL();
		producto.setId(100L);
		
		List<ProductoPL> productos = new ArrayList<>();
		productos.add(producto);
		productoPLRepository.updatePrecios(productos, 10);
		
		assertTrue(productoPLRepository.findById(100L).get().getPrecio() == 6.6);
		
	}
*/
	
	@Test
	void getEstadisticaNumeroProductosByFamilia() {

		Map<Long, Long> resultadosEsperados = new HashMap<>();
		
		resultadosEsperados.put(1L,  6L);  // Familia [id=1, nombre=LICOR]: 6
		resultadosEsperados.put(2L, 11L);  // Familia [id=2, nombre=REFRESCO]: 11
		resultadosEsperados.put(3L,  2L);  // Familia [id=3, nombre=CERVEZA]: 2
		resultadosEsperados.put(4L,  6L);  // Familia [id=4, nombre=TAPA]: 6
		resultadosEsperados.put(5L, 12L);  // Familia [id=5, nombre=COMIDA]: 12
		resultadosEsperados.put(6L,  0L);  // Familia [id=6, nombre=POSTRE]: 0
		resultadosEsperados.put(7L,  3L);  // Familia [id=7, nombre=AGUA]: 3
		resultadosEsperados.put(8L,  5L);  // Familia [id=8, nombre=INFUSION]: 5
		resultadosEsperados.put(9L,  11L); // Familia [id=9, nombre=BOCADILLO]: 11
		resultadosEsperados.put(10L,  7L); // Familia [id=10, nombre=CAFE]: 7
		resultadosEsperados.put(11L,  7L); // Familia [id=11, nombre=BOLLERIA]: 7
		resultadosEsperados.put(12L,  0L); // Familia [id=12, nombre=ZUMO]: 0
		
		List<Object[]> resultados = productoPLRepository.getEstadisticaNumeroProductosByFamilia();
		
		resultados.forEach(fila -> {
			
			Long idFamilia = ((FamiliaPL) fila[0]).getId();
			Long cantidad = (Long) fila [1];
			
			assertEquals(resultadosEsperados.get(idFamilia), cantidad);
			
		});
			
	}
	
}
