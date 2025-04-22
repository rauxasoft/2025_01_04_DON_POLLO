package com.sinensia.donpollo.integration.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="PEDIDOS")
public class PedidoPL {

	@Id
	@Column(name="CODIGO")
	@GeneratedValue(generator = "PEDIDO_SEQ")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHora;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_CLIENTE")
	private ClientePL cliente;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPLEADO")
	private DependientePL dependiente;
	
	@ManyToOne
	@JoinColumn(name="ID_ESTABLECIMIENTO")
	private EstablecimientoPL establecimiento;
	
	@Enumerated(EnumType.STRING)
	private EstadoPedidoPL estado;
	
	@Column(name="COMENTARIO")
	private String observaciones;
	
	@ElementCollection(fetch =  FetchType.EAGER)
	@JoinTable(name = "LINEAS_PEDIDO", joinColumns = @JoinColumn(name="CODIGO_PEDIDO"))
	@OrderColumn(name="ORDEN")
	private List<LineaDetallePL> lineas;
	
	public PedidoPL() {
		
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

	public ClientePL getCliente() {
		return cliente;
	}

	public void setCliente(ClientePL cliente) {
		this.cliente = cliente;
	}

	public DependientePL getDependiente() {
		return dependiente;
	}

	public void setDependiente(DependientePL dependiente) {
		this.dependiente = dependiente;
	}

	public EstablecimientoPL getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(EstablecimientoPL establecimiento) {
		this.establecimiento = establecimiento;
	}

	public EstadoPedidoPL getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedidoPL estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<LineaDetallePL> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaDetallePL> lineas) {
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
		PedidoPL other = (PedidoPL) obj;
		return Objects.equals(id, other.id);
	}

}
