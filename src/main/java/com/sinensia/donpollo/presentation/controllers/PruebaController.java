package com.sinensia.donpollo.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.model.Pedido;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.integration.repositories.ClienteRepository;
import com.sinensia.donpollo.integration.repositories.DependienteRepository;
import com.sinensia.donpollo.integration.repositories.FamiliaRepository;
import com.sinensia.donpollo.integration.repositories.PedidoRepository;
import com.sinensia.donpollo.integration.repositories.ProductoRepository;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {

	@Autowired
	private FamiliaRepository familiaRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private DependienteRepository dependienteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("/pedidos")
	public List<Pedido> getPedidos(){
		return pedidoRepository.findAll();
	}
	
	@GetMapping("/clientes")
	public List<Cliente> getClientes() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/dependientes")
	public List<Dependiente> getDependientes() {
		return dependienteRepository.findAll();
	}
		
	@GetMapping("/productos")
	public List<Producto> getProductos() {
		return productoRepository.findAll();
	}
	
	@GetMapping("/familias")
	public List<Familia> getFamilias() {
		return familiaRepository.findAll();
	}
}
