package com.lordkadoc.server.objects;

public class Wall extends GameObject {

	public Wall(int gameObjectId) {
		super(gameObjectId, false);
	}

	@Override
	public GameObject clone() {
		return new Wall(this.gameObjectId);
	}

}
