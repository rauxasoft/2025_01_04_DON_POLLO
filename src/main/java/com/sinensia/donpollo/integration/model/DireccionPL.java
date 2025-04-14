package com.sinensia.donpollo.integration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DireccionPL {

	@Column(name="DIRECCION")
	private String via;
	
	private String poblacion;
	private String codigoPostal;
	private String provincia;
	private String pais;
	
	public DireccionPL() {
		
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Direccion [via=" + via + ", poblacion=" + poblacion + ", codigoPostal=" + codigoPostal + ", provincia="
				+ provincia + ", pais=" + pais + "]";
	}

}
