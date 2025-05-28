package com.sinensia.donpollo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint unathorizedHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector instrospector){
		return new MvcRequestMatcher.Builder(instrospector);
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		
		http.csrf(csrf -> csrf.disable())

		.exceptionHandling(exception -> exception.authenticationEntryPoint(unathorizedHandler))
		.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))      // para permitir el acceso a irames
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> 
		
				auth.requestMatchers("/donpollo/auth/signin/**").permitAll()					// Para permitir el acceso al servidor de autenticación
				    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()						// Permitir acceso a métodos OPTIONS (lo utilizan los navegadores)
				    .requestMatchers("/h2-console/**").permitAll()								// Para permitir el acceso a la consola H2
				    .requestMatchers("/familias/**").permitAll()								// Para permitir el acceso "/familias"
				//  .requestMatchers("/dependientes/**").permitAll()							// Para permitir el acceso "/dependientes"
				    .requestMatchers("/productos/**").permitAll()								// Para permitir el acceso "/productos"
				//  .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "(swagger-ui/**").permitAll()
				    
				    .anyRequest().authenticated()												// El resto de end-points requieren estar autenticado
				    
				);
		
		http.authenticationProvider(getAuthenticationProvider());
		
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	// *******************************************************
	//
	// Private Methdos
	//
	// *******************************************************
	
	private DaoAuthenticationProvider getAuthenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
}
