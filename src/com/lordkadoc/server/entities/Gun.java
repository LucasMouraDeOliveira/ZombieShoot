package com.lordkadoc.server.entities;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.server.world.World;

public class Gun extends Weapon{

	public Gun(String name) {
		super(name);
	}

	@Override
	public void shoot(World world) {
		Projectile projectile = new Projectile(player.getX(), player.getY(), 1);
		projectile.setDx(Math.cos(player.getAngle())*Constants.BULLET_SPEED);
		projectile.setDy(Math.sin(player.getAngle())*Constants.BULLET_SPEED);
		projectile.setAngle(player.getAngle());
		projectile.setDamage(damage);
		projectile.setOwner(player);
		projectile.setLifespan(Constants.BULLET_LIFESPAN);
		
		world.addProjectile(projectile);
		
		ammosInMagazine--;
		ammos--;
		
		if(ammosInMagazine == 0){
			reload();
		}
		shot();
		
	}

}
