package com.lordkadoc.client.loop;

import com.lordkadoc.client.launcher.ClientLauncher;

public class ClientGameLoop extends Thread{
	
	protected ClientLauncher client;
	
	protected int delay;
	
	public ClientGameLoop(int delay, ClientLauncher client) {
		this.delay = delay;
		this.client = client;
	}
	
	@Override
	public void run(){
		while(true){
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.client.notifyObservers("update");
			this.client.sendUpdate();
			
		}
	}

}
