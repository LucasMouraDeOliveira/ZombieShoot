package com.lordkadoc.client.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lordkadoc.client.launcher.ClientLauncher;

public class ClientMenu extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1190504701979386745L;
	
	protected ClientLauncher client;
	
	protected JTextField host, port;
	
	protected JButton connect;
	
	public ClientMenu(ClientLauncher clientLauncher) {
		this.client = clientLauncher;
		this.initMenu();
	}

	private void initMenu() {
		this.host = new JTextField("25.108.139.129");
		this.port = new JTextField("42000");
		
		this.connect = new JButton("Connexion");
		this.connect.addActionListener(this);
		
		this.add(this.host);
		this.add(this.port);
		this.add(this.connect);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.connect)){
			client.connectToServer(this.host.getText(), this.port.getText());
		}
	}

}
