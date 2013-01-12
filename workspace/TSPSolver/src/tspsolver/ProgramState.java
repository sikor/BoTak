package tspsolver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tspsolver.algorithms.Algorithm;
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.Problem;
import tspsolver.model.interfaces.IModelChangeListener;

public class ProgramState {
	 
	private final SolvedProblems solvedProblems; //solved problems is thread - safe
	private final IModelChangeListener changeListener; //should be thread - safe implemented
	private final ExecutorService executor;
	
	
	
	public ProgramState(IModelChangeListener el) {
		executor = Executors.newSingleThreadExecutor();
		this.changeListener = el;
		this.solvedProblems = new SolvedProblems(executor, changeListener);
	}
	
	
	public SolvedProblems getSolvedProblems(){
		return solvedProblems;
	}

	public ProblemSolution addNewSolution(Problem problem, Algorithm algorithm, AlgorithmParameters properties) {
		ProblemSolution problemSolution = new ProblemSolution(executor, changeListener, problem, algorithm, properties);
		solvedProblems.addSolution(problemSolution);
		return problemSolution;
	}


}
