package com.sinensia.donpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.integration.model.ClientePL;
import com.sinensia.donpollo.integration.repositories.ClientePLRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServicesImplTest {
	
	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private ClientePLRepository clientePLRepository;
	
	@InjectMocks
	private ClienteServicesImpl clienteServicesImpl;
	
	private Cliente nuevoCliente;
	private ClientePL nuevoClientePL;
	private ClientePL createdClientePL1;
	
	private Cliente cliente1;
	private Cliente cliente2;

	private ClientePL clientePL1;
	private ClientePL clientePL2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void create_ok_Test() {
		
		when(clientePLRepository.existsByNif("11111111R")).thenReturn(false);
		when(mapper.map(nuevoCliente, ClientePL.class)).thenReturn(nuevoClientePL);
		when(clientePLRepository.save(nuevoClientePL)).thenReturn(createdClientePL1);
		
		Long id = clienteServicesImpl.create(nuevoCliente);
		
		assertEquals(100L, id);
	}
	
	@Test
	void create_con_id_no_null_Test() {
		
		nuevoCliente.setId(12345L);
		
		BusinessException ex = assertThrows(BusinessException.class, () -> {
			clienteServicesImpl.create(nuevoCliente);
		});	
		
		String mensaje = ex.getMessage();
		
		assertEquals("Para crear un cliente la id ha de ser null", mensaje);
	}
	
	@Test
	void create_con_nif_repetido_Test() {
		
		when(clientePLRepository.existsByNif("11111111R")).thenReturn(true);
		
		BusinessException ex = assertThrows(BusinessException.class, () -> {
			clienteServicesImpl.create(nuevoCliente);
		});	
		
		String mensaje = ex.getMessage();
		
		assertEquals("Ya existe un cliente con el NIF: 11111111R", mensaje);
		
	}
	
	@Test
	void readTest() {
		
		when(clientePLRepository.findById(40L)).thenReturn(Optional.of(clientePL2));
		when(mapper.map(clientePL2, Cliente.class)).thenReturn(cliente1);
		
		Optional<Cliente> optional = clienteServicesImpl.read(40L);
		
		assertTrue(optional.isPresent());
		assertEquals(cliente1, optional.get());
	}
	
	@Test
	void read_not_found_Test() {
		
		when(clientePLRepository.findById(40L)).thenReturn(Optional.empty());
		
		Optional<Cliente> optional = clienteServicesImpl.read(40L);
		
		assertTrue(optional.isEmpty());
		
	}
	
	@Test
	void getAllTest() {
		
		List<Cliente> clientesEsperados = Arrays.asList(cliente1, cliente2);
				
		when(clientePLRepository.findAll()).thenReturn(Arrays.asList(clientePL1, clientePL2));
		when(mapper.map(clientePL1, Cliente.class)).thenReturn(cliente1);
		when(mapper.map(clientePL2, Cliente.class)).thenReturn(cliente2);
				
		List<Cliente> clientes = clienteServicesImpl.getAll();
				
		assertEquals(2, clientes.size());
		assertTrue(clientes.containsAll(clientesEsperados));
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************

	private void initObjects() {
		
		nuevoCliente = new Cliente();
		nuevoCliente.setId(null);
		nuevoCliente.setNif("11111111R");
		
		nuevoClientePL = new ClientePL();
		nuevoClientePL.setId(null);
		nuevoClientePL.setNif("11111111R");
		
		createdClientePL1 = new ClientePL();
		createdClientePL1.setId(100L);
		createdClientePL1.setNif("11111111R");

		// *************************************
		
		cliente1 = new Cliente();
		cliente1.setId(40L);
		cliente1.setNombre("Anna");
		
		cliente2 = new Cliente();
		cliente2.setId(50L);
		cliente2.setNombre("Roberto");
		
		clientePL1 = new ClientePL();
		clientePL1.setId(40L);
		clientePL1.setNombre("Anna");
	
		clientePL2 = new ClientePL();
		clientePL2.setId(50L);
		clientePL2.setNombre("Roberto");
		
	}

}
