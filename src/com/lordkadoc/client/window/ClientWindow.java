package com.lordkadoc.client.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.lordkadoc.client.graphics.ObjectImagePool;
import com.lordkadoc.commons.Constants;
import com.lordkadoc.commons.networkobjects.NCell;
import com.lordkadoc.commons.networkobjects.NGameObject;
import com.lordkadoc.commons.networkobjects.NPlayer;
import com.lordkadoc.commons.networkobjects.NProjectile;
import com.lordkadoc.commons.networkobjects.NWorld;
import com.lordkadoc.commons.networkobjects.NZombie;

public class ClientWindow extends JPanel implements Observer, KeyListener, MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 6243355324410119425L;
	
	private NWorld currentWorld;
	
	private ObjectImagePool imagePool;
	
	private Point mousePoint;
	
	private NPlayer currentPlayer;
	
	private boolean[] move;
	
	private boolean shooting;
	
	public ClientWindow() {
		this.setPreferredSize(new Dimension(Constants.CELL_SIZE*15, Constants.CELL_SIZE*15));
		this.imagePool = ObjectImagePool.getInstance();
		this.move = new boolean[4];
		this.mousePoint = new Point(0,0);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g){
		this.paintPanel(g, currentWorld);
	}
	
	private void paintPanel(Graphics g, NWorld world){
		
		if(world == null)
			return;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//paint world
		NCell currentCell;
		BufferedImage img;
		//On parcours le tableau de cellules
		for(int i=0;i<world.getCells().length;i++){
			for(int j=0;j<world.getCells().length;j++){
				currentCell = world.getCells()[i][j];
				//On récupère l'image correspondant au biome de la cellule dans la pool d'images
				img = this.imagePool.getBiomeImage(currentCell.getBiome());
				//Si l'image n'est pas null, on la dessine, sinon on affiche un carré rouge à la place
				if(img != null){
					g.drawImage(img, i*Constants.CELL_SIZE, j*Constants.CELL_SIZE, this);
				}else{
					g.setColor(Color.RED);
					g.fillRect(i*Constants.CELL_SIZE, j*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
				}
				NGameObject gameObject = currentCell.getGameObject();
				if(gameObject != null){
					img = this.imagePool.getObjectImage(gameObject.getGameObjectId());
					if(img != null){
						g.drawImage(img, i*Constants.CELL_SIZE, j*Constants.CELL_SIZE, this);
					}else{
						g.setColor(Color.RED);
						g.drawString("?", i*Constants.CELL_SIZE+10, j*Constants.CELL_SIZE+10);
					}
				}
				
			}
		}
		
		for(NPlayer player : world.getPlayers()){
			img = this.imagePool.getEntityImage(player.getSkinId());
			if(img != null){
				drawRotatedImage(img, player.getX(), player.getY(), player.getAngle()-Math.PI/2, g);
				drawHealthBar(player, g);
			}
		}
		
		for(NZombie zombie : world.getZombies()){
			img = this.imagePool.getEntityImage(zombie.getSkinId());
			if(img != null){
				drawRotatedImage(img, zombie.getX(), zombie.getY(), zombie.getAngle()-Math.PI/2, g);
			}
		}
		
		for(NProjectile projectile : world.getProjectiles()){
			img = this.imagePool.getProjectileImage(projectile.getSkinId());
			if(img != null){
				drawRotatedImage(img, projectile.getX(), projectile.getY(), projectile.getAngle(), g);
			}
		}
		
		if(this.currentPlayer == null)
			return;
		
		img = this.imagePool.getEntityImage(this.currentPlayer.getSkinId());
		if(img != null){
			//g.setColor(Color.RED);
			//g.drawRect(this.currentPlayer.getX()-26, this.currentPlayer.getY()-26, 52, 52);
			drawRotatedImage(img, this.currentPlayer.getX(), this.currentPlayer.getY(), this.currentPlayer.getAngle()-Math.PI/2, g);
			drawHealthBar(this.currentPlayer, g);
			drawAmmos(this.currentPlayer, g);
		}
		
	}
	
	public void drawHealthBar(NPlayer player, Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(player.getX()-25, player.getY()-40, 50, 5);
		g.setColor(Color.RED);
		g.fillRect(player.getX()-25, player.getY()-40, (int)(50*((double)player.getHealth()/100)), 5);
		g.setColor(Color.BLACK);
		g.drawRect(player.getX()-25, player.getY()-40, 50, 5);
	}
	
	public void drawAmmos(NPlayer player, Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(player.getX()-25, player.getY()-60, 50, 20);
		g.setColor(Color.BLACK);
		g.drawRect(player.getX()-25, player.getY()-60, 50, 20);
		g.drawString(player.getAmmosInMagazine() + " / " + player.getAmmos(), player.getX() - 20, player.getY()-45);
	}
	
	public void drawRotatedImage(BufferedImage img, int x, int y, double angle, Graphics g){
		AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(angle);
        at.translate(-img.getWidth()/2, -img.getHeight()/2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, at, this);
	}

	public boolean[] getMove() {
		return move;
	}
	
	public boolean isShooting() {
		return shooting;
	}

	public double getAngle() {
		if(this.currentPlayer == null)
			return 0;
		
		int deltaX = mousePoint.x - (this.currentPlayer.getX());
		int deltaY = mousePoint.y - (this.currentPlayer.getY());
		return Math.atan2(deltaY, deltaX);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof NWorld){
			if(this.currentWorld == null){
				this.currentWorld = (NWorld)arg;
			}else{
				((NWorld) arg).setCells(this.currentWorld.getCells());
				this.currentWorld = (NWorld)arg;
				if(((NWorld)arg).getCurrentPlayer() != null){
					this.currentPlayer = ((NWorld)arg).getCurrentPlayer();
				}
			}
		}else if(arg.toString().equals("update")){
			this.repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			move[0] = true;
			break;
		case KeyEvent.VK_DOWN:
			move[1] = true;
			break;
		case KeyEvent.VK_LEFT:
			move[2] = true;
			break;
		case KeyEvent.VK_RIGHT:
			move[3] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			move[0] = false;
			break;
		case KeyEvent.VK_DOWN:
			move[1] = false;
			break;
		case KeyEvent.VK_LEFT:
			move[2] = false;
			break;
		case KeyEvent.VK_RIGHT:
			move[3] = false;
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.requestFocusInWindow();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.shooting = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.shooting = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePoint = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePoint = new Point(e.getX(), e.getY());
	}

}
