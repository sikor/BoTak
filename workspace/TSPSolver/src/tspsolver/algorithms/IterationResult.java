package tspsolver.algorithms;

import java.util.Arrays;

import tspsolver.annotations.Immutable;



@Immutable
public class IterationResult {
	private final int [] path;
	private final double length;
	private final int iterationNumber;
	
	
	public IterationResult(int [] path, double length, int iterationNumber){
		this.path = path;
		this.length = length;
		this.iterationNumber = iterationNumber;
	}
	
	
	public int[] getPath() {
		return Arrays.copyOf(path, path.length);
	}

	public double getLength() {
		return length;
	}


	public int getIterationNumber() {
		return iterationNumber;
	}

	
	
}