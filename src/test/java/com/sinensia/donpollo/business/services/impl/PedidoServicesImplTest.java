package com.sinensia.donpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.donpollo.integration.repositories.PedidoPLRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServicesImplTest {

	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private PedidoPLRepository pedidoPLRepository;
	
	@InjectMocks
	private PedidoServicesImpl pedidoServicesImpl;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	@Disabled
	void createTest() {
		fail("Not implemented yet!");
	}				
	
	@Test
	@Disabled
	void readTest() {
		fail("Not implemented yet!");
	}			
	
	@Test
	@Disabled
	void updateTest() {
		fail("Not implemented yet!");
	}		
	
	@Test
	@Disabled
	void getAllTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getByIdEstablecimientoTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getBetweenFechasTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getNumeroTotalPedidosTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getEstadisticaNumeroPedidosByEstablecimientoTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getEstadisticaNumeroPedidosByEstablecimientoBetweenDatesTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getEstadisticaImporteMedioByEstablecimientoTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getEstadisticaImporteMedioByEstablecimientoBetweenDatesTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getEstadisticaNumeroPedidosByDependienteTest() {
		
		Object[] fila1 = {"[1887] López Quesada, Juan", 214L};
		Object[] fila2 = {"[29] Cifuentes Merino, Carlota", 560L};
		
		List<Object[]> resultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getEstadisticaNumeroPedidosByDependiente()).thenReturn(resultados);
		
		Map<String, Integer> estadistica = pedidoServicesImpl.getEstadisticaNumeroPedidosByDependiente();
		
		assertEquals(2, estadistica.size());
		
		assertTrue(estadistica.containsKey("[1887] López Quesada, Juan"));
		assertTrue(estadistica.containsKey("[29] Cifuentes Merino, Carlota"));
		assertEquals(214, estadistica.get("[1887] López Quesada, Juan"));
		assertEquals(560, estadistica.get("[29] Cifuentes Merino, Carlota"));
	}
	
	@Test
	@Disabled
	void getEstadisticaNumeroPedidosByDependienteBetweenDatesTest() {
		fail("Not implemented yet!");
	}
	
	@Test
	@Disabled
	void getDTO1Test() {
		fail("Not implemented yet!");
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************

	private void initObjects() {
		
	}
}
