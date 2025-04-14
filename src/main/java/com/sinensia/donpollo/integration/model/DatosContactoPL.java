package com.sinensia.donpollo.integration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DatosContactoPL {

	@Column(name="TELEFONO")
	private String telefono1;
	
	@Column(name="MOVIL")
	private String telefono2;
	
	private String email;
	
	public DatosContactoPL() {
		
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "DatosContacto [telefono1=" + telefono1 + ", telefono2=" + telefono2 + ", email=" + email + "]";
	}
	
}
