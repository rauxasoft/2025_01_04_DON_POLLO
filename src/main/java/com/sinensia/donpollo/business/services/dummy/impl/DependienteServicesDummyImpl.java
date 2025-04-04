package com.sinensia.donpollo.business.services.dummy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.DatosContacto;
import com.sinensia.donpollo.business.model.Dependiente;
import com.sinensia.donpollo.business.model.Direccion;
import com.sinensia.donpollo.business.services.DependienteServices;

@Service
public class DependienteServicesDummyImpl implements DependienteServices {

	private final Map<Long, Dependiente> DEPENDIENTES_DB = new HashMap<>();
	
	public DependienteServicesDummyImpl() {
		initData();
	}
	
	@Override
	public List<Dependiente> getAll() {
		return new ArrayList<>(DEPENDIENTES_DB.values());
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
		
	private void initData() {
	
		Direccion direccion1 = new Direccion();
		direccion1.setVia("Avenida Don Carlos, 233 ático 1");
		direccion1.setPoblacion("Madrid");
		direccion1.setCodigoPostal("89030");
		direccion1.setProvincia("Madrid");
		direccion1.setPais("España");
		
		DatosContacto datosContacto1 = new DatosContacto();
		datosContacto1.setTelefono1("91 220 89 92");
		datosContacto1.setEmail("honoriomare344@gmail.com");
		
		Direccion direccion2 = new Direccion();
		direccion2.setVia("c/ Mercurio, 20 principal segunda");
		direccion2.setPoblacion("Mataró");
		direccion2.setCodigoPostal("08020");
		direccion2.setProvincia("Barcelona");
		direccion2.setPais("España");
		
		DatosContacto datosContacto2 = new DatosContacto();
		datosContacto2.setTelefono1("609897821");
		datosContacto2.setEmail("carlota@adent.com");
		
		Dependiente dependiente1 = new Dependiente();
		dependiente1.setId(100L);
		dependiente1.setNif("45998123R");
		dependiente1.setNombre("Honorio");
		dependiente1.setApellido1("Martín");
		dependiente1.setApellido2("Salvador");
		dependiente1.setLicenciaManipuladorAlimentos("LM20091112");
		dependiente1.setDireccion(direccion1);
		dependiente1.setDatosContacto(datosContacto1);
		
		Dependiente dependiente2 = new Dependiente();
		dependiente2.setId(101L);
		dependiente2.setNif("35009281H");
		dependiente2.setNombre("Carlota");
		dependiente2.setApellido1("Cifuentes");
		dependiente2.setApellido2("Merino");
		dependiente2.setLicenciaManipuladorAlimentos("LM20092201");
		dependiente2.setDireccion(direccion2);
		dependiente2.setDatosContacto(datosContacto2);
		
		DEPENDIENTES_DB.put(dependiente1.getId(), dependiente1);
		DEPENDIENTES_DB.put(dependiente2.getId(), dependiente2);
		
	}

}
