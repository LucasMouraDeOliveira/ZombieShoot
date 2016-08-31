package com.lordkadoc.server.entities;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.commons.networkobjects.PlayerUpdate;

public class Player extends LivingEntity{
	
	protected String name;
	
	protected boolean shooting;
	
	protected Weapon weapon;
		
	public Player(int x, int y, int entityId, String name){
		super(x, y, entityId);
		this.name = name;
	}
	
	public void handle(PlayerUpdate playerUpdate) {
		boolean[] b  = playerUpdate.getMove();
		this.dx = 0;
		this.dy = 0;
		if(b[0]){
			this.setDy(dy-1*Constants.PLAYER_TOP_SPEED);
			this.speedY=Math.max(-1*Constants.PLAYER_TOP_SPEED, this.speedY-1*Constants.PLAYER_ACCELERATION);
		}
		if(b[1]){
			this.setDy(dy+1*Constants.PLAYER_TOP_SPEED);
			this.speedY=Math.min(1*Constants.PLAYER_TOP_SPEED, this.speedY+1*Constants.PLAYER_ACCELERATION);
		}
		if(!(b[0] ^ b[1])){
			this.speedY = 0;
		}
		if(b[2]){
			this.setDx(dx-1*Constants.PLAYER_TOP_SPEED);
			this.speedX=Math.max(-1*Constants.PLAYER_TOP_SPEED, this.speedX-1*Constants.PLAYER_ACCELERATION);
		}
		if(b[3]){
			this.setDx(dx+1*Constants.PLAYER_TOP_SPEED);
			this.speedX=Math.min(1*Constants.PLAYER_TOP_SPEED, this.speedX+1*Constants.PLAYER_ACCELERATION);
		}
		if(!(b[2] ^ b[3])){
			this.speedX = 0;
		}
		
		if(playerUpdate.isShooting()){
			setShooting(true);
		}
		
		setAngle(playerUpdate.getAngle());
		
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
}
