package com.sinensia.donpollo.business.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Pedido {

	private Long id;
	private Date fechaHora;
	private Cliente cliente;
	private Dependiente dependiente;
	private Establecimiento establecimiento;
	private EstadoPedido estado;
	private String observaciones;
	private List<LineaDetalle> lineas;
	
	public Pedido() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Dependiente getDependiente() {
		return dependiente;
	}

	public void setDependiente(Dependiente dependiente) {
		this.dependiente = dependiente;
	}

	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<LineaDetalle> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaDetalle> lineas) {
		this.lineas = lineas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}

}
