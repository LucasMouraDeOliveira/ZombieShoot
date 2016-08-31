package com.lordkadoc.server.factory;

import java.util.ArrayList;
import java.util.List;

import com.lordkadoc.commons.networkobjects.NCell;
import com.lordkadoc.commons.networkobjects.NGameObject;
import com.lordkadoc.commons.networkobjects.NPlayer;
import com.lordkadoc.commons.networkobjects.NProjectile;
import com.lordkadoc.commons.networkobjects.NWorld;
import com.lordkadoc.commons.networkobjects.NZombie;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.entities.Projectile;
import com.lordkadoc.server.entities.Zombie;
import com.lordkadoc.server.objects.GameObject;
import com.lordkadoc.server.world.Cell;
import com.lordkadoc.server.world.World;

public class NFactory {
	
	/**
	 * Renvoie la map sans les entités
	 * @param world le monde
	 * @return la carte sans entités et sans joueurs
	 */
	public static NWorld getNWorld(World world) {
		NWorld nWorld = new NWorld();
		int width = world.getWidth();
		int height = world.getHeight();
		NCell[][] nCells = new NCell[width][height];
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				nCells[i][j] = getNCell(world.getCell(i, j));
			}
		}
		nWorld.setCells(nCells);
		nWorld.setZombies(new ArrayList<NZombie>());
		nWorld.setPlayers(new ArrayList<NPlayer>());
		nWorld.setProjectiles(new ArrayList<NProjectile>());
		return nWorld;
	}
	
	public static NWorld getNWorldForPlayer(World world, Player currentPlayer){
		NWorld nWorld = new NWorld();
		nWorld.setCurrentPlayer(getNPlayer(currentPlayer));
		List<NPlayer> nPlayers = new ArrayList<NPlayer>();
		for(Player player : world.getPlayers()){
			//TODO check if player is visible by currentPlayer
			if(!player.equals(currentPlayer)){				
				nPlayers.add(getNPlayer(player));
			}
		}
		nWorld.setPlayers(nPlayers);
		List<NZombie> nZombies = new ArrayList<NZombie>();
		for(Zombie zombie : world.getZombies()){
			nZombies.add(getNZombie(zombie));
		}
		nWorld.setZombies(nZombies);
		List<NProjectile> nProjectiles = new ArrayList<NProjectile>();
		for(Projectile projectile : world.getProjectiles()){
			nProjectiles.add(getNProjectile(projectile));
		}
		nWorld.setProjectiles(nProjectiles);
		//TODO add other visible entities
		//TODO add changes in the cells (blocks destroyed, created, ...)
		return nWorld;
	}
	
	public static NCell getNCell(Cell cell){
		NCell ncell = new NCell();
		ncell.setX(cell.getX());
		ncell.setY(cell.getY());
		ncell.setBiome(cell.getBiome());
		if(cell.getGameObject() != null){
			ncell.setGameObject(getNGameObject(cell.getGameObject()));
		}
		return ncell;
	}

	public static NGameObject getNGameObject(GameObject gameObject) {
		NGameObject nGameObject = new NGameObject();
		nGameObject.setGameObjectId(gameObject.getGameObjectId());
		return nGameObject;
	}
	
	public static NZombie getNZombie(Zombie zombie){
		NZombie nZombie = new NZombie();
		nZombie.setX((int)zombie.getX());
		nZombie.setY((int)zombie.getY());
		nZombie.setWidth(zombie.getWidth());
		nZombie.setHeight(zombie.getHeight());
		nZombie.setAngle(zombie.getAngle());
		nZombie.setSkinId(zombie.getSkinId());
		nZombie.setHealth(zombie.getHealth());
		return nZombie;
	}
	
	public static NPlayer getNPlayer(Player player){
		NPlayer nPlayer = new NPlayer();
		nPlayer.setX((int)player.getX());
		nPlayer.setY((int)player.getY());
		nPlayer.setWidth(player.getWidth());
		nPlayer.setHeight(player.getHeight());
		nPlayer.setAngle(player.getAngle());
		nPlayer.setSkinId(player.getSkinId());
		nPlayer.setName(player.getName());
		nPlayer.setHealth(player.getHealth());
		nPlayer.setAmmos(player.getWeapon().getAmmos());
		nPlayer.setAmmosInMagazine(player.getWeapon().getAmmosInMagazine());
		return nPlayer;
	}
	
	public static NProjectile getNProjectile(Projectile projectile){
		NProjectile nProjectile = new NProjectile();
		nProjectile.setAngle(projectile.getAngle());
		nProjectile.setX((int)projectile.getX());
		nProjectile.setY((int)projectile.getY());
		nProjectile.setSkinId(projectile.getSkinId());
		return nProjectile;
	}
	
	public static void main(String[] args) {
		World world = new World(100, 100);
		long time = System.currentTimeMillis();
		NFactory.getNWorld(world);
		System.out.println("Temps d'exécution : " + (System.currentTimeMillis()-time) + " millisecondes");
	}

}
