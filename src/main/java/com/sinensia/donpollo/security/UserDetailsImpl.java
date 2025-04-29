package com.sinensia.donpollo.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinensia.donpollo.security.integration.model.UsuarioPL;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 176767577687L;
	
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private boolean enabled;
	private Collection<? extends GrantedAuthority> authorities;
	
	@JsonIgnore
	private String password;
	
	private UserDetailsImpl(Long id, 
						   String username, 
						   String firstName, 
						   String lastName, 
						   String email, 
						   boolean enabled,
						   Collection<? extends GrantedAuthority> authorities, 
						   String password) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.enabled = enabled;
		this.authorities = authorities;
		this.password = password;
	}

	public static UserDetailsImpl build(UsuarioPL usuarioPL) {
		
		List<GrantedAuthority> roles = usuarioPL.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(usuarioPL.getId(), 
								   usuarioPL.getUsername(), 
								   usuarioPL.getFirstName(), 
								   usuarioPL.getLastName(),
								   usuarioPL.getEmail(),
								   usuarioPL.getEnabled(),
								   roles,
								   usuarioPL.getPassword());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
	
}
