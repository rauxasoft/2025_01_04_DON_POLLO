package com.sinensia.donpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.services.ClienteServices;
import com.sinensia.donpollo.integration.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteServicesImpl implements ClienteServices {

	private final ClienteRepository clienteRepository;
	
	public ClienteServicesImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Override
	@Transactional
	public Long create(Cliente cliente) {
		
		if(cliente.getId() != null) {
			throw new BusinessException("Para crear un cliente la id ha de ser null");
		}
		
		boolean existe = clienteRepository.existsByNif(cliente.getNif());
		
		if (existe) {
			throw new BusinessException("Ya existe un cliente con el NIF: " + cliente.getNif());
		}
		
		Cliente createdCliente = clienteRepository.save(cliente);
		
		return createdCliente.getId();
		
	}

	@Override
	public Optional<Cliente> read(Long id) {
		return clienteRepository.findById(id);
	}

	@Override
	public List<Cliente> getAll() {
		return clienteRepository.findAll();
	}

}
