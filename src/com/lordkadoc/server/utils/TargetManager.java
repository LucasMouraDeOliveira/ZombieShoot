package com.lordkadoc.server.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.server.entities.LivingEntity;
import com.lordkadoc.server.entities.Player;
import com.lordkadoc.server.entities.Zombie;
import com.lordkadoc.server.world.Cell;
import com.lordkadoc.server.world.World;

public class TargetManager {
	
	private static final TargetManager instance = new TargetManager();
	
	private TargetManager(){
		
	}
	
	public static TargetManager getInstance(){
		return instance;
	}
	
	public void setTarget(Zombie zombie, World world){
		List<Player> playerList = world.getPlayers();
		Player target = null;
		for(Player player : playerList){
			if(target == null || isCloser(zombie, player, target)){
				target = player;
			}
		}
		zombie.setTarget(target);
	}
	
	private boolean isCloser(LivingEntity a, LivingEntity b, LivingEntity c){
		Point2D.Double pa = new Point2D.Double(a.getX(), a.getY());
		Point2D.Double pb = new Point2D.Double(b.getX(), b.getY());
		Point2D.Double pc = new Point2D.Double(c.getX(), c.getY());
		return pa.distance(pb) < pa.distance(pc);
	}
	
	public Cell nextTargetCell(Zombie zombie, World world){
		if(!zombie.hasTarget())
			return null;
		Cell source = world.getEntityCurrentCell(zombie);
		Cell target = world.getEntityCurrentCell(zombie.getTarget());
		List<Cell> cells = getPath(source, target, world);
		if(cells == null || cells.isEmpty()){
			return null;
		}
		return cells.get(0);
	}
	
	private List<Cell> getPath(Cell source, Cell target, World world){
		Comparator<Node> nodeComparator = new NodeComparator();
		PriorityQueue<Node> openList = new PriorityQueue<Node>(10, nodeComparator);
		List<Node> closedList = new ArrayList<Node>();
		Node[][] nodes = new Node[world.getWidth()][world.getHeight()];
		for(int i=0;i<world.getWidth();i++){
			for(int j=0;j<world.getHeight();j++){
				nodes[i][j] = new Node(world.getCell(i, j));
				if(world.getCell(i, j).equals(source)){
					openList.add(nodes[i][j]);
					nodes[i][j].setF(getHeuristicEstimation(source, target));
					nodes[i][j].setG(0);
				}
			}
		}
		Node currentNode;
		double g;
		while(!openList.isEmpty()){
			currentNode = openList.poll();
			if(currentNode.getCell().equals(target)){
				return getPath(source, currentNode);
			}
			closedList.add(currentNode);
			for(Node neighbor : getNeighbors(currentNode, nodes)){
				if(closedList.contains(neighbor)){
					continue;
				}
				g = currentNode.getG() + getDistance(currentNode, neighbor);
				if(!openList.contains(neighbor)){
					openList.add(neighbor);
				}else if(g > neighbor.getG()){
					continue;
				}
				neighbor.setPrevious(currentNode);
				neighbor.setG(g);
				neighbor.setF(neighbor.getG()+getHeuristicEstimation(neighbor.getCell(), target));
			}
			
		}
		
		return null;
	}
	
	private List<Node> getNeighbors(Node node, Node[][] nodes){
		List<Node> neighbors = new ArrayList<Node>();
		Node north = getNode(nodes, node.getX(), node.getY()-1);
		if(north != null && north.getCell().isCrossable())
			neighbors.add(north);
		Node south = getNode(nodes, node.getX(), node.getY()+1);
		if(south != null && south.getCell().isCrossable())
			neighbors.add(south);
		Node east = getNode(nodes, node.getX()+1, node.getY());
		if(east != null && east.getCell().isCrossable())
			neighbors.add(east);
		Node west = getNode(nodes, node.getX()-1, node.getY());
		if(west != null && west.getCell().isCrossable())
			neighbors.add(west);
		//TODO ajouter cases en diagonale
		return neighbors;
	}
	
	private Node getNode(Node[][] nodes, int x, int y){
		try{
			return nodes[x][y];
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	private double getDistance(Node currentNode, Node neighbor){
		return 1;
	}
	
	private double getHeuristicEstimation(Cell source, Cell target){
		return euclidianDistance(source.getX(), source.getY(), target.getX(), target.getY());
	}
	
	private double euclidianDistance(int x1, int y1, int x2, int y2){
		return new Point2D.Double(x1,y1).distance(new Point2D.Double(x2,y2));
	}
	
	private List<Cell> getPath(Cell source, Node node){
		List<Cell> cells = new ArrayList<Cell>();
		Node currentNode = node;
		while(currentNode != null){
			if(!currentNode.getCell().equals(source)){
				cells.add(0, currentNode.getCell());
			}
			currentNode = currentNode.getPrevious();
		}
		return cells;
	}
	
	public void moveZombieToward(World world, Zombie zombie, Cell cell){
		double x1 = zombie.getX();
		double y1 = zombie.getY();
		double x2 = cell.getX()*Constants.CELL_SIZE+Constants.CELL_SIZE/2;
		double y2 = cell.getY()*Constants.CELL_SIZE+Constants.CELL_SIZE/2;
		double x = x2-x1;
		double y = y2-y1;
		double m = Math.abs(x) + Math.abs(y);
		double n = 10 / m;
		x = x*n;
		y = y*n;
		zombie.setDx(x);
		zombie.setDy(y);
		new Movement().move2(zombie, 10, world);
	}	
	
	public void rotateZombieToward(Zombie zombie) {
		zombie.setAngle(Math.atan2(zombie.getTarget().getY()-zombie.getY(), zombie.getTarget().getX()-zombie.getX()));
	}
	
	public static void main(String[] args) {
		Comparator<Node> nodeComparator = new NodeComparator();
		PriorityQueue<Node> openList = new PriorityQueue<Node>(10, nodeComparator);
		Node n1 = new Node(new Cell(0, 0));
		n1.setF(10);
		Node n2 = new Node(new Cell(0, 1));
		openList.add(n1);
		openList.add(n2);
		System.out.println(openList.poll().getF());
		System.out.println(openList.poll().getF());
	}

	

}
