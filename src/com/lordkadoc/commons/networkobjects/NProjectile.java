package com.lordkadoc.commons.networkobjects;

public class NProjectile {
	
	private int x, y;
	
	private double angle;
	
	private int skinId;
	
	public NProjectile() {
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
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

}
