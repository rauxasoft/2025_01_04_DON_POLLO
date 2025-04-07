package com.sinensia.donpollo.business.services.dummy.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.services.ProductoServices;

@Service
@Primary
public class ProductoServicesDummyImpl implements ProductoServices {

	private final Map<Long, Producto> PRODUCTOS_DB = new HashMap<>();
	
	public ProductoServicesDummyImpl() {
		initData();
	}
	
	@Override
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new BusinessException("Para crear un producto la id ha de ser null");
		}
		
		Long id = System.currentTimeMillis();
		
		producto.setId(id);
		
		PRODUCTOS_DB.put(id, producto);
	
		return id;
	}

	@Override
	public Optional<Producto> read(Long idProducto) {
		return Optional.ofNullable(PRODUCTOS_DB.get(idProducto));
	}

	@Override
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new BusinessException("La id del producto no puede ser null");
		}
		
		boolean existe = PRODUCTOS_DB.containsKey(id);
		
		if(!existe) {
			throw new BusinessException("No existe el producto con id [" + id + "]");
		}
		
		PRODUCTOS_DB.replace(id, producto);
			
	}

	@Override
	public void delete(Long idProducto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Producto> getAll() {
		return new ArrayList<>(PRODUCTOS_DB.values());
	}

	@Override
	public List<Producto> getByFamilia(Familia familia) {
		
		return PRODUCTOS_DB.values().stream()
				.filter(producto -> producto.getFamilia().equals(familia))
				.toList();
	}

	@Override
	public List<Producto> getByPrecioBetween(double min, double max) {
		
		return PRODUCTOS_DB.values().stream()
				.filter(producto -> producto.getPrecio() >= min && producto.getPrecio() <= max)
				.toList();
	}

	@Override
	public List<Producto> getDescatalogados() {
		
		return PRODUCTOS_DB.values().stream()
				.filter(producto -> producto.isDescatalogado())
				.toList();
	}

	@Override
	public List<Producto> getByFechaAltaBetween(Date desde, Date hasta) {
		
		return PRODUCTOS_DB.values().stream()
				.filter(producto -> producto.getFechaAlta().after(desde) && producto.getFechaAlta().before(hasta))
				.toList();
	}

	@Override
	public int getNumeroTotalProductos() {

		return PRODUCTOS_DB.size();
	}

	@Override
	public int getNumeroTotalProductosByFamilia(Familia familia) {
		
		return (int) PRODUCTOS_DB.values().stream()
			.filter(producto -> producto.getFamilia().equals(familia))
			.count();	
	}

	@Override
	public void incrementarPrecios(Familia familia, double porcentaje) {
	
		PRODUCTOS_DB.values().stream()
			.filter(producto -> producto.getFamilia().equals(familia))
			.forEach(producto -> {
				double precio = producto.getPrecio();
				double nuevoPrecio = precio + (precio * porcentaje) / 100;
				producto.setPrecio(nuevoPrecio);
				PRODUCTOS_DB.replace(producto.getId(), producto);
			});	
	}

	@Override
	public void incrementarPrecios(List<Producto> productos, double porcentaje) {
		
		productos.stream().forEach(producto -> {
			double precio = producto.getPrecio();
			double nuevoPrecio = precio + (precio * porcentaje) / 100;
			producto.setPrecio(nuevoPrecio);
			PRODUCTOS_DB.replace(producto.getId(), producto);
		});
		
	}

	@Override
	public void incrementarPrecios(Long[] idsProducto, double porcentaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Familia, Integer> getEstadisticaNumeroProductosByFamilia() {
		
		Map<Familia, Integer> estadistica = new HashMap<>();
		
		PRODUCTOS_DB.values().stream().forEach(producto -> {
			
			Familia familia = producto.getFamilia();
			
			if (estadistica.containsKey(familia)) {
				int cantidad = estadistica.get(familia) + 1;
				estadistica.replace(familia, cantidad);
			} else {
				estadistica.put(familia, 1);
			}
		});
		
		return estadistica;
	}
	
	@Override
	public Map<Familia, Double> getEstadisticaPrecioMedioProductosByFamilia() {
		
		Map<Familia, Integer> estadisticaNumeroProductos = new HashMap<>();
		Map<Familia, Double> estadisticaPrecios = new HashMap<>();
		
		PRODUCTOS_DB.values().forEach(producto -> {
			
			Familia familia = producto.getFamilia();
			
			if (estadisticaNumeroProductos.containsKey(familia)) {
				int cantidad = estadisticaNumeroProductos.get(familia) + 1;
				double precioAcumulado = estadisticaPrecios.get(familia) + producto.getPrecio();
				estadisticaNumeroProductos.replace(familia, cantidad);
				estadisticaPrecios.replace(familia, precioAcumulado);
			} else {
				estadisticaNumeroProductos.put(familia, 1);
				estadisticaPrecios.put(familia, producto.getPrecio());
			}
		});
		
		estadisticaPrecios.forEach((familia, precioAcumulado) -> {
			
			int numeroProductos = estadisticaNumeroProductos.get(familia);
			double precioMedio = Math.round(precioAcumulado / numeroProductos * 100.0) / 100.0;
			estadisticaPrecios.replace(familia, precioMedio);
		});
		
		return estadisticaPrecios;
	}
		
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
	
	private void initData() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date fecha1 = null;
		Date fecha2 = null;
		Date fecha3 = null;
		Date fecha4 = null;
		Date fecha5 = null;
		
		try {
			fecha1 = sdf.parse("23/08/2023");
			fecha2 = sdf.parse("24/08/2023");
			fecha3 = sdf.parse("27/08/2023");
			fecha4 = sdf.parse("30/08/2023");
			fecha5 = sdf.parse("10/09/2023");
		} catch (ParseException e) {
			
		}
		
		Familia familia1 = new Familia();
		Familia familia2 = new Familia();
		
		familia1.setId(10L);
		familia2.setId(20L);
		
		familia1.setNombre("LICORES");
		familia2.setNombre("TAPAS");
		
		Producto producto1 = new Producto();
		Producto producto2 = new Producto();
		Producto producto3 = new Producto();
		Producto producto4 = new Producto();
		Producto producto5 = new Producto();
		
		producto1.setId(100L);
		producto2.setId(101L);
		producto3.setId(102L);
		producto4.setId(103L);
		producto5.setId(104L);
		
		producto1.setNombre("Copa de Oban");
		producto1.setDescripcion("Delicioso whisky escocés");
		producto1.setFamilia(familia1);
		producto1.setPrecio(17.20);
		producto1.setDescatalogado(false);
		producto1.setFechaAlta(fecha1);
		
		producto2.setNombre("Bomba de la Barceloneta");
		producto2.setDescripcion("Deliciosa patata con carne rebozada. Muy picante!");
		producto2.setFamilia(familia2);
		producto2.setPrecio(8.50);
		producto2.setDescatalogado(false);
		producto2.setFechaAlta(fecha2);
		
		producto3.setNombre("Cachopo");
		producto3.setDescripcion("Auténtico cachopo asturiano");
		producto3.setFamilia(familia2);
		producto3.setPrecio(11.00);
		producto3.setDescatalogado(false);
		producto3.setFechaAlta(fecha3);
		
		producto4.setNombre("Anís de el Mono");
		producto4.setDescripcion("El delicioso y aromático anís de Badalona");
		producto4.setFamilia(familia1);
		producto4.setPrecio(6.45);
		producto4.setDescatalogado(true);
		producto4.setFechaAlta(fecha4);
		
		producto5.setNombre("Patatas Bravas");
		producto5.setDescripcion("Patatas bravas de verdad. Muy picantes! (no nos hacemos responsables)");
		producto5.setFamilia(familia2);
		producto5.setPrecio(6.00);
		producto5.setDescatalogado(false);
		producto5.setFechaAlta(fecha5);
		
		PRODUCTOS_DB.put(producto1.getId(), producto1);
		PRODUCTOS_DB.put(producto2.getId(), producto2);
		PRODUCTOS_DB.put(producto3.getId(), producto3);
		PRODUCTOS_DB.put(producto4.getId(), producto4);
		PRODUCTOS_DB.put(producto5.getId(), producto5);
			
	}

}
