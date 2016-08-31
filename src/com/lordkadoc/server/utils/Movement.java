package com.lordkadoc.server.utils;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lordkadoc.commons.Constants;
import com.lordkadoc.server.entities.LivingEntity;
import com.lordkadoc.server.world.Cell;
import com.lordkadoc.server.world.World;

public class Movement {
	
	public void move2(LivingEntity entity, int speed, World world){
		double dx = entity.getDx();
		double dy = entity.getDy();
		if(dx == 0 && dy == 0){
			return;
		}
		for(int i=0;i<speed;i++){
			double x = entity.getX()+entity.getDx()/speed;
			double y = entity.getY()+entity.getDy()/speed;
			if(world.collides(x, y, entity.getWidth(), entity.getHeight())){
				x = entity.getX();
				y = entity.getY()+entity.getDy()/speed;
				if(world.collides(x, y, entity.getWidth(), entity.getHeight())){
					x = entity.getX()+entity.getDx()/speed;
					y = entity.getY();
					if(world.collides(x, y, entity.getWidth(), entity.getHeight())){
						break;
					}else{
						entity.setX(x);
						entity.setY(y);
					}
				}else{
					entity.setX(x);
					entity.setY(y);
				}
			}
			else{
				entity.setX(x);
				entity.setY(y);
			}
		}
	}
	
	/**
	 * Déplace une entité en fonction de son vecteur de déplacement et des obstacles présents sur la carte
	 * 
	 * @param entity l'entité à déplacer
	 * @param world le terrain
	 */
	public void move(LivingEntity entity, World world){
		
		List<Point2D.Double> hitboxPoints;
		Map<Point2D.Double, List<Cell>> cellsForPoint = new HashMap<Point2D.Double, List<Cell>>();
		List<Intersection> intersections = new ArrayList<Intersection>();
		
		// 1: Récupérer les 4 points de la hitbox de l'entité
		hitboxPoints = getHitboxPoints(entity);
		
		// 2: Pour chaque point, récupérer les cases de la carte traversées par le vecteur de déplacement en partant du point
		for(Point2D.Double point : hitboxPoints){
			cellsForPoint.put(point, getObstacleCells(getCellsForPointAndVector(point, entity.getDx(), entity.getDy(), world)));
		}
		// 3: Pour chaque case non traversable, déterminer le(s) point(s) d'intersection avec le vecteur de déplacement en partant du point
		// et déterminer la distance parcourue avant intersection
		for(Point2D.Double point : cellsForPoint.keySet()){
			intersections.add(getClosestIntersectionForPoint(point, entity.getDx(), entity.getDy(), cellsForPoint.get(point)));
		}
		
		// 4: S'il y a au moins une intersection
		Intersection firstIntersection = getFirstIntersection(intersections);
		if(firstIntersection != null){
			// 4.1 : calculer le vecteur de rebond pour l'intersection la plus proche et son point associé
			
			// 4.2 : déplacer l'entité au point d'intersection
			//Point2D.Double intersectionPoint = closestIntersectionForPoint.get(firstIntersectingPoint);
			/*entity.setX(entity.getX()+intersectionPoint.getX()-firstIntersectingPoint.getX());
			entity.setY(entity.getY()+intersectionPoint.getY()-firstIntersectingPoint.getY());
			entity.setDx(-1*(entity.getDx()/2));
			entity.setDy(-1*(entity.getDy()/2));*/
			// 4.3 : mettre à jour le vecteur de déplacement de l'entité
			
			// 4.4 : appeler de nouveau la fonction
			//move(entity, world);
		}
		// 5 : Sinon, déplacer le joueur vers sa nouvelle position
		else{
			entity.setX(entity.getX()+entity.getDx());
			entity.setY(entity.getY()+entity.getDy());
			entity.setDx(0);
			entity.setDy(0);
		}
		
	}
	
