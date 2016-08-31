package com.lordkadoc.server.action;

import com.lordkadoc.server.entities.Player;

@Deprecated
public class MoveHandler implements ActionHandler {
	
	protected int direction;
	
	protected Player player;
	
	public MoveHandler(int direction, Player player) {
		this.direction = direction;
		this.player = player;
	}

	@Override
	public void handle() {
		//player.setMoving(direction, true);
	}

}
