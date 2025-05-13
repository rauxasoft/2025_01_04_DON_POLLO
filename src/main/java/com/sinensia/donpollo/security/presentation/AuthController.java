package com.sinensia.donpollo.security.presentation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinensia.donpollo.common.presentation.ErrorResponse;
import com.sinensia.donpollo.security.JwtUtils;
import com.sinensia.donpollo.security.UserDetailsImpl;

@RestController
@RequestMapping("donpollo/auth")
@CrossOrigin
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
		
		Authentication authentication = null;
		
		try {
			
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			
		} catch (Exception e) {
			
			logger.error("Error de autenticación para elusuario {}", loginRequest.getUsername());
			return new ResponseEntity<>(new ErrorResponse("Bad Credentials"), HttpStatus.UNAUTHORIZED);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.toList();
		
		JwtResponse jwtResponse = new JwtResponse(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
				
		return ResponseEntity.ok(jwtResponse);
	}
}
