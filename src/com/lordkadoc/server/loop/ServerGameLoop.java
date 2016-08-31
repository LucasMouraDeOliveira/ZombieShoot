package com.lordkadoc.server.loop;

import java.util.ArrayList;
import java.util.List;

import com.lordkadoc.server.ServerInstance;

public class ServerGameLoop extends Thread {
	
	protected long delay;
	
	protected ServerInstance serverInstance;
	
	protected List<GameLoopOperation> gameLoopOperations;

	public ServerGameLoop(int delay, ServerInstance serverInstance) {
		this.delay = delay;
		this.serverInstance = serverInstance;
		this.initGameLoopOperations();
	}
	
	public void initGameLoopOperations(){
		this.gameLoopOperations = new ArrayList<GameLoopOperation>();
		this.gameLoopOperations.add(new UpdatePlayers(50, serverInstance));
		this.gameLoopOperations.add(new UpdateZombies(50, serverInstance));
		this.gameLoopOperations.add(new UpdateWeapons(50, serverInstance));
		this.gameLoopOperations.add(new UpdateProjectiles(50, serverInstance));
		this.gameLoopOperations.add(new UpdateClient(50, serverInstance));
	}
	
	@Override
	public void run(){
		long start;
		long end;
		while(serverInstance.isRunning()){
			start = System.currentTimeMillis();
			update(delay);
			end = System.currentTimeMillis();
			//System.out.println(end-start);
			if(delay-(end-start)>0){				
				try {
					Thread.sleep(Math.max(0, delay-(end-start)));
				} catch (InterruptedException e) {}
			}
		}
	}
	
	private void update(long delay){
		for(GameLoopOperation gameLoopOperation : gameLoopOperations){
			gameLoopOperation.trigger(delay);
		}
	}

}
