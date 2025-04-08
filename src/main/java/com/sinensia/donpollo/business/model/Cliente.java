package com.sinensia.donpollo.business.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="CLIENTES")
public class Cliente extends Persona {

	private boolean gold;
	
	public Cliente() {
		
	}

	public boolean isGold() {
		return gold;
	}

	public void setGold(boolean gold) {
		this.gold = gold;
	}

}
