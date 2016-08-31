package com.lordkadoc.server.utils;

import com.lordkadoc.server.world.Cell;

public class Node {
	
	protected double f, g;
	
	protected Node previous;
	
	protected Cell cell;
	
	public Node(Cell cell){
		this.cell = cell;
		this.f = 10000;
		this.g = 10000;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public Node getPrevious() {
		return previous;
	}
	
	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	public double getF() {
		return f;
	}
	
	public void setF(double f) {
		this.f = f;
	}
	
	public double getG() {
		return g;
	}
	
	public void setG(double g) {
		this.g = g;
	}
	
	public int getX() {
		return cell.getX();
	}
	
	public int getY() {
		return cell.getY();
	}

}