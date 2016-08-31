package com.lordkadoc.server.loop;

import java.util.Iterator;

import com.lordkadoc.server.ServerInstance;
import com.lordkadoc.server.entities.Zombie;
import com.lordkadoc.server.utils.TargetManager;
import com.lordkadoc.server.world.Cell;
import com.lordkadoc.server.world.World;

public class UpdateZombies extends GameLoopOperation {
	
	protected ServerInstance serverInstance;

	public UpdateZombies(long delay, ServerInstance serverInstance) {
		super(delay);
		this.serverInstance = serverInstance;
	}

	@Override
	protected void update() {
		World world = serverInstance.getWorld();
		Iterator<Zombie> zombies = world.getZombies().iterator();
		Zombie zombie;
		while(zombies.hasNext()){
			zombie = zombies.next();
			if(!zombie.isActive()){
				zombies.remove();
			}else{
				if(!zombie.isAlive()){
					zombie.setActive(false);
					//TODO temps d'animation de mort (ne pas le passer à active false tout de suite)
				}else{
					//update zombie
					if(!zombie.hasTarget()){
						TargetManager.getInstance().setTarget(zombie, world);
					}else{
						//System.out.println(world.getEntityCurrentCell(zombie).getX() + "/" + world.getEntityCurrentCell(zombie).getY());
						Cell nextCell = TargetManager.getInstance().nextTargetCell(zombie, world);
						if(nextCell != null){
							TargetManager.getInstance().moveZombieTo(world, zombie, nextCell);
						}
					}
				}
			}
		}
	}

}
