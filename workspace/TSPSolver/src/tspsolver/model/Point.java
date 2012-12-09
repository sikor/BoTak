package tspsolver.model;

public class Point {
	
	
	private double x;
	private double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public  double getX() {
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
