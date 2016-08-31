package com.lordkadoc.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.lordkadoc.commons.ConnectionHandler;
import com.lordkadoc.commons.networkobjects.ConnectionAttempt;
import com.lordkadoc.commons.networkobjects.PlayerUpdate;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.factory.Factory;
import com.lordkadoc.server.loop.ServerGameLoop;
import com.lordkadoc.server.world.World;

public class ServerInstance {
	
	public final static int TCP_PORT = 42000;
	
	protected Server server;
	
	protected int maxPlayers;
	
	protected Map<Connection, Player> players;
	
	protected World world;
	
	protected ServerGameLoop gameLoop;
	
	public ServerInstance(int maxPlayers) {
		this.maxPlayers = maxPlayers;
		this.players = new HashMap<Connection, Player>();
		this.initWorld();
		this.initGameLoop();
	}
	
	public void initWorld(){
		this.world = new World(30, 30);
	}
	
	public void initGameLoop(){
		this.gameLoop = new ServerGameLoop(50,this);
	}
	
	public void startClientConnection(){
		//Taille du buffer d'écriture augmentée pour envoyer une carte plus grande
		this.server = new Server(32768,32768);
		ConnectionHandler.register(this.server.getKryo());
		this.server.start();
		try {
			this.server.bind(TCP_PORT);
			this.server.addListener(new ServerListener(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startGameLoop(){
		this.gameLoop.start();
	}
	
	public boolean isRunning(){
		return true;
	}

	public boolean tryConnection(Connection connection, ConnectionAttempt message) {
		if(players.size() < maxPlayers){
			Player player = Factory.createPlayer(100, 100, message.getSkinId(), message.getLogin());
			players.put(connection, player);
			world.addPlayer(player);
			return true;			
		}
		return false;
	}
	
	public void disconnectPlayer(Connection connection){
		Player player = players.get(connection);
		if(player != null){
			world.removePlayer(player);
			players.remove(connection);
		}
	}
	
	public Map<Connection, Player> getPlayers(){
		return players;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void sendMessage(Connection connection, Object message){
		connection.sendTCP(message);
	}

	public void handlePlayerUpdate(Connection connection, PlayerUpdate playerUpdate) {
		Player player = players.get(connection);
		if(player != null){
			player.handle(playerUpdate);
		}
	}
	
	public static void main(String[] args) {
		ServerInstance test = new ServerInstance(10);
		test.startClientConnection();
		test.startGameLoop();
	}
	
}