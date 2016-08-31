package com.lordkadoc.server.entities;

import com.lordkadoc.commons.Constants;

public class Zombie extends LivingEntity{
	
	protected Player target;
	
	protected int damage;

	public Zombie(int x, int y, int skinId) {
		super(x, y, skinId);
		this.damage = Constants.DEFAULT_ZOMBIE_DAMAGE;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Player getTarget() {
		return target;
	}
	
	public void setTarget(Player target) {
		this.target = target;
	}
	
	public boolean hasTarget(){
		return target != null;
	}

}
