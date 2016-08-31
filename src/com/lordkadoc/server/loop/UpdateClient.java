package com.lordkadoc.server.loop;

import com.esotericsoftware.kryonet.Connection;
import com.lordkadoc.server.ServerInstance;
import com.lordkadoc.server.factory.NFactory;

public class UpdateClient extends GameLoopOperation {
	
	protected ServerInstance serverInstance;

	public UpdateClient(long delay, ServerInstance serverInstance) {
		super(delay);
		this.serverInstance = serverInstance;
	}

	@Override
	protected void update() {
		
		/*World world = serverInstance.getWorld();
		int i = (int)(Math.random()*world.getWidth());
		int j = (int)(Math.random()*world.getHeight());
		
		world.getCell(i, j).setGameObject(ObjectPool.getInstance().getObject(1));*/
		
		for(Connection connection : serverInstance.getPlayers().keySet()){
			serverInstance.sendMessage(connection, NFactory.getNWorldForPlayer(serverInstance.getWorld(), serverInstance.getPlayers().get(connection)));
		}
	}

}
