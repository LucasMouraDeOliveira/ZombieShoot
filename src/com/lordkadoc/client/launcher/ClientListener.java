package com.lordkadoc.client.launcher;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.lordkadoc.commons.networkobjects.NWorld;

public class ClientListener extends Listener {
	
	protected ClientLauncher clientLauncher;
	
	public ClientListener(ClientLauncher clientLauncher) {
		this.clientLauncher = clientLauncher;
	}
	
	@Override
	public void received(Connection connection, Object message) {
		if(message instanceof NWorld){
			clientLauncher.notifyObservers(message);
		}
	}

}
