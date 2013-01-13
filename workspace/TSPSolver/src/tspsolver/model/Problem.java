package tspsolver.model;


import java.util.ArrayList;
import java.util.List;

import tspsolver.annotations.Immutable;
import tspsolver.utils.ArrayUtils;


@Immutable
public class Problem {

	
	private final List<Point> pointList;
	private final double[][] distances;
	
	
	public Problem(List<Point> pointList){
		this.pointList = pointList;
		distances = new double[pointList.size()][pointList.size()];
		for(int i=0; i<pointList.size(); ++i){
			for(int j=i; j<pointList.size(); ++j){
				distances[i][j] = distances[j][i] = pointList.get(i).getDistance(pointList.get(j));
			}
		}
	}

	public double[][] getDistances() {
		return ArrayUtils.cloneArray(distances);
	}


	public List<Point> getPointList() {
		return new ArrayList<Point>(pointList);
	}
	

}
