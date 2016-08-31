package com.lordkadoc.server.world;

import com.lordkadoc.server.objects.GameObject;

public class Cell {
	
	protected final int x, y;
	
	protected GameObject gameObject;
	
	protected int biome;
	
	public Cell(int x, int y) {
		this(x, y, 0);
	}
	
	public Cell(int x, int y, int biome){
		this.x = x;
		this.y = y;
		this.biome = biome;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public GameObject getGameObject(){
		return gameObject;
	}
	
	public void setGameObject(GameObject gameObject){
		this.gameObject = gameObject;
	}
	
	public boolean isOccupied(){
		return gameObject != null;
	}
	
	public boolean isCrossable(){
		return !this.isOccupied() || this.gameObject.isCrossable();
	}
	
	public int getBiome() {
		return biome;
	}
	
	public void setBiome(int biome) {
		this.biome = biome;
	}

}