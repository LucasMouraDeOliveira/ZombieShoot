package com.lordkadoc.server.world;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.server.entities.Entity;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.entities.Projectile;
import com.lordkadoc.server.entities.Zombie;
import com.lordkadoc.server.factory.Factory;

public class World {
	
	private Cell[][] cells;
	
	private List<Player> players;
	
	private List<Zombie> zombies;
	
	private List<Projectile> projectiles;
	
	private int width, height;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		this.players = new ArrayList<Player>();
		this.zombies = new ArrayList<Zombie>();
		this.projectiles = new ArrayList<Projectile>();
		this.initWorld();
	}
	
	public void initWorld(){
		this.cells = new Cell[width][height];
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				this.cells[i][j] = new Cell(i,j);
				this.cells[i][j].setBiome(2);
				if(i==0 || i == width-1 || j == 0 || j == height-1){
					this.cells[i][j].setGameObject(ObjectPool.getInstance().getObject(1));
				}
			}
		}
		
		for(int i=0;i<100;i++){
			Zombie z = Factory.createZombie((int)(Math.random()*1000), (int)(Math.random()*1000));
			addZombie(z);
		}
		
		this.cells[5][5].setGameObject(ObjectPool.getInstance().getObject(1));
		this.cells[5][6].setGameObject(ObjectPool.getInstance().getObject(1));
		this.cells[5][7].setGameObject(ObjectPool.getInstance().getObject(1));
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean collides(double x, double y, int width, int height){
		Point2D.Double p1 = new Point2D.Double(x-width/2, y-height/2);
		Point2D.Double p2 = new Point2D.Double(x+width/2, y-height/2);
		Point2D.Double p3 = new Point2D.Double(x+width/2, y+height/2);
		Point2D.Double p4 = new Point2D.Double(x-width/2, y+height/2);
		return !getCell((int)(p1.x/Constants.CELL_SIZE), (int)(p1.y/Constants.CELL_SIZE)).isCrossable()
				|| !getCell((int)(p2.x/Constants.CELL_SIZE), (int)(p2.y/Constants.CELL_SIZE)).isCrossable()
				|| !getCell((int)(p3.x/Constants.CELL_SIZE), (int)(p3.y/Constants.CELL_SIZE)).isCrossable()
				|| !getCell((int)(p4.x/Constants.CELL_SIZE), (int)(p4.y/Constants.CELL_SIZE)).isCrossable();
	}
	
	public Cell getEntityCurrentCell(Entity entity){
		int x = (int) (entity.getX()/Constants.CELL_SIZE);
		int y = (int) (entity.getY()/Constants.CELL_SIZE);
		return getCell(x, y);
	}
	
	public Cell getCell(int x, int y){
		try{
			return cells[x][y];
		} catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Zombie> getZombies() {
		return zombies;
	}
	
	public void addZombie(Zombie zombie) {
		this.zombies.add(zombie);
	}
	
	public void removeZombie(Entity zombie) {
		this.zombies.remove(zombie);
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public void addProjectile(Projectile projectile) {
		this.projectiles.add(projectile);
	}
	
	public void removeProjectile(Projectile projectile) {
		this.projectiles.remove(projectile);
	}
	
}
