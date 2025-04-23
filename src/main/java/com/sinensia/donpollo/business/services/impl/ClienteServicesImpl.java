package com.sinensia.donpollo.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.services.ClienteServices;
import com.sinensia.donpollo.integration.model.ClientePL;
import com.sinensia.donpollo.integration.repositories.ClientePLRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteServicesImpl implements ClienteServices {

	private final ClientePLRepository clientePLRepository;
	private final DozerBeanMapper mapper;
	
	public ClienteServicesImpl(ClientePLRepository clienteRepository, DozerBeanMapper mapper) {
		this.clientePLRepository = clienteRepository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public Long create(Cliente cliente) {
		
		if(cliente.getId() != null) {
			throw new BusinessException("Para crear un cliente la id ha de ser null");
		}
		
		boolean existe = clientePLRepository.existsByNif(cliente.getNif());
		
		if (existe) {
			throw new BusinessException("Ya existe un cliente con el NIF: " + cliente.getNif());
		}
		
		ClientePL clientePL = mapper.map(cliente, ClientePL.class);
	
		return clientePLRepository.save(clientePL).getId();
	}

	@Override
	public Optional<Cliente> read(Long id) {
			
		return clientePLRepository.findById(id).stream()
				.map(x -> mapper.map(x, Cliente.class))
				.findAny();
	}

	@Override
	public List<Cliente> getAll() {
		
		return clientePLRepository.findAll().stream()
				.map(x -> mapper.map(x, Cliente.class))
				.toList();
	}

}
