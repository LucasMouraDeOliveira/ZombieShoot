package com.lordkadoc.server.loop;

import java.util.Iterator;
import java.util.List;

import com.lordkadoc.server.ServerInstance;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.entities.Projectile;
import com.lordkadoc.server.entities.Zombie;
import com.lordkadoc.server.world.Cell;
import com.lordkadoc.server.world.World;

public class UpdateProjectiles extends GameLoopOperation {
	
	protected ServerInstance serverInstance;

	public UpdateProjectiles(long delay, ServerInstance serverInstance) {
		super(delay);
		this.serverInstance = serverInstance;
	}

	@Override
	protected void update() {
		World world = serverInstance.getWorld();
		Iterator<Projectile> projectiles = world.getProjectiles().iterator();
		Projectile projectile;
		while(projectiles.hasNext()){
			projectile = projectiles.next();
			if(!projectile.isActive()){
				projectiles.remove();
			}else{
				projectile.move();
				checkCollisionWithMap(projectile, world);
				checkCollisionWithPlayers(projectile, world.getPlayers());
				checkCollisionWithZombies(projectile, world.getZombies());
				projectile.setLifespan(projectile.getLifespan()-this.getDelay());
				if(projectile.getLifespan() <= 0){
					projectile.setActive(false);
				}
			}
		}
	}
	
	private void checkCollisionWithMap(Projectile projectile, World world){
		Cell cell = world.getEntityCurrentCell(projectile);
		if(!cell.isCrossable()){
			projectile.setActive(false);
		}
	}
	
	private void checkCollisionWithPlayers(Projectile projectile, List<Player> players){
		for(Player player : players){
			if(!projectile.isActive())
				return;
			if( !player.equals(projectile.getOwner()) && projectile.collides(player)){
				projectile.setActive(false);
				player.hit(projectile.getDamage());
				return;
			}
		}
	}
	
	private void checkCollisionWithZombies(Projectile projectile, List<Zombie> zombies){
		for(Zombie zombie : zombies){
			if(!projectile.isActive())
				return;
			if(projectile.collides(zombie)){
				projectile.setActive(false);
				zombie.hit(projectile.getDamage());
				return;
			}
		}
	}

}
