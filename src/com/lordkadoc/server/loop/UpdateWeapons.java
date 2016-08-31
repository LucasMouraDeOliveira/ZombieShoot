package com.lordkadoc.server.loop;

import com.lordkadoc.server.ServerInstance;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.entities.Weapon;
import com.lordkadoc.server.world.World;

public class UpdateWeapons extends GameLoopOperation {
	
	protected ServerInstance serverInstance;

	public UpdateWeapons(long delay, ServerInstance serverInstance) {
		super(delay);
		this.serverInstance = serverInstance;
	}

	@Override
	protected void update() {
		World world = serverInstance.getWorld();
		Weapon weapon;
		for(Player player : world.getPlayers()){
			weapon = player.getWeapon();
			if(weapon.isReloading()){
				weapon.setCurrentReloadTime(weapon.getCurrentReloadTime()-this.delay);
				if(weapon.getCurrentReloadTime() <= 0){
					weapon.setReloading(false);
					weapon.setAmmosInMagazine(weapon.getMagazineSize());
				}
			}
			if(weapon.isFireInDelay()){
				weapon.setCurrentFireTime(weapon.getCurrentFireTime()-this.delay);
				if(weapon.getCurrentFireTime() <= 0){
					weapon.setFireInDelay(false);
				}
			}
		}
	}

}
