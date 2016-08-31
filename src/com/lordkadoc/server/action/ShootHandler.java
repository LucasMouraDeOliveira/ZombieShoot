package com.lordkadoc.server.action;

import com.lordkadoc.server.entities.Player;

@Deprecated
public class ShootHandler implements ActionHandler{
	
	protected Player player;
	
	public ShootHandler(Player player) {
		this.player = player;
	}

	@Override
	public void handle() {
		//this.player.setShooting(true);
	}

}
