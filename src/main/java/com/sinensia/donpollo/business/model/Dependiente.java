package com.sinensia.donpollo.business.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="EMPLEADOS")
public class Dependiente extends Persona {

	private String licenciaManipuladorAlimentos;
	
	public Dependiente() {
		
	}
	
	public String getLicenciaManipuladorAlimentos() {
		return licenciaManipuladorAlimentos;
	}

	public void setLicenciaManipuladorAlimentos(String licenciaManipuladorAlimentos) {
		this.licenciaManipuladorAlimentos = licenciaManipuladorAlimentos;
	}

}
