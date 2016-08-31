package com.lordkadoc.client.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ObjectImagePool {
	
	private final static ObjectImagePool instance = new ObjectImagePool();
	
	protected Map<Integer, BufferedImage> gameObjectImages;
	
	protected Map<Integer, BufferedImage> biomeImages;
	
	protected Map<Integer, BufferedImage> entityImages;
	
	protected Map<Integer, BufferedImage> projectileImages;
	
	private ObjectImagePool(){
		this.gameObjectImages = new HashMap<Integer, BufferedImage>();
		this.biomeImages = new HashMap<Integer, BufferedImage>();
		this.entityImages = new HashMap<Integer, BufferedImage>();
		this.projectileImages = new HashMap<Integer, BufferedImage>();
		this.initGameObjectImages();
		this.initBiomeImages();
		this.initEntityImages();
		this.initProjectileImages();
	}
	
	public static ObjectImagePool getInstance(){
		return instance;
	}

	private void initGameObjectImages() {
		this.gameObjectImages.put(1, loadImage("/images/world/wall.png"));
	}
	
	private void initBiomeImages() {
		this.biomeImages.put(1, loadImage("/images/world/grass.png"));
		this.biomeImages.put(2, loadImage("/images/world/wood_floor.png"));
	}
	
	private void initEntityImages() {		
		this.entityImages.put(1, loadImage("/images/entity/joueur.png"));
		this.entityImages.put(2, loadImage("/images/entity/zombie.png"));
	}
	
	private void initProjectileImages() {
		this.projectileImages.put(1, loadImage("/images/projectile/ball.png"));
	}
	
	public BufferedImage getObjectImage(int index){
		return this.gameObjectImages.get(index);
	}
	
	public BufferedImage getBiomeImage(int index){
		return this.biomeImages.get(index);
	}
	
	public BufferedImage getEntityImage(int index){
		return this.entityImages.get(index);
	}
	
	public BufferedImage getProjectileImage(int index){
		return this.projectileImages.get(index);
	}
	
	private BufferedImage loadImage(String path){
		try {
			InputStream in = ObjectImagePool.class.getResourceAsStream(path);
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		ObjectImagePool.getInstance();
	}

}
