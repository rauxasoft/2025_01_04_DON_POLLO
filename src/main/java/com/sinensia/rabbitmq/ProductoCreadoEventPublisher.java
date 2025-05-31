package com.sinensia.rabbitmq;

import java.time.format.DateTimeFormatter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.sinensia.donpollo.business.model.Producto;

@Component
public class ProductoCreadoEventPublisher {
	
	private final RabbitTemplate rabbitTemplate;

    public ProductoCreadoEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarProductoCreado(Producto producto) {
        
    	ProductoCreadoEvent event = new ProductoCreadoEvent(
    		producto.getId(),
        	producto.getNombre(),
        	DateTimeFormatter.ISO_INSTANT.format(producto.getFechaAlta().toInstant()),
        	producto.getPrecio(),
        	producto.getDescripcion(),
        	producto.getFamilia().getNombre(),
        	producto.isDescatalogado()
    	);
    	
  
    	
   // 	rabbitTemplate.convertAndSend("productos.exchange", "productos.creado", json);

    	
    	rabbitTemplate.convertAndSend("productos.exchange", "productos.creado", event);
    	System.out.println("Evento publicado: " + event.nombre());
    
    }
}
