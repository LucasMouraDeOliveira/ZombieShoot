package com.lordkadoc.server.objects;

public abstract class GameObject {
	
	protected final int gameObjectId;
	
	public GameObject(int gameObjectId, boolean crossable) {
		this.gameObjectId = gameObjectId;
	}
	
	protected boolean crossable;
	
	public boolean isCrossable() {
		return crossable;
	}
	
	public abstract GameObject clone();

	public int getGameObjectId() {
		return gameObjectId;
	}
	
}
