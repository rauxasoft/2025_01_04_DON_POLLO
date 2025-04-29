package com.sinensia.donpollo.business.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.model.EstadoPedido;
import com.sinensia.donpollo.business.model.LineaDetalle;
import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.model.dtos.PedidoDTO1;
import com.sinensia.donpollo.integration.model.ClientePL;
import com.sinensia.donpollo.integration.model.DependientePL;
import com.sinensia.donpollo.integration.model.EstablecimientoPL;
import com.sinensia.donpollo.integration.model.EstadoPedidoPL;
import com.sinensia.donpollo.integration.model.LineaDetallePL;
import com.sinensia.donpollo.integration.model.PedidoPL;
import com.sinensia.donpollo.integration.model.ProductoPL;
import com.sinensia.donpollo.integration.repositories.PedidoPLRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServicesImplTest {

	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private PedidoPLRepository pedidoPLRepository;
	
	@InjectMocks
	private PedidoServicesImpl pedidoServicesImpl;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private Pedido pedido1;
	private Pedido pedido2;
	private PedidoPL pedido1PL;
	private PedidoPL pedido2PL;
	private PedidoPL pedido1PLcreado;
	private PedidoPL pedido2PLcreado;
	private Establecimiento tienda1;
	private Establecimiento tienda2;
	private Date desde;
	private Date hasta;
	
	@BeforeEach
	void init() throws ParseException {
		initObjects();
	}
	
	@Test
	void create_ok_Test() {
		pedido1.setId(null);
		pedido1PL.setId(null);
		
		when(mapper.map(pedido1, PedidoPL.class)).thenReturn(pedido1PL);
		when(pedidoPLRepository.save(pedido1PL)).thenReturn(pedido1PLcreado);
		
		Long id = pedidoServicesImpl.create(pedido1);
		
		assertEquals(11L, id);
	}
	
	@Test
	void create_con_id_no_null_Test() {
		
		pedido1.setId(12345L);
		
		BusinessException ex = assertThrows(BusinessException.class, () -> {
			pedidoServicesImpl.create(pedido1);
		});	
		
		String mensaje = ex.getMessage();
		
		assertEquals("Para crear un pedido la id ha de ser null", mensaje);
	}				
	
	@Test
	void readTest() {
		
		when(pedidoPLRepository.findById(11L)).thenReturn(Optional.of(pedido1PL));
		when(mapper.map(pedido1PL, Pedido.class)).thenReturn(pedido1);
		
		Optional<Pedido> optional = pedidoServicesImpl.read(11L);
		
		assertTrue(optional.isPresent());
		assertEquals(pedido1, optional.get());
	}			
	
	@Test
	void read_not_found_Test() {
		
		when(pedidoPLRepository.findById(40L)).thenReturn(Optional.empty());
		
		Optional<Pedido> optional = pedidoServicesImpl.read(40L);
		
		assertTrue(optional.isEmpty());
		
	}
	
	@Test
	void updateTest() {
		
		pedido1.setEstado(EstadoPedido.CANCELADO);
		pedido1PL.setEstado(EstadoPedidoPL.CANCELADO);
		pedido1PLcreado.setEstado(EstadoPedidoPL.CANCELADO);
		
		when(pedidoPLRepository.existsById(11L)).thenReturn(true);
		when(mapper.map(pedido1, PedidoPL.class)).thenReturn(pedido1PL);
		when(pedidoPLRepository.save(pedido1PL)).thenReturn(pedido1PLcreado);
		
		pedidoServicesImpl.update(pedido1);
		
		assertEquals(EstadoPedido.CANCELADO, pedido1.getEstado());
		
	}		
	
	@Test
	void getAllTest() {
		
		List<Pedido> pedidosEsperados = Arrays.asList(pedido1, pedido2);
		
		when(pedidoPLRepository.findAll()).thenReturn(Arrays.asList(pedido1PL, pedido2PL));
		when(mapper.map(pedido1PL, Pedido.class)).thenReturn(pedido1);
		when(mapper.map(pedido2PL, Pedido.class)).thenReturn(pedido2);
				
		List<Pedido> pedidos = pedidoServicesImpl.getAll();
				
		assertEquals(2, pedidos.size());
		assertTrue(pedidos.containsAll(pedidosEsperados));
	}
	
	@Test
	void getByIdEstablecimientoTest() {

		List<Pedido> pedidosEsperados = Arrays.asList(pedido1, pedido2);
		
		when(pedidoPLRepository.findByEstablecimientoId(10L)).thenReturn(Arrays.asList(pedido1PL, pedido2PL));
		when(mapper.map(pedido1PL, Pedido.class)).thenReturn(pedido1);
		when(mapper.map(pedido2PL, Pedido.class)).thenReturn(pedido2);
		
		List<Pedido> pedidos= pedidoServicesImpl.getByIdEstablecimiento(10L); 
		
		assertEquals(pedidosEsperados, pedidos);
	}
	
	@Test
	void getBetweenFechasTest() throws ParseException {
		
		List<Pedido> pedidosEsperados = Arrays.asList(pedido2);
		
		when(pedidoPLRepository.findByFechaHoraBetweenOrderByFechaHora(desde, hasta)).thenReturn(Arrays.asList(pedido2PL));
		when(mapper.map(pedido2PL, Pedido.class)).thenReturn(pedido2);
		
		List<Pedido> pedidos= pedidoServicesImpl.getBetweenFechas(desde, hasta);
		
		assertEquals(pedidosEsperados, pedidos);
	}
	
	@Test
	void getNumeroTotalPedidosTest() {
		
		when(pedidoPLRepository.count()).thenReturn((long) 2);
		int totalPedidos = pedidoServicesImpl.getNumeroTotalPedidos();
		
		assertEquals(2, totalPedidos);
	}
	
	@Test
	void getEstadisticaNumeroPedidosByEstablecimientoTest() {
		
		Object[] fila1 = {tienda1.getNombre(), 2L};
		Object[] fila2 = {tienda2.getNombre(), 0L};
		List<Object[]> resultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getEstadisticaNumeroPedidosByEstablecimiento()).thenReturn(resultados);
		
		Map<String, Integer> estadistica = pedidoServicesImpl.getEstadisticaNumeroPedidosByEstablecimiento();
		
		assertEquals(2, estadistica.size());
		
		assertTrue(estadistica.containsKey(tienda1.getNombre()));
		assertTrue(estadistica.containsKey(tienda2.getNombre()));
		assertEquals(2, estadistica.get(tienda1.getNombre()));
		assertEquals(0, estadistica.get(tienda2.getNombre()));
		
	}
	
	@Test
	void getEstadisticaNumeroPedidosByEstablecimientoBetweenDatesTest() {
		
		Object[] fila1 = {tienda1.getNombre(), 1L};
		Object[] fila2 = {tienda2.getNombre(), 0L};
		List<Object[]> resultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getEstadisticaNumeroPedidosByEstablecimiento()).thenReturn(resultados);
		
		Map<String, Integer> estadistica = pedidoServicesImpl.getEstadisticaNumeroPedidosByEstablecimiento(desde, hasta);
		
		assertEquals(2, estadistica.size());
		
		assertTrue(estadistica.containsKey(tienda1.getNombre()));
		assertTrue(estadistica.containsKey(tienda2.getNombre()));
		assertEquals(1, estadistica.get(tienda1.getNombre()));
		assertEquals(0, estadistica.get(tienda2.getNombre()));
		
	}
	
	@Test
	void getEstadisticaImporteMedioByEstablecimientoTest() {
		
		Object[] fila1 = {tienda1.getNombre(), 11.0};
		Object[] fila2 = {tienda2.getNombre(), 0.0};
		List<Object[]> resultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getEstadisticaImporteMedioByEstablecimiento()).thenReturn(resultados);
		
		Map<String, Double> estadistica = pedidoServicesImpl.getEstadisticaImporteMedioByEstablecimiento();
		
		assertEquals(2, estadistica.size());
		
		assertTrue(estadistica.containsKey(tienda1.getNombre()));
		assertTrue(estadistica.containsKey(tienda2.getNombre()));
		assertEquals(11.0, estadistica.get(tienda1.getNombre()));
		assertEquals(0.0, estadistica.get(tienda2.getNombre()));
		
	}
	
	@Test
	void getEstadisticaImporteMedioByEstablecimientoBetweenDatesTest() {
		
		Object[] fila1 = {tienda1.getNombre(), 10.0};
		Object[] fila2 = {tienda2.getNombre(), 0.0};
		List<Object[]> resultados = Arrays.asList(fila1, fila2);
		
		when(pedidoPLRepository.getEstadisticaImporteMedioByEstablecimiento(desde, hasta)).thenReturn(resultados);
		
		Map<String, Double> estadistica = pedidoServicesImpl.getEstadisticaImporteMedioByEstablecimiento(desde, hasta);
		
		assertEquals(2, estadistica.size());
		
		assertTrue(estadistica.containsKey(tienda1.getNombre()));
		assertTrue(estadistica.containsKey(tienda2.getNombre()));
		assertEquals(10.0, estadistica.get(tienda1.getNombre()));
		assertEquals(0.0, estadistica.get(tienda2.getNombre()));
		
	}
	
	@Test
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
	void getEstadisticaNumeroPedidosByDependienteBetweenDatesTest() {
		
		Object[] fila1 = {"[1] López, Marta", 214L};
		Object[] fila2 = {"[2] Pérez , Carlos", 560L};
		
		List<Object[]> resultados = Arrays.asList(fila1, fila2);
		  
		when(pedidoPLRepository.getEstadisticaNumeroPedidosByDependiente(desde, hasta)).thenReturn(resultados);
		
		Map<String, Integer> estadistica = pedidoServicesImpl.getEstadisticaNumeroPedidosByDependiente(desde, hasta);
		
		assertEquals(2, estadistica.size());
		
		assertTrue(estadistica.containsKey("[1] López, Marta"));
		assertTrue(estadistica.containsKey("[2] Pérez , Carlos"));
		assertEquals(214, estadistica.get("[1] López, Marta"));
		assertEquals(560, estadistica.get("[2] Pérez , Carlos"));
	}
	
	@Test
	void getDTO1Test() {
		
		Object[] fila1 = {pedido1PL.getId(), pedido1PL.getEstablecimiento().getNombre(), pedido1PL.getFechaHora(), pedido1PL.getEstado(), "López, Marta"};
		Object[] fila2 = {pedido2PL.getId(), pedido2PL.getEstablecimiento().getNombre(), pedido2PL.getFechaHora(), pedido2PL.getEstado(), "Pérez, Carlos"};
		List<Object[]> resultadoEsperado = Arrays.asList(fila1, fila2);		
		
		PedidoDTO1 fila1DTO = new PedidoDTO1(pedido1.getId().intValue(), pedido1.getEstablecimiento().getNombre(), "17/03/2025", "10:35", pedido1.getEstado().toString(), "López, Marta");
		PedidoDTO1 fila2DTO = new PedidoDTO1(pedido2.getId().intValue(), pedido2.getEstablecimiento().getNombre(), "10/12/2024", "12:35", pedido2.getEstado().toString(), "Pérez, Carlos");
		List<PedidoDTO1> resultadoEsperadoDTO = Arrays.asList(fila1DTO, fila2DTO);
		
		when(pedidoPLRepository.getDTO1()).thenReturn(resultadoEsperado);
		
		List<PedidoDTO1> resultado = pedidoServicesImpl.getDTO1();
		
		assertEquals(resultado, resultadoEsperadoDTO);
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************

	private void initObjects() throws ParseException {
	
		Date fecha1 = sdf.parse("2025-03-17 10:35");
		Date fecha2 = sdf.parse("2024-12-10 12:35");
		
		desde = sdf.parse("2025-01-01 10:00");
		hasta = sdf.parse("2025-04-24 10:00");
		
		ClientePL cliente1PL = new ClientePL();
		cliente1PL.setId(1L);
		cliente1PL.setNombre("Ana Gómez");
	
		ClientePL cliente2PL = new ClientePL();
		cliente2PL.setId(2L);
		cliente2PL.setNombre("Luis Ruiz");
	
		DependientePL dep1PL = new DependientePL();
		dep1PL.setId(1L);
		dep1PL.setNombre("Marta López");
	
		DependientePL dep2PL = new DependientePL();
		dep2PL.setId(2L);
		dep2PL.setNombre("Carlos Pérez");
	
		EstablecimientoPL tiendaPL = new EstablecimientoPL();
		tiendaPL.setId(10L);
		tiendaPL.setNombre("Tienda Central");
	
		ProductoPL producto1PL = new ProductoPL();
		producto1PL.setId(1L);
		producto1PL.setNombre("Producto1");
		
		ProductoPL producto2PL = new ProductoPL();
		producto2PL.setId(2L);
		producto2PL.setNombre("Producto2");
		
		LineaDetallePL l1PL = new LineaDetallePL();
		l1PL.setProducto(producto1PL);
		l1PL.setCantidad(5);
		l1PL.setPrecio(15.58);
		
		LineaDetallePL l2PL = new LineaDetallePL();
		l2PL.setProducto(producto2PL);
		l2PL.setCantidad(3);
		l2PL.setPrecio(23.41);
		
		List<LineaDetallePL> lineasPedido1PL = Arrays.asList(l1PL, l2PL);
		List<LineaDetallePL> lineasPedido2PL = Arrays.asList(l2PL);
	
		pedido1PL = new PedidoPL();
		pedido1PL.setId(11L);
		pedido1PL.setFechaHora(fecha1);
		pedido1PL.setCliente(cliente1PL);
		pedido1PL.setDependiente(dep1PL);
		pedido1PL.setEstablecimiento(tiendaPL);
		pedido1PL.setEstado(EstadoPedidoPL.PENDIENTE_ENTREGA);
		pedido1PL.setObservaciones("Entrega en horario de tarde");
		pedido1PL.setLineas(lineasPedido1PL);
	
		pedido2PL = new PedidoPL();
		pedido2PL.setId(22L);
		pedido2PL.setFechaHora(fecha2);
		pedido2PL.setCliente(cliente2PL);
		pedido2PL.setDependiente(dep2PL);
		pedido2PL.setEstablecimiento(tiendaPL);
		pedido2PL.setEstado(EstadoPedidoPL.ENTREGADO);
		pedido2PL.setObservaciones("Pagado en efectivo");
		pedido2PL.setLineas(lineasPedido2PL);
	
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNombre("Ana Gómez");
	
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNombre("Luis Ruiz");
	
		Dependiente dep1 = new Dependiente();
		dep1.setId(1L);
		dep1.setNombre("Marta López");
	
		Dependiente dep2 = new Dependiente();
		dep2.setId(2L);
		dep2.setNombre("Carlos Pérez");
	
		tienda1 = new Establecimiento();
		tienda1.setId(10L);
		tienda1.setNombre("Tienda Central");
		
		tienda2 = new Establecimiento();
		tienda2.setId(20L);
		tienda2.setNombre("Tienda SubCentral");
	
		Producto producto1 = new Producto();
		producto1.setId(1L);
		producto1.setNombre("Producto1");
		
		Producto producto2 = new Producto();
		producto2.setId(2L);
		producto2.setNombre("Producto2");
		
		LineaDetalle l1 = new LineaDetalle();
		l1.setProducto(producto1);
		l1.setCantidad(5);
		l1.setPrecio(2);
		
		LineaDetalle l2 = new LineaDetalle();
		l2.setProducto(producto2);
		l2.setCantidad(3);
		l2.setPrecio(4);
		
		List<LineaDetalle> lineasPedido1 = Arrays.asList(l1, l2);
		List<LineaDetalle> lineasPedido2 = Arrays.asList(l2);
	
		pedido1 = new Pedido();
		pedido1.setId(11L);
		pedido1.setFechaHora(fecha1);
		pedido1.setCliente(cliente1);
		pedido1.setDependiente(dep1);
		pedido1.setEstablecimiento(tienda1);
		pedido1.setEstado(EstadoPedido.PENDIENTE_ENTREGA);
		pedido1.setObservaciones("Entrega en horario de tarde");
		pedido1.setLineas(lineasPedido1);
		
		pedido2 = new Pedido();
		pedido2.setId(22L);
		pedido2.setFechaHora(fecha2);
		pedido2.setCliente(cliente2);
		pedido2.setDependiente(dep2);
		pedido2.setEstablecimiento(tienda1);
		pedido2.setEstado(EstadoPedido.ENTREGADO);
		pedido2.setObservaciones("Pagado en efectivo");
		pedido2.setLineas(lineasPedido2);
		
		pedido1PLcreado= new PedidoPL();
		pedido1PLcreado.setId(11L);
		pedido1PLcreado.setFechaHora(fecha1);
		pedido1PLcreado.setCliente(cliente1PL);
		pedido1PLcreado.setDependiente(dep1PL);
		pedido1PLcreado.setEstablecimiento(tiendaPL);
		pedido1PLcreado.setEstado(EstadoPedidoPL.PENDIENTE_ENTREGA);
		pedido1PLcreado.setObservaciones("Entrega en horario de tarde");
		pedido1PLcreado.setLineas(lineasPedido1PL);
	
		pedido2PLcreado = new PedidoPL();
		pedido2PLcreado.setId(22L);
		pedido2PLcreado.setFechaHora(fecha2);
		pedido2PLcreado.setCliente(cliente2PL);
		pedido2PLcreado.setDependiente(dep2PL);
		pedido2PLcreado.setEstablecimiento(tiendaPL);
		pedido2PLcreado.setEstado(EstadoPedidoPL.ENTREGADO);
		pedido2PLcreado.setObservaciones("Pagado en efectivo");
		pedido2PLcreado.setLineas(lineasPedido2PL);
	}
}
