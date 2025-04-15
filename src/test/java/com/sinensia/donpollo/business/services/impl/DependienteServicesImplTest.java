package com.sinensia.donpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.integration.model.DependientePL;
import com.sinensia.donpollo.integration.repositories.DependientePLRepository;

@ExtendWith(MockitoExtension.class)
public class DependienteServicesImplTest {

	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private DependientePLRepository dependientePLRepository;
	
	@InjectMocks
	private DependienteServicesImpl dependienteServicesImpl;
	
	private DependientePL dependientePL1;
	private DependientePL dependientePL2;
	
	private Dependiente dependiente1;
	private Dependiente dependiente2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void getAllTest() {
		
		// Arrange
		
		List<Dependiente> dependientesEsperados = Arrays.asList(dependiente1, dependiente2);
		
		when(dependientePLRepository.findAll()).thenReturn(Arrays.asList(dependientePL1, dependientePL2));
		when(mapper.map(dependientePL1, Dependiente.class)).thenReturn(dependiente1);
		when(mapper.map(dependientePL2, Dependiente.class)).thenReturn(dependiente2);
		
		// Act
		
		List<Dependiente> dependientes = dependienteServicesImpl.getAll();
		
		// Aassert
		
		assertEquals(2, dependientes.size());
		assertTrue(dependientes.containsAll(dependientesEsperados));
		
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************

	private void initObjects() {
		
		dependientePL1 = new DependientePL();
		dependientePL2 = new DependientePL();
		
		dependientePL1.setId(100L);
		dependientePL2.setId(200L);
		
		dependientePL1.setNombre("Agapito");
		dependientePL2.setNombre("Carlota");
		
		// ************************************
		
		dependiente1 = new Dependiente();
		dependiente2 = new Dependiente();
		
		dependiente1.setId(100L);
		dependiente2.setId(200L);
		
		dependiente1.setNombre("Agapito");
		dependiente2.setNombre("Carlota");
			
	}
}
