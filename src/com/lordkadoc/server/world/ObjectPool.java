package com.lordkadoc.server.world;

import java.util.HashMap;
import java.util.Map;

import com.lordkadoc.server.entities.Entity;
import com.lordkadoc.server.objects.GameObject;
import com.lordkadoc.server.objects.Wall;

public class ObjectPool {
	
	private static final ObjectPool instance = new ObjectPool();
	
	private Map<Integer, GameObject> gameObjectPool;
	
	private Map<Integer, Entity> entityPool;
	
	private ObjectPool() {
		this.initGameObjectPool();
		this.initEntityPool();
	}
	
	public static ObjectPool getInstance(){
		return instance;
	}
	
	public GameObject getObject(int index){
		return gameObjectPool.get(index).clone();
	}
	
	public Entity getEntity(int index){
		return entityPool.get(index);
	}
	
	private void initGameObjectPool(){
		this.gameObjectPool = new HashMap<Integer, GameObject>();
		this.gameObjectPool.put(1, new Wall(1));
	}

	private void initEntityPool(){
		this.entityPool = new HashMap<Integer, Entity>();
	}
	
	
}
