package com.lordkadoc.server.entities;

import java.awt.geom.Rectangle2D;

public abstract class LivingEntity extends Entity{
	
	protected int width, height;
	
	protected int health, maxHealth;
	
	protected int speedX, speedY;

	public LivingEntity(double x, double y, int skinId) {
		super(x, y, skinId);
		this.speedX = 0;
		this.speedY = 0;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void hit(int hitpoints){
		this.health = Math.max(0, this.health-hitpoints);
	}
	
	public void heal(int hitpoints){
		this.health = Math.min(this.maxHealth, this.health+hitpoints);
	}
	
	public boolean isAlive(){
		return this.health > 0;
	}
	
	public Rectangle2D.Double getHitbox(){
		return new Rectangle2D.Double(x-width/2, y-height/2, width, height);
	}
	
	@Override
	public boolean collides(Entity entity){
		return entity.collides(this);
	}
	
	@Override
	public boolean collides(LivingEntity entity){
		return this.getHitbox().intersects(entity.getHitbox());
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
