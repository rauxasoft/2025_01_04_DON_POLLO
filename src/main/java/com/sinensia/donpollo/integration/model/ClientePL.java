package com.sinensia.donpollo.integration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="CLIENTES")
public class ClientePL extends PersonaPL {

	private boolean gold;
	
	public ClientePL() {
		
	}

	public boolean isGold() {
		return gold;
	}

	public void setGold(boolean gold) {
		this.gold = gold;
	}

}
