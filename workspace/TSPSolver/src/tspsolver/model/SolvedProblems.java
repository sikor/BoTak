package tspsolver.model;

import java.util.ArrayList;
import tspsolver.model.interfaces.IModelChangeListener;

public class SolvedProblems {
	
	
	private ArrayList<ProblemSolution> problemsSolutions;
	private IModelChangeListener programStateChangeListener;
	
	SolvedProblems(IModelChangeListener programStateChangeListener){
		this.programStateChangeListener = programStateChangeListener;
		problemsSolutions = new ArrayList<ProblemSolution>();
	}

	synchronized void addSolution(ProblemSolution problemSolution) {
		problemsSolutions.add(problemSolution);
		programStateChangeListener.newSolutionAdded(problemSolution);
	}

	public synchronized int getSolutionsCount(){
		return problemsSolutions.size();
	}
	
	public synchronized ProblemSolution getProblemSolution(int i){
		return problemsSolutions.get(i);
	}

}
