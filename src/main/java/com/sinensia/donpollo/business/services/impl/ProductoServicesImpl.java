package com.sinensia.donpollo.business.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.sinensia.donpollo.business.config.BusinessException;
import com.sinensia.donpollo.business.model.Familia;
import com.sinensia.donpollo.business.model.Producto;
import com.sinensia.donpollo.business.model.dtos.Oferta;
import com.sinensia.donpollo.business.model.dtos.ProductoDTO1;
import com.sinensia.donpollo.business.model.dtos.ProductoDTO2;
import com.sinensia.donpollo.business.services.ProductoServices;
import com.sinensia.donpollo.integration.model.FamiliaPL;
import com.sinensia.donpollo.integration.model.ProductoPL;
import com.sinensia.donpollo.integration.repositories.ProductoPLRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServicesImpl implements ProductoServices {

	private final ProductoPLRepository productoPLRepository;
	private final DozerBeanMapper mapper;
	private List<Oferta> productosOferta = new ArrayList<>();
	
	public ProductoServicesImpl(ProductoPLRepository productoRepository, DozerBeanMapper mapper) {
		this.productoPLRepository = productoRepository;
		this.mapper = mapper;
	}
	 
	@Override
	@Transactional
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new BusinessException("Para crear un producto la id ha de ser null");
		}
		
		ProductoPL productoPL = mapper.map(producto, ProductoPL.class);
	
		return productoPLRepository.save(productoPL).getId();
	}

	@Override
	public Optional<Producto> read(Long idProducto) {
		
		return productoPLRepository.findById(idProducto).stream()
				.map(x -> mapper.map(x, Producto.class))
				.findAny();
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		if(id == null) {
			throw new BusinessException("La id del producto no puede ser null");
		}
		
		boolean existe = productoPLRepository.existsById(id);
		
		if(!existe) {
			throw new BusinessException("No existe el producto con id [" + id + "]");
		}
		
		productoPLRepository.save(mapper.map(producto, ProductoPL.class));
		
	}

	@Override
	@Transactional
	public void delete(Long idProducto) {
		
		if(idProducto == null) {
			throw new BusinessException("La id del producto no puede ser null");
		}
		
		boolean existe = productoPLRepository.existsById(idProducto);
		
		if(!existe) {
			throw new BusinessException("El producto [" + idProducto + "] no existe. No se puede eliminar.");
		}
		
		Optional<ProductoPL> optionalPL = productoPLRepository.findById(idProducto);
		
		optionalPL.get().setDescatalogado(true);
		
	}

	@Override
	public List<Producto> getAll() {
		return convertListFromIntegrationToBusiness(productoPLRepository.findAll());
	}

	@Override
	public List<Producto> getByFamilia(Familia familia) {
		return convertListFromIntegrationToBusiness(productoPLRepository.findByFamiliaId(familia.getId()));
	}

	@Override
	public List<Producto> getByPrecioBetween(double min, double max) {
		return convertListFromIntegrationToBusiness(productoPLRepository.findByPrecioBetweenOrderByPrecio(min, max));
	}

	@Override
	public List<Producto> getDescatalogados() {
		return convertListFromIntegrationToBusiness(productoPLRepository.findByDescatalogadoTrue());
	}

	@Override
	public List<Producto> getByFechaAltaBetween(Date desde, Date hasta) {
		return convertListFromIntegrationToBusiness(productoPLRepository.findByFechaAltaBetweenOrderByFechaAlta(desde, hasta));
	}

	@Override
	public int getNumeroTotalProductos() {
		return (int) productoPLRepository.count();
	}

	@Override
	public int getNumeroTotalProductosByFamilia(Familia familia) {
		
		FamiliaPL familiaPL = mapper.map(familia, FamiliaPL.class);
		
		ProductoPL ejemploProductoPL = new ProductoPL();
		ejemploProductoPL.setFamilia(familiaPL);
	
		return (int) productoPLRepository.count(Example.of(ejemploProductoPL));
		
	}

	@Override
	@Transactional
	public void incrementarPrecios(Familia familia, double porcentaje) {
		
		FamiliaPL familiaPL = mapper.map(familia, FamiliaPL.class);
		
		productoPLRepository.updatePrecios(familiaPL, porcentaje);
		
	}

	@Override
	@Transactional
	public void incrementarPrecios(List<Producto> productos, double porcentaje) {
		
		// TODO Buscar solición para esto!
		// Aqui la idea es NO tener un método específico en el repositorio. 
		// Aquí deberíamos poder utilizar el mismo método que ya tenemos en el reposirio.
		
		// Resumiendo: convertir List<Producto> en Long[]
		
		Long[] ids = {};
		
		productoPLRepository.updatePrecios(ids , porcentaje);	
		
		
	}

	@Override
	@Transactional
	public void incrementarPrecios(Long[] idsProducto, double porcentaje) {
		productoPLRepository.updatePrecios(idsProducto, porcentaje);	
	}

	// TODO Test unitario
	
	@Override
	public Map<Familia, Integer> getEstadisticaNumeroProductosByFamilia() {
		
		List<Object[]> resultados = productoPLRepository.getEstadisticaNumeroProductosByFamilia();
		
		Map<Familia, Integer> estadistica = new HashMap<>();
		
		resultados.forEach(resultado -> {	
			estadistica.put(mapper.map((FamiliaPL) resultado[0], Familia.class), ((Long) resultado[1]).intValue());
			
		});
		
		return estadistica;
	}

	// TODO Test unitario
	
	@Override
	public Map<Familia, Double> getEstadisticaPrecioMedioProductosByFamilia() {

		List<Object[]> resultados = productoPLRepository.getEstadisticaPrecioMedioProductosByFamilia();
		
		Map<Familia, Double> estadistica = new HashMap<>();

		resultados.forEach(resultado -> {	
			estadistica.put(mapper.map((FamiliaPL) resultado[0], Familia.class), ((Double) resultado[1]));
			
		});
		
		return estadistica;
	}
	
	// *******************************************************
	//
	// DTOs
	//
	// *******************************************************

	@Override
	public List<ProductoDTO1> getDTO1() {
		return productoPLRepository.getDTO1().stream()
				.map(fila -> {
					String nombre = (String) fila[0];
					Double precio = (Double) fila[1];	
					return new ProductoDTO1(nombre, precio);
				}).toList();
	}

	@Override
	public List<ProductoDTO2> getDTO2() {
		return productoPLRepository.getDTO2().stream()
				.map(fila -> {
					String nombre = (String) fila[0];
					String descripcion = (String) fila[1];	
					return new ProductoDTO2(nombre, descripcion);
				}).toList();
	}
	
	@Override
	public List<Oferta> getOfertas() {
		
		List<ProductoPL> productosPL = productoPLRepository.findAll();
		
		if(this.productosOferta.size() == 0 || Math.random() > 0.8) {
			generarOfertas(productosPL);
		}
		
		return productosOferta;
	}
	
	// *******************************************************
	//
	// Private Methods
	//
	// *******************************************************	
	
	private List<Producto> convertListFromIntegrationToBusiness(List<ProductoPL> productosPL){
		
		return productosPL.stream()
				.map(x -> mapper.map(x, Producto.class))
				.toList();
	}
	
	private void generarOfertas(List<ProductoPL> productosPL) {
		
		this.productosOferta.clear();
		
		Collections.shuffle(productosPL);
		
		for(int i = 0; i < 5; i++) {
			ProductoPL productoPL = productosPL.get(i);
			double nuevoPrecio = productoPL.getPrecio() - productoPL.getPrecio() * 0.20;
			nuevoPrecio = Math.round(nuevoPrecio * 100) / 100.0;
			Oferta oferta = new Oferta(productoPL.getId(), productoPL.getNombre(), productoPL.getFamilia().getNombre(), productoPL.getPrecio(), nuevoPrecio);
			this.productosOferta.add(oferta);
		}
		
	}
	
}
