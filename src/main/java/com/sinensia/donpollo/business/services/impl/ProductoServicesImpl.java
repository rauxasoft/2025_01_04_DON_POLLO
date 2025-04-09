package com.sinensia.donpollo.business.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.services.ProductoServices;
import com.sinensia.donpollo.integration.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServicesImpl implements ProductoServices {

	private final ProductoRepository productoRepository;
	
	public ProductoServicesImpl(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	 
	@Override
	@Transactional
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new BusinessException("Para crear un producto la id ha de ser null");
		}
		
		Producto createdProducto = productoRepository.save(producto);
		
		return createdProducto.getId();
	}

	@Override
	public Optional<Producto> read(Long idProducto) {
		return productoRepository.findById(idProducto);
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new BusinessException("La id del producto no puede ser null");
		}
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new BusinessException("No existe el producto con id [" + id + "]");
		}
		
		productoRepository.save(producto);
		
	}

	@Override
	public void delete(Long idProducto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Producto> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Producto> getByFamilia(Familia familia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getByPrecioBetween(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getDescatalogados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getByFechaAltaBetween(Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumeroTotalProductos() {
		return (int) productoRepository.count();
	}

	@Override
	public int getNumeroTotalProductosByFamilia(Familia familia) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void incrementarPrecios(Familia familia, double porcentaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incrementarPrecios(List<Producto> productos, double porcentaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incrementarPrecios(Long[] idsProducto, double porcentaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Familia, Integer> getEstadisticaNumeroProductosByFamilia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Familia, Double> getEstadisticaPrecioMedioProductosByFamilia() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
