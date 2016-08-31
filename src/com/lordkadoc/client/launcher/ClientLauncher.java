package com.lordkadoc.client.launcher;

import java.io.IOException;
import java.util.Observable;

import javax.swing.JFrame;

import com.esotericsoftware.kryonet.Client;
import com.lordkadoc.client.loop.ClientGameLoop;
import com.lordkadoc.client.window.ClientMenu;
import com.lordkadoc.client.window.ClientWindow;
import com.lordkadoc.commons.ConnectionHandler;
import com.lordkadoc.commons.networkobjects.ConnectionAttempt;
import com.lordkadoc.commons.networkobjects.PlayerUpdate;

public class ClientLauncher extends Observable {

	protected Client client;

	protected ClientMenu clientMenu;

	protected ClientWindow clientWindow;

	protected ClientGameLoop gameLoop;

	protected JFrame frame;

	public ClientLauncher() {
		this.client = new Client(32768, 32768);
		this.gameLoop = new ClientGameLoop(50, this);
		this.clientWindow = new ClientWindow();
		this.clientMenu = new ClientMenu(this);
		this.addObserver(this.clientWindow);
	}

	public void start() {
		this.frame = new JFrame("Open world");
		frame.setContentPane(this.clientMenu);
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		this.client.start();
		ConnectionHandler.register(this.client.getKryo());
	}

	@Override
	public void notifyObservers(Object arg) {
		this.setChanged();
		super.notifyObservers(arg);
	}

	public static void main(String[] args) {
		new ClientLauncher().start();
	}

	public void sendUpdate() {
		PlayerUpdate playerUpdate = new PlayerUpdate();
		playerUpdate.setMove(clientWindow.getMove());
		playerUpdate.setAngle(clientWindow.getAngle());
		playerUpdate.setShooting(clientWindow.isShooting());
		client.sendTCP(playerUpdate);
	}

	public void connectToServer(String host, String port) {

		try {
			int p = Integer.valueOf(port);
			this.client.connect(5000, host, p);
			ConnectionAttempt connectionAttempt = new ConnectionAttempt();
			connectionAttempt.setLogin("Lucas");
			connectionAttempt.setSkinId(1);
			this.client.sendTCP(connectionAttempt);
			this.client.addListener(new ClientListener(this));
			this.frame.setContentPane(this.clientWindow);
			this.frame.validate();
			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
			this.gameLoop.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
