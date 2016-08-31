package com.lordkadoc.server.entities;

import com.lordkadoc.server.world.World;

public abstract class Weapon {
	
	protected Player player;
	
	protected String name;
	
	protected int damage;
	
	protected long reloadTime, fireRate;
	
	protected long currentReloadTime, currentFireTime;
	
	protected int ammos, magazineSize, ammosInMagazine;
	
	protected boolean reloading, fireInDelay;
	
	public Weapon(String name) {
		this.name = name;
		this.currentFireTime = 0;
		this.currentReloadTime = 0;
		this.reloading = false;
		this.fireInDelay = false;
	}
	
	public abstract void shoot(World world);
	
	public boolean canShoot(){
		return ammos > 0 && !reloading && !fireInDelay;
	}
	
	protected void reload(){
		reloading = true;
		currentReloadTime = reloadTime;
	}
	
	protected void shot(){
		fireInDelay = true;
		currentFireTime = fireRate;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public long getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(long reloadTime) {
		this.reloadTime = reloadTime;
	}

	public long getFireRate() {
		return fireRate;
	}

	public void setFireRate(long fireRate) {
		this.fireRate = fireRate;
	}

	public int getAmmos() {
		return ammos;
	}

	public void setAmmos(int ammos) {
		this.ammos = ammos;
	}

	public int getMagazineSize() {
		return magazineSize;
	}

	public void setMagazineSize(int magazineSize) {
		this.magazineSize = magazineSize;
	}

	public int getAmmosInMagazine() {
		return ammosInMagazine;
	}

	public void setAmmosInMagazine(int ammosInMagazine) {
		this.ammosInMagazine = ammosInMagazine;
	}

	public boolean isReloading() {
		return reloading;
	}

	public void setReloading(boolean reloading) {
		this.reloading = reloading;
	}

	public boolean isFireInDelay() {
		return fireInDelay;
	}

	public void setFireInDelay(boolean fireInDelay) {
		this.fireInDelay = fireInDelay;
	}
	
	public long getCurrentReloadTime() {
		return currentReloadTime;
	}
	
	public void setCurrentReloadTime(long currentReloadTime) {
		this.currentReloadTime = currentReloadTime;
	}
	
	public long getCurrentFireTime() {
		return currentFireTime;
	}
	
	public void setCurrentFireTime(long currentFireTime) {
		this.currentFireTime = currentFireTime;
	}

}
