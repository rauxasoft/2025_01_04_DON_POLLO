package com.sinensia.donpollo.business.services;

import java.util.List;
import java.util.Optional;

import com.sinensia.donpollo.business.model.Cliente;

public interface ClienteServices {
	
	Long create(Cliente cliente);
	
	Optional<Cliente> read(Long id);
	
	List<Cliente> getAll();

}