	/**
	 * Retourne les quatres points qui forment la hitbox d'une entité
	 * 
	 * @param entity l'entité
	 * 
	 * @return les quatres points qui forment le rectangle de la hitbox de l'entité
	 */
	private List<Point2D.Double> getHitboxPoints(LivingEntity entity){
		List<Point2D.Double> points = new ArrayList<Point2D.Double>();
		points.add(new Point2D.Double(entity.getX()-entity.getWidth()/2, entity.getY()-entity.getHeight()/2));
		points.add(new Point2D.Double(entity.getX()-entity.getWidth()/2, entity.getY()+entity.getHeight()/2));
		points.add(new Point2D.Double(entity.getX()+entity.getWidth()/2, entity.getY()-entity.getHeight()/2));
		points.add(new Point2D.Double(entity.getX()+entity.getWidth()/2, entity.getY()+entity.getHeight()/2));
		return points;
	}
	
	/**
	 * Retourne les cellules traversées par la ligne partant du point en appliquant le vecteur (dx,dy)
	 * 
	 * @param point le point de départ
	 * @param dx la composante x du vecteur de déplacement 
	 * @param dy la composante y du vecteur de déplacement
	 * @param world le terrain
	 * 
	 * @return la liste des cellules traversées par le vecteur en partant du point
	 */
	private List<Cell> getCellsForPointAndVector(Point2D.Double point, double dx, double dy, World world){
		Point2D.Double point2 = new Point2D.Double(point.x+dx, point.y+dy);
		int x1 = (int) (point.x/Constants.CELL_SIZE);
		int y1 = (int) (point.y/Constants.CELL_SIZE);
		int x2 = (int) (point2.x/Constants.CELL_SIZE);
		int y2 = (int) (point2.y/Constants.CELL_SIZE);
		if(x1 > x2){
			int tmp = x2;
			x2 = x1;
			x1 = tmp;
		}
		if(y1 > y2){
			int tmp = y2;
			y2 = y1;
			y1 = tmp;
		}
		List<Cell> cells = new ArrayList<Cell>();
		for(int i=x1;i<=x2;i++){
			for(int j=y1;j<=y2;j++){
				cells.add(world.getCell(i, j));
			}
		}
		return cells;
	}
	
	
	/**
	 * Retourne les cellules non traversables d'une liste de cellules
	 * 
	 * @param cells la liste de cellules
	 * 
	 * @return les cellules non traversables dans la liste
	 */
	private List<Cell> getObstacleCells(List<Cell> cells){
		List<Cell> tmp = new ArrayList<Cell>();
		for(Cell cell : cells){
			if(!cell.isCrossable())
				tmp.add(cell);
		}
		return tmp;
	}

	/**
	 * Retourne le point d'intersection le plus proche du point de départ entre un vecteur partant d'un point et une liste de cellules.
	 * S'il n'y a pas de point d'intersection avec ces cellules, ou si la liste de cellules est vide, renvoie null
	 * 
	 * @param point le point de départ du vecteur
	 * @param dx la composante x du vecteur de mouvement
	 * @param dy la composante y du vecteur de mouvement
	 * @param cells la liste de cellule
	 * 
	 * @return le point d'intersection le plus proche de 'point' s'il y en a un, null sinon
	 */
	private Intersection getClosestIntersectionForPoint(Point2D.Double point, double dx, double dy, List<Cell> cells){
		Intersection closestIntersection = null;
		List<Intersection> currentCellIntersections;
		for(Cell cell : cells){
			currentCellIntersections = intersection(point, dx, dy, getLinesForCell(cell));
			for(Intersection currentIntersection : currentCellIntersections){
				if(closestIntersection == null || isCloser(currentIntersection, closestIntersection))
					closestIntersection = currentIntersection;	
			}
		}
		return closestIntersection;
	}
	
	private Intersection getFirstIntersection(List<Intersection> intersections){
		Intersection currentIntersection = null;
		for(Intersection intersection : intersections){
			if(currentIntersection == null || isCloser(intersection, currentIntersection)){
				currentIntersection = intersection;
			}
		}
		return currentIntersection;
	}
	
