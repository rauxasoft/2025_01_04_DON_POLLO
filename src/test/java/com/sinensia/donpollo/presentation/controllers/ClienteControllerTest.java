package com.sinensia.donpollo.presentation.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.services.ClienteServices;
import com.sinensia.donpollo.common.presentation.ErrorResponse;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = ClienteController.class)
public class ClienteControllerTest extends AbstractControllerTest {

	@MockitoBean
	private ClienteServices clienteServices;
	
	private Cliente cliente1;
	private Cliente cliente2;
	
	@BeforeEach
	void init() {
		initObjects();
	}

	@Test
	void solicitamos_todos_los_clientes() throws Exception {
		
		List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
		
		when(clienteServices.getAll()).thenReturn(clientes);
		
		MvcResult mvcResult = mockMvc.perform(get("/clientes"))
											.andExpect(status().isOk())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, clientes);
		
	}
	
	@Test
	void solicitamos_cliente_por_id() throws Exception {
		
		when(clienteServices.read(100L)).thenReturn(Optional.of(cliente1));
		
		MvcResult mvcResult = mockMvc.perform(get("/clientes/100"))
				.andExpect(status().isOk())
				.andReturn();
		
		assertResponseBodyIsOk(mvcResult, cliente1);
		
	}

	@Test
	void solicitamos_cliente_por_id_no_existente() throws Exception {
		
		when(clienteServices.read(500L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/clientes/500"))
											.andExpect(status().isNotFound())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("No existe el cliente 500"));
		
	}
	
	@Test
	void crearmos_cliente_ok() throws Exception {
		
		cliente1.setId(null);
		
		when(clienteServices.create(cliente1)).thenReturn(200L); 
		
		String requestBody = objectMapper.writeValueAsString(cliente1);
		
		mockMvc.perform(post("/clientes").content(requestBody).contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated())
						.andExpect(header().string("Location","http://localhost/clientes/200"));	
	}

	@Test
	void crearmos_cliente_con_id_no_null() throws Exception {
		
		when(clienteServices.create(cliente1)).thenThrow(new BusinessException("No se puede crear un cliente con id null"));
			
		String requestBody = objectMapper.writeValueAsString(cliente1);
		
		MvcResult mvcResult = mockMvc.perform(post("/clientes").content(requestBody).contentType(MediaType.APPLICATION_JSON))
											.andExpect(status().isBadRequest())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("No se puede crear un cliente con id null"));
		
	}

	@Test
	void crearmos_cliente_con_nif_repetido() throws Exception {
	
		cliente1.setId(null);
		
		when(clienteServices.create(cliente1)).thenThrow(new BusinessException("Ya existe un cliente con el mismo nif"));
		
		String requestBody = objectMapper.writeValueAsString(cliente1);
		
		MvcResult mvcResult = mockMvc.perform(post("/clientes").content(requestBody).contentType(MediaType.APPLICATION_JSON))
											.andExpect(status().isBadRequest())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("Ya existe un cliente con el mismo nif"));
		
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************

	private void initObjects() {
		
		cliente1 = new Cliente();
		cliente1.setId(100L);
		cliente1.setNombre("José Ramón");
		cliente1.setNif("49887123R");
		
		cliente2 = new Cliente();
		cliente2.setId(101L);
		cliente2.setNombre("Carmela");
		cliente2.setNif("34452716T");
		
	}
	
}
