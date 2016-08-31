package com.lordkadoc.server.loop;

import java.util.Iterator;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.server.ServerInstance;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.utils.Movement;
import com.lordkadoc.server.world.World;

public class UpdatePlayers extends GameLoopOperation{
	
	protected ServerInstance serverInstance;

	public UpdatePlayers(long delay, ServerInstance serverInstance) {
		super(delay);
		this.serverInstance = serverInstance;
	}

	@Override
	protected void update() {
		World world = serverInstance.getWorld();
		Iterator<Player> players = world.getPlayers().iterator();
		Player player;
		while(players.hasNext()){
			player = players.next();
			if(!player.isActive()){
				players.remove();
				// + message pour informer le client qu'il est mort
			}else{
				if(!player.isAlive()){
					player.setActive(false);
				}else{
					new Movement().move2(player, Constants.PLAYER_TOP_SPEED, world);
					if(player.isShooting()){
						if(player.getWeapon().canShoot()){
							player.getWeapon().shoot(world);
						}
						player.setShooting(false);
					}
				}
			}
		}
	}

}
