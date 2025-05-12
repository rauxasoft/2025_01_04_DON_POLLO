package com.sinensia.donpollo.presentation.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.services.PedidoServices;
import com.sinensia.donpollo.common.presentation.ErrorResponse;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = PedidoController.class)
public class PedidoControllerTest extends AbstractControllerTest{

	@MockitoBean
	private PedidoServices pedidoServices;
	
	private Pedido pedido1;
	private Pedido pedido2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void solicitamos_todos_los_pedidos() throws Exception{
		
		List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
		
		when(pedidoServices.getAll()).thenReturn(pedidos);
		
		MvcResult mvcResult = mockMvc.perform(get("/pedidos"))
											.andExpect(status().isOk())
											.andReturn();
	
		assertResponseBodyIsOk(mvcResult, pedidos);
		
	}
	
	@Test
	void solicitamos_pedido_por_id() throws Exception {
		
		when(pedidoServices.read(1L)).thenReturn(Optional.of(pedido1));
		
		MvcResult mvcResult = mockMvc.perform(get("/pedidos/1"))
				.andExpect(status().isOk())
				.andReturn();
		
		assertResponseBodyIsOk(mvcResult, pedido1);
		
	}

	@Test
	void solicitamos_pedido_por_id_no_existente() throws Exception {
		
		when(pedidoServices.read(500L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/pedidos/500"))
											.andExpect(status().isNotFound())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("No existe el pedido 500"));
		
	}
	
	@WithMockUser(roles = "ADMIN")
	@Test
	void creamos_pedido_ok() throws Exception {
		
		pedido1.setId(null);
		
		when(pedidoServices.create(pedido1)).thenReturn(3L); 
		
		String requestBody = objectMapper.writeValueAsString(pedido1);
		
		mockMvc.perform(post("/pedidos").content(requestBody).contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated())
						.andExpect(header().string("Location", "http://localhost/pedidos/3"));	
	}

	@WithMockUser(roles = "ADMIN")
	@Test
	void creamos_pedido_con_id_no_null() throws Exception {
		
		when(pedidoServices.create(pedido1)).thenThrow(new BusinessException("No se puede crear un pedido con id no null"));
		
		String requestBody = objectMapper.writeValueAsString(pedido1);
		
		MvcResult mvcResult = mockMvc.perform(post("/pedidos").content(requestBody).contentType(MediaType.APPLICATION_JSON))
											.andExpect(status().isBadRequest())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("No se puede crear un pedido con id no null"));

	}
	
	@WithMockUser(roles = "ADMIN")
	@Test
	void actualizamos_datos_pedido_ok() throws Exception{
		
		String requestBody = objectMapper.writeValueAsString(pedido1);
		
		mockMvc.perform(put("/pedidos/1").content(requestBody).contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isNoContent());
		
		verify(pedidoServices, times(1)).update(pedido1);
		
	}
	
	//********************************************************************************************
	//
	// Private Methods
	//
	//********************************************************************************************
	
	private void initObjects() {
		
		pedido1 = new Pedido();
		pedido1.setId(1L);
		pedido1.setObservaciones("Pedido uno de prova");
		
		pedido2 = new Pedido();
		pedido2.setId(2L);
		pedido2.setObservaciones("Pedido dos de prova");
	}
}
