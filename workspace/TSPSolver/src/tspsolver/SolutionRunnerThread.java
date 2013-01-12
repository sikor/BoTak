package tspsolver;

import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.IterationResult;

public class SolutionRunnerThread extends Thread{

	private final ISolver solver;
	private final ProblemSolution problemSolution;
	
	public SolutionRunnerThread(ISolver solver, ProblemSolution problemSolution) {
		this.solver = solver;
		this.problemSolution = problemSolution;
	}

	
	public void run(){
		IterationResult iterationResult;
		while((iterationResult = solver.nextIteration())!= null){
			problemSolution.addIterationResult(iterationResult);
		}
		problemSolution.setFinished();
	}
	
	
	
}
