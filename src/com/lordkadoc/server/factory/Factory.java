package com.lordkadoc.server.factory;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.server.entities.Gun;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.entities.Zombie;

public class Factory {
	
	/*public static Projectile getProjectileForPlayer(Player player){
		Projectile projectile = new Projectile(player.getX(), player.getY(), 1);
		projectile.setDx(Math.cos(player.getAngle())*Constants.BULLET_SPEED);
		projectile.setDy(Math.sin(player.getAngle())*Constants.BULLET_SPEED);
		projectile.setAngle(player.getAngle());
		projectile.setDamage(Constants.PLAYER_DAMAGE);
		projectile.setOwner(player);
		projectile.setLifespan(Constants.BULLET_LIFESPAN);
		return projectile;
	}*/
	
	public static Gun createGun(Player player){
		Gun gun = new Gun("Standard Gun");
		gun.setPlayer(player);
		gun.setAmmos(Constants.GUN_AMMO);
		gun.setMagazineSize(Constants.GUN_MAGAZINE_CAPACITY);
		gun.setAmmosInMagazine(Constants.GUN_MAGAZINE_CAPACITY);
		gun.setDamage(Constants.GUN_DAMAGE);
		gun.setFireRate(Constants.GUN_FIRE_RATE);
		gun.setReloadTime(Constants.GUN_RELOAD_TIME);
		return gun;
	}
	
	public static Zombie createZombie(int x, int y){
		Zombie zombie = new Zombie(x, y, 2);
		zombie.setWidth(52);
		zombie.setHeight(52);
		zombie.setHealth(Constants.DEFAULT_ZOMBIE_HEALTH);
		zombie.setMaxHealth(Constants.DEFAULT_ZOMBIE_HEALTH);
		return zombie;
	}
	
	public static Player createPlayer(int x, int y, int skinId, String name){
		Player player = new Player(x, y, skinId, name);
		player.setWidth(52);
		player.setHeight(52);
		player.setHealth(Constants.DEFAULT_PLAYER_HEALTH);
		player.setMaxHealth(Constants.DEFAULT_PLAYER_HEALTH);
		player.setWeapon(Factory.createGun(player));
		return player;

	}

}
