package tspsolver;

import tspsolver.algorithms.Algorithm;
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.Problem;
import tspsolver.model.interfaces.IModelChangeListener;

public class ProgramState {
	 
	private final SolvedProblems solvedProblems; //solved problems is thread - safe
	private final IModelChangeListener changeListener; //should be thread - safe implemented
	
	
	
	public ProgramState(IModelChangeListener el) {
		this.changeListener = el;
		this.solvedProblems = new SolvedProblems(changeListener);
	}
	
	
	public SolvedProblems getSolvedProblems(){
		return solvedProblems;
	}

	public ProblemSolution addNewSolution(Problem problem, Algorithm algorithm, AlgorithmParameters properties) {
		ProblemSolution problemSolution = new ProblemSolution(changeListener, problem, algorithm, properties);
		solvedProblems.addSolution(problemSolution);
		return problemSolution;
	}


}
