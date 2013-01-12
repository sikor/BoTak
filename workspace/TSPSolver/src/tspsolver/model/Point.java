package tspsolver.model;

import tspsolver.annotations.Immutable;


@Immutable
public class Point {
	
	
	private final double x;
	private final double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double getDistance(Point p){
		double dx = x - p.getX();
		double dy = y - p.getY();
		return Math.sqrt(dx*dx + dy*dy);
	}
	

}
