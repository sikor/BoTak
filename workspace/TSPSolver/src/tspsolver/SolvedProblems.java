package tspsolver;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

import tspsolver.model.interfaces.IModelChangeListener;

public class SolvedProblems {
	
	
	private ArrayList<ProblemSolution> problemsSolutions;
	private IModelChangeListener programStateChangeListener;
	
	public SolvedProblems(IModelChangeListener programStateChangeListener){
		this.programStateChangeListener = programStateChangeListener;
		problemsSolutions = new ArrayList<ProblemSolution>();
	}

	synchronized void addSolution(final ProblemSolution problemSolution) {
		problemsSolutions.add(problemSolution);
		new FutureTask<Object>(new Runnable(){
			@Override
			public void run() {
				programStateChangeListener.newSolutionAdded(problemSolution);
			}
		}, null);
	}

	public synchronized int getSolutionsCount(){
		return problemsSolutions.size();
	}
	
	public synchronized ProblemSolution getProblemSolution(int i){
		return problemsSolutions.get(i);
	}

}
