package com.sinensia.donpollo.business.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.model.DatosContacto;
import com.sinensia.donpollo.business.model.Direccion;
import com.sinensia.donpollo.business.model.Establecimiento;
import com.sinensia.donpollo.business.services.EstablecimientoServices;

@Service
public class EstablecimientoServicesImpl implements EstablecimientoServices {

	private final Map<Long, Establecimiento> ESTABLECIMIENTOS_DB = new HashMap<>();
	
	public EstablecimientoServicesImpl() {
		initData();
	}
	
	@Override
	public Long create(Establecimiento establecimiento) {

		if(establecimiento.getId() != null) {
			throw new IllegalStateException("Para crear un establecimiento la id ha de ser null");
		}
		
		Long id = System.currentTimeMillis();
		
		establecimiento.setId(id);
		
		ESTABLECIMIENTOS_DB.put(id, establecimiento);
	
		return id;
	}

	@Override
	public Optional<Establecimiento> read(Long idEstablecimiento) {
		return Optional.ofNullable(ESTABLECIMIENTOS_DB.get(idEstablecimiento));
	}

	@Override
	public void update(Establecimiento establecimiento) {
		
		Long id = establecimiento.getId();
		
		if(id == null) {
			throw new IllegalStateException("La id de establecimiento no puede ser null");
		}
		
		boolean existe = ESTABLECIMIENTOS_DB.containsKey(id);
		
		if(!existe) {
			throw new IllegalArgumentException("No existe el establecimiento con id [" + id + "]");
		}
		
		ESTABLECIMIENTOS_DB.replace(id, establecimiento);
		
	}

	@Override
	public List<Establecimiento> getAll() {
		return new ArrayList<>(ESTABLECIMIENTOS_DB.values());
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
	
	private void initData() {
		
		Direccion direccion1 = new Direccion();
		
		direccion1.setVia("Avda. Monforte de Lemos, 36");
		direccion1.setPoblacion("Madrid");
		direccion1.setCodigoPostal("28029");
		direccion1.setProvincia("Madrid");
		direccion1.setPais("España");
		
		Direccion direccion2 = new Direccion();
		
		direccion2.setVia("Avinguda de la Gran Via de l'Hospitalet, 75");
		direccion2.setPoblacion("Barcelona");
		direccion2.setCodigoPostal("08908");
		direccion2.setProvincia("Barcelona");
		direccion2.setPais("España");
		
		DatosContacto datosContacto1 = new DatosContacto();
		
		datosContacto1.setEmail("vaguada@donpollo.com");
		datosContacto1.setTelefono1("91 220 23 44");
		datosContacto1.setTelefono2(null);
		
		DatosContacto datosContacto2 = new DatosContacto();
		
		datosContacto2.setEmail("granvia2@donpollo.com");
		datosContacto2.setTelefono1("93 231 44 56");
		datosContacto2.setTelefono2("620908445");
		
		Establecimiento establecimiento1 = new Establecimiento();
		Establecimiento establecimiento2 = new Establecimiento();
		
		establecimiento1.setId(100L);
		establecimiento1.setNombre("DON POLLO - GRAN VIA 2");
		establecimiento1.setDireccion(direccion1);
		establecimiento1.setDatosContacto(datosContacto1);
		
		establecimiento2.setId(101L);
		establecimiento2.setNombre("DON POLLO - LA VAGUADA");
		establecimiento2.setDireccion(direccion2);
		establecimiento2.setDatosContacto(datosContacto2);
		
		ESTABLECIMIENTOS_DB.put(establecimiento1.getId(), establecimiento1);
		ESTABLECIMIENTOS_DB.put(establecimiento2.getId(), establecimiento2);
		
	}

}
