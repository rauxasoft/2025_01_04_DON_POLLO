package com.sinensia.donpollo.business.services.dummy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Cliente;
import com.sinensia.donpollo.business.model.DatosContacto;
import com.sinensia.donpollo.business.model.Direccion;
import com.sinensia.donpollo.business.services.ClienteServices;

@Service
public class ClienteServicesDummyImpl implements ClienteServices {

	private final Map<Long, Cliente> CLIENTES_BD = new HashMap<>();

	public ClienteServicesDummyImpl() {
		initData();
	}

	@Override
	public List<Cliente> getAll() {
		return new ArrayList<>(CLIENTES_BD.values());
	}

	@Override
	public Optional<Cliente> read(Long idCliente) {
		return Optional.ofNullable(CLIENTES_BD.get(idCliente));
	}

	@Override
	public Long create(Cliente cliente) {
		
		if(cliente.getId() != null) {
			throw new BusinessException("Para crear un cliente la id ha de ser null");
		}
		
		boolean existe = CLIENTES_BD.values()
				.stream()
				.map(x -> x.getNif())
				.anyMatch(nif -> nif.equals(cliente.getNif()));
		
		if (existe) {
			throw new BusinessException("Ya existe un cliente con el NIF: " + cliente.getNif());
		}
		
		Long id = System.currentTimeMillis();
		
		cliente.setId(id);
		
		CLIENTES_BD.put(id, cliente);
	
		return id;
		
	}

	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************

	public void initData() {
		
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNif("12345678A");
        cliente1.setNombre("Juan");
        cliente1.setApellido1("Pérez");
        cliente1.setApellido2("García");
        
        Direccion direccion1 = new Direccion();
        direccion1.setVia("Avenida Don Carlos, 233 ático 1");
        direccion1.setPoblacion("Madrid");
        direccion1.setCodigoPostal("89030");
        direccion1.setProvincia("Madrid");
        direccion1.setPais("España");
        
        DatosContacto datosContacto1 = new DatosContacto();
        datosContacto1.setTelefono1("91 330 89 92");
        datosContacto1.setEmail("pancracio344@gmail.com");
        
        cliente1.setDireccion(direccion1);
        cliente1.setDatosContacto(datosContacto1);
        cliente1.setGold(true);
        
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNif("98765432B");
        cliente2.setNombre("Ana");
        cliente2.setApellido1("López");
        cliente2.setApellido2("Martínez");
        
        Direccion direccion2 = new Direccion();
        direccion2.setVia("c/ Mercurio, 20 principal segunda");
        direccion2.setPoblacion("Mataró");
        direccion2.setCodigoPostal("08020");
        direccion2.setProvincia("Barcelona");
        direccion2.setPais("España");
        
        DatosContacto datosContacto2 = new DatosContacto();
        datosContacto2.setTelefono1("609897821");
        datosContacto2.setEmail("carlota@adent.com");
        
        cliente2.setDireccion(direccion2);
        cliente2.setDatosContacto(datosContacto2);
        cliente2.setGold(false);
        
        CLIENTES_BD.put(cliente1.getId(), cliente1);
        CLIENTES_BD.put(cliente2.getId(), cliente2);

    }

}
