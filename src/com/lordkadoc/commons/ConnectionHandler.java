package com.lordkadoc.commons;

import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.lordkadoc.commons.networkobjects.ConnectionAttempt;
import com.lordkadoc.commons.networkobjects.NCell;
import com.lordkadoc.commons.networkobjects.NEntity;
import com.lordkadoc.commons.networkobjects.NGameObject;
import com.lordkadoc.commons.networkobjects.NPlayer;
import com.lordkadoc.commons.networkobjects.NProjectile;
import com.lordkadoc.commons.networkobjects.NWorld;
import com.lordkadoc.commons.networkobjects.NZombie;
import com.lordkadoc.commons.networkobjects.PlayerUpdate;

public class ConnectionHandler {
	
	public static void register(Kryo kryo){
		kryo.register(ArrayList.class);
		kryo.register(boolean[].class);
		kryo.register(ConnectionAttempt.class);
		kryo.register(NCell.class);
		kryo.register(NCell[].class);
		kryo.register(NCell[][].class);
		kryo.register(NGameObject.class);
		kryo.register(NWorld.class);
		kryo.register(NProjectile.class);
		kryo.register(NEntity.class);
		kryo.register(NPlayer.class);
		kryo.register(NZombie.class);
		kryo.register(PlayerUpdate.class);
	}

}