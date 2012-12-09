package tspsolver.algorithms;




public class IterationResult {
	private int [] path;
	private double length;
	private int iterationNumber;
	
	
	public IterationResult(int [] path, double length, int iterationNumber){
		this.path = path;
		this.length = length;
		this.iterationNumber = iterationNumber;
	}
	
	
	public int[] getPath() {
		return path;
	}

	public double getLength() {
		return length;
	}


	public int getIterationNumber() {
		return iterationNumber;
	}

	
	
}