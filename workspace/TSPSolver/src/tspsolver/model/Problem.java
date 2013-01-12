package tspsolver.model;


import java.util.List;

public class Problem {

	
	private List<Point> pointList;
	private double[][] distances;
	
	
	public Problem(List<Point> pointList){
		this.pointList = pointList;
	}
	
	
	public double[][] getDistances() {
		if(distances == null){
			distances = new double[pointList.size()][pointList.size()];
			for(int i=0; i<pointList.size(); ++i){
				for(int j=i; j<pointList.size(); ++j){
					distances[i][j] = distances[j][i] = pointList.get(i).getDistance(pointList.get(j));
				}
			}
		}
		return distances;
	}


	public List<Point> getPointList() {
		return pointList;
	}
	

}
