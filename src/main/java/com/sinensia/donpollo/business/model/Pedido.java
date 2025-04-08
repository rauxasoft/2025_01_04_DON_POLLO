package com.sinensia.donpollo.business.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="PEDIDOS")
public class Pedido {

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHora;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_CLIENTE")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPLEADO")
	private Dependiente dependiente;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTABLECIMIENTO")
	private Establecimiento establecimiento;
	
	@Enumerated(EnumType.STRING)
	private EstadoPedido estado;
	
	@Column(name="COMENTARIO")
	private String observaciones;
	
	@ElementCollection(fetch =  FetchType.EAGER)
	@JoinTable(name = "LINEAS_PEDIDO", joinColumns = @JoinColumn(name="CODIGO_PEDIDO"))
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
