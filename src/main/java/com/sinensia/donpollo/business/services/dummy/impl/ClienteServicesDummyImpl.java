package com.sinensia.donpollo.business.services.dummy.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.services.ClienteServices;

@Service
public class ClienteServicesDummyImpl implements ClienteServices {

	@Override
	public Long create(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Cliente> read(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Cliente> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
