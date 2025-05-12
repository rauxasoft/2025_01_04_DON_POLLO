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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.services.ProductoServices;
import com.sinensia.donpollo.common.presentation.ErrorResponse;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = ProductoController.class)
public class ProductoControllerTest extends AbstractControllerTest {

	@MockitoBean
	private ProductoServices productoServices;
	
	private Producto producto1;
	private Producto producto2;
	
	@BeforeEach
	void init() {
		initObjects();
	}

	@WithMockUser
	@Test
	void solicitamos_todos_los_productos() throws Exception {
		
		List<Producto> productos = Arrays.asList(producto1, producto2);
		
		when(productoServices.getAll()).thenReturn(productos);
		
		MvcResult mvcResult = mockMvc.perform(get("/productos"))
											.andExpect(status().isOk())
											.andReturn();
	
		assertResponseBodyIsOk(mvcResult, productos);
		
	}
	
	@WithMockUser
	@Test
	void solicitamos_producto_por_id() throws Exception {
		
		when(productoServices.read(500L)).thenReturn(Optional.of(producto1));
		
		MvcResult mvcResult = mockMvc.perform(get("/productos/500"))
				.andExpect(status().isOk())
				.andReturn();
		
		assertResponseBodyIsOk(mvcResult, producto1);
		
	}

	@WithMockUser
	@Test
	void solicitamos_producto_por_id_no_existente() throws Exception {
		
		when(productoServices.read(500L)).thenReturn(Optional.empty());
		
		MvcResult mvcResult = mockMvc.perform(get("/productos/500"))
											.andExpect(status().isNotFound())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("No existe el producto 500"));
		
	}
	
	@WithMockUser
	@Test
	void creamos_producto_ok() throws Exception {
		
		producto1.setId(null);
		
		when(productoServices.create(producto1)).thenReturn(500L); 
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		mockMvc.perform(post("/productos").content(requestBody).contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated())
						.andExpect(header().string("Location","http://localhost/productos/500"));	
	}

	@WithMockUser
	@Test
	void creamos_producto_con_id_no_null() throws Exception {
		
		when(productoServices.create(producto1)).thenThrow(new BusinessException("No se puede crear un producto con id null"));
		
		String requestBody = objectMapper.writeValueAsString(producto1);
		
		MvcResult mvcResult = mockMvc.perform(post("/productos").content(requestBody).contentType(MediaType.APPLICATION_JSON))
											.andExpect(status().isBadRequest())
											.andReturn();
		
		assertResponseBodyIsOk(mvcResult, new ErrorResponse("No se puede crear un producto con id null"));

	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
	
	private void initObjects() {
		
		producto1 = new Producto();
		producto1.setId(500L);
		producto1.setNombre("CocaCola");
		
		producto2 = new Producto();
		producto2.setId(101L);
		producto2.setNombre("Chocolate");
		
	}
	
}