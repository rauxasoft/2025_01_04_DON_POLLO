package com.sinensia.donpollo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

public class MyArchitectureTest {
	
	@Test
	@Disabled
    public void las_implementaciones_de_business_estan_en_su_sitio() {
		
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.sinensia.donpollo");
    
        final ArchRule rule = classes().that()
        							.areAnnotatedWith(Service.class)
        							.and()
        							.haveNameMatching(".*ServicesImpl")
        							.should().resideInAPackage("com.sinensia.donpollo.business.services.impl");
    
        rule.check(importedClasses);
    }
	
	@Test
	@Disabled
    public void los_controladores_estan_en_su_sitio() {
		
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.sinensia.donpollo");
    
        final ArchRule rule1 = classes().that()
        								.areAnnotatedWith(RestController.class)
        								.should()
        								.haveNameMatching(".*Controller")
        								.andShould()
        								.resideInAPackage("..presentation.controllers");
        	
        final ArchRule rule2 = noClasses().that()
        								.resideInAnyPackage("..presentation..")
        								.should()
        								.dependOnClassesThat()
        								.resideInAPackage("..integration..");
    
        rule1.check(importedClasses);
        rule2.check(importedClasses);
    }
	
	@Test
	@Disabled
	public void no_hay_sysos() {
		
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.sinensia.donpollo");
		
		final ArchRule NO_SYS_STREAM = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
		
		NO_SYS_STREAM.check(importedClasses);
	}
	
	@Test
	@Disabled
	public void no_se_utiliza_logger_de_java_util() {
		
	
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.sinensia.donpollo");
		
		GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(importedClasses);
		
	}
	
	// Cómo asegurar que nuestra arquitectura está bien definida a nivel de paquetes?
	// Por ejemplo, al hacer empaquetdo por capas!
	
	// Capa PRESENTATION
	// Capa BUSINESS
	// Capa INTEGRATION
	
	// Puedo basarme en cualquier cosa:
	// - si la clase tiene tal o cual prefijo o sufijo o nombre
	// - si la clase implementa tal interface o extiende de tal clase
	// - si la clase tiene tal o cual anotación
	// - si la clase está en tal o cual paquete...
	
	@Test
	@Disabled
	public void arquitectura_por_capas() {
		
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.sinensia.donpollo");
		
		LayeredArchitecture arch = Architectures.layeredArchitecture()
				
				.consideringAllDependencies()
				
				.layer("Presentation").definedBy("..presentation..")
				.layer("Business").definedBy("..business..")
				.layer("Integration").definedBy("..integration..")
				
				.whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
				.whereLayer("Business").mayOnlyBeAccessedByLayers("Presentation")
				.whereLayer("Integration").mayOnlyBeAccessedByLayers("Business");
		
		arch.check(importedClasses);
	}
	
}
