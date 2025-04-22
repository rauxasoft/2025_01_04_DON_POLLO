package com.sinensia.donpollo;

import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

public class ArquitecturaTest {

	@Test
	void los_servicios_deben_estar_en_el_paquete_services() {
		
		JavaClasses classes = new ClassFileImporter().importPackages("com.sinensia.donpollo.business.services.impl");
		
		ArchRule regla = Architectures.layeredArchitecture()
				
				
				.layer("Service").definedBy("..service..")
				.whereLayer("Service").mayOnlyBeAccessedByLayers("Controller");
		
		regla.check(classes);
		
	}
	
}
