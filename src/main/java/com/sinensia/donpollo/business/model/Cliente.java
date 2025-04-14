package com.sinensia.donpollo.business.model;

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
