package tspsolver.algorithms;

public interface ISolver {
	
	/**
	 * 
	 * @param distances
	 * @return
	 * 
	 * take distances between points return the array of point indexes in optimized order
	 */
	public int[] solve(double[][] distances);

}
