package com.sinensia.donpollo.config;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {

	@Bean(name="mapper")
	DozerBeanMapper getMapper() {
		
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		
		List<String> mapingFiles = List.of("dozer-configuration-mappings.xml");
		
		dozerBeanMapper.setMappingFiles(mapingFiles);
		
		return dozerBeanMapper;
	}
	
}