	/**
	 * Retourne un rectangle correspondant à la hitbox d'une cellule de la carte
	 * 
	 * @param cell la cellule
	 * 
	 * @return la hitbox de la cellule, sous la forme d'un rectangle
	 */
	private List<Line2D.Double> getLinesForCell(Cell cell){
		List<Line2D.Double> lines = new ArrayList<Line2D.Double>();
		lines.add(new Line2D.Double(cell.getX()*Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE, cell.getX()*Constants.CELL_SIZE+Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE));
		lines.add(new Line2D.Double(cell.getX()*Constants.CELL_SIZE+Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE, cell.getX()*Constants.CELL_SIZE+Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE+Constants.CELL_SIZE));
		lines.add(new Line2D.Double(cell.getX()*Constants.CELL_SIZE+Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE+Constants.CELL_SIZE, cell.getX()*Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE+Constants.CELL_SIZE));
		lines.add(new Line2D.Double(cell.getX()*Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE+Constants.CELL_SIZE, cell.getX()*Constants.CELL_SIZE, cell.getY()*Constants.CELL_SIZE));
		return lines;
	}
	
	/**
	 * Retourne le(s) point(s) d'intersection entre un vecteur (dx, dy) partant d'un point et un rectangle
	 * @param point le point de départ du vecteur de déplacement
	 * @param dx la composante x du vecteur de déplacement 
	 * @param dy la composante y du vecteur de déplacement
	 * @param cell la liste de lignes (4) correspondant à la hitbox d'une cellule
	 * 
	 * @return le point d'intersection entre le vecteur et le rectangle
	 */
	private List<Intersection> intersection(Point2D.Double point, double dx, double dy, List<Line2D.Double> cell){
		List<Intersection> intersection = new ArrayList<Intersection>();
		Line2D.Double l1 = new Line2D.Double(point.x, point.y, point.x+dx, point.y+dy);
		Point2D.Double p2;
		for(Line2D.Double l2 : cell){
			p2 = getIntersection(l1, l2);
			if(p2 != null){
				Intersection i = new Intersection();
				i.origin = point;
				i.intersectionPoint = p2;
				i.intersectionLine = l2;
				intersection.add(i);
			}
		}
		return intersection;
	}
	
	private Point2D.Double getIntersection(Line2D.Double line1, Line2D.Double line2) {

        double x1,y1, x2,y2, x3,y3, x4,y4;
        x1 = line1.x1; y1 = line1.y1; x2 = line1.x2; y2 = line1.y2;
        x3 = line2.x1; y3 = line2.y1; x4 = line2.x2; y4 = line2.y2;
        double x = (
                (x2 - x1)*(x3*y4 - x4*y3) - (x4 - x3)*(x1*y2 - x2*y1)
                ) /
                (
                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
                );
        double y = (
                (y3 - y4)*(x1*y2 - x2*y1) - (y1 - y2)*(x3*y4 - x4*y3)
                ) /
                (
                (x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)
                );
        
        if(x1 > x2){
        	double tmp = x2;
        	x2 = x1;
        	x1 = tmp;
        }
        if(y1 > y2){
        	double tmp = y2;
        	y2 = y1;
        	y1 = tmp;
        }
        
        if(x >= x1 && x <= x2 && y >= y1 && y <= y2)
        	return new Point2D.Double(x, y);
        else
        	return null;

    }
	
	private boolean isCloser(Intersection a, Intersection b){
		return a.distance() < b.distance();
	}
	
	/**
	 * Calcule un vecteur de rebond par rapport au point de départ d'un déplacement, le vecteur de déplacement associé, et le point d'intersection avec un obstacle
	 * 
	 * @param point le point de départ
	 * @param intersection le point d'intersection
	 * @param dx la composante x du vecteur de déplacement 
	 * @param dy la composante y du vecteur de déplacement
	 * 
	 * @return le vecteur de rebond
	 */
	private Point2D.Double getRebounceVector(Point2D.Double point, Point2D.Double intersection, int dx, int dy){
		return null;
	}
	
	class Intersection{
		
		public Point2D.Double origin;
		
		public Point2D.Double intersectionPoint;
		
		public Line2D.Double intersectionLine;
		
		public double distance(){
			return intersectionPoint.distance(origin);
		}
		
	}

}
