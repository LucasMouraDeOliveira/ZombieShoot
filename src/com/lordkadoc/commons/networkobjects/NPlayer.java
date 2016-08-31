package com.lordkadoc.commons.networkobjects;

public class NPlayer extends NEntity{
	
	private String name;
	
	private int health;
	
	private int ammos;
	
	private int ammosInMagazine;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getAmmos() {
		return ammos;
	}
	
	public void setAmmos(int ammos) {
		this.ammos = ammos;
	}
	
	public int getAmmosInMagazine() {
		return ammosInMagazine;
	}
	
	public void setAmmosInMagazine(int ammosInMagazine) {
		this.ammosInMagazine = ammosInMagazine;
	}

}
