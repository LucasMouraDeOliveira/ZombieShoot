package com.lordkadoc.server.entities;

import java.awt.geom.Point2D;

public abstract class Entity {
	
	protected double x, y;
	
	protected double dx, dy;
	
	protected double angle;
	
	protected int skinId;
	
	protected boolean active;
	
	public Entity(double x, double y, int skinId) {
		this.x = x;
		this.y = y;
		this.angle = 0;
		this.skinId = skinId;
		this.active = true;
	}
	
	public boolean collides(Entity entity){
		return false;
	}
	
	public boolean collides(LivingEntity entity){
		Point2D.Double coordinates = new Point2D.Double(x, y);
		return entity.getHitbox().contains(coordinates);
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public int getSkinId() {
		return skinId;
	}
	
	public void setSkinId(int skinId) {
		this.skinId = skinId;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}
	
	public void setDx(double dx) {
		this.dx = dx;
	}
	
	public void setDy(double dy) {
		this.dy = dy;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
}
