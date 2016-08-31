package com.lordkadoc.commons.networkobjects;

public class PlayerUpdate {
	
	private boolean move[];
	
	private boolean shooting;
	
	private double angle;
	
	public PlayerUpdate() {

	}
	
	public boolean[] getMove() {
		return move;
	}
	
	public void setMove(boolean[] move) {
		this.move = move;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public boolean isShooting() {
		return shooting;
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
}
