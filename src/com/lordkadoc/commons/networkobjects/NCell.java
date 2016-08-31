package com.lordkadoc.commons.networkobjects;

public class NCell {
	
	private int x, y;
	
	private int biome;
	
	private NGameObject gameObject;
	
	public NCell() {
		
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
	
	public int getBiome() {
		return biome;
	}
	
	public void setBiome(int biome) {
		this.biome = biome;
	}
	
	public NGameObject getGameObject() {
		return gameObject;
	}
	
	public void setGameObject(NGameObject gameObject) {
		this.gameObject = gameObject;
	}

}
