package com.lordkadoc.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.lordkadoc.commons.networkobjects.ConnectionAttempt;
import com.lordkadoc.commons.networkobjects.PlayerUpdate;
import com.lordkadoc.server.factory.NFactory;

public class ServerListener extends Listener {
	
	protected ServerInstance serverInstance;
	
	public ServerListener(ServerInstance serverInstance){
		this.serverInstance = serverInstance;
	}
	
	@Override
	public void disconnected(Connection connection){   
		serverInstance.disconnectPlayer(connection);
	}
	
	@Override
	public void received(Connection connection, Object message){
		if(message instanceof ConnectionAttempt){
			if(serverInstance.tryConnection(connection, (ConnectionAttempt)message)){
				//renvoyer message connexion acceptée
				serverInstance.sendMessage(connection, NFactory.getNWorld(serverInstance.getWorld()));
			}else{
				//renvoyer message connexion refusée
			}
		}else if(message instanceof PlayerUpdate){
			serverInstance.handlePlayerUpdate(connection, (PlayerUpdate)message);
		}
	}

}
