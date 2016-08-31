package com.lordkadoc.commons.networkobjects;

import java.util.List;

public class NWorld {
	
	private NCell[][] cells;
	
	private NPlayer currentPlayer;
	
	private List<NPlayer> players;
	
	private List<NZombie> zombies;
	
	private List<NProjectile> projectiles;
	
	public NWorld(){
		
	}
	
	public NCell[][] getCells() {
		return cells;
	}
	
	public void setCells(NCell[][] cells) {
		this.cells = cells;
	}
	
	public NPlayer getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(NPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public List<NPlayer> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<NPlayer> players) {
		this.players = players;
	}
	
	public List<NZombie> getZombies() {
		return zombies;
	}
	
	public void setZombies(List<NZombie> zombies) {
		this.zombies = zombies;
	}
	
	public List<NProjectile> getProjectiles() {
		return projectiles;
	}
	
	public void setProjectiles(List<NProjectile> projectiles) {
		this.projectiles = projectiles;
	}

}
