package com.lordkadoc.server.entities;

public class Projectile extends Entity{
	
	private int damage;
	
	private Player owner;
	
	private long lifespan;
	
	public Projectile(double x, double y, int skinId) {
		super(x, y, skinId);
	}
	
	public void move(){
		this.x+=dx;
		this.y+=dy;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public long getLifespan() {
		return lifespan;
	}
	
	public void setLifespan(long lifespan) {
		this.lifespan = lifespan;
	}

}