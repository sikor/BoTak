package tspsolver;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

import tspsolver.model.interfaces.IModelChangeListener;

public class SolvedProblems {
	
	
	private final ArrayList<ProblemSolution> problemsSolutions;
	private final IModelChangeListener programStateChangeListener;
	private final ExecutorService guiExecutor;
	
	public SolvedProblems(ExecutorService guiExecutor, IModelChangeListener programStateChangeListener){
		this.programStateChangeListener = programStateChangeListener;
		problemsSolutions = new ArrayList<ProblemSolution>();
		this.guiExecutor = guiExecutor;
	}

	synchronized void addSolution(final ProblemSolution problemSolution) {
		problemsSolutions.add(problemSolution);
		guiExecutor.submit(new Runnable(){
			@Override
			public void run() {
				programStateChangeListener.newSolutionAdded(problemSolution);
			}
		});
	}

	public synchronized int getSolutionsCount(){
		return problemsSolutions.size();
	}
	
	public synchronized ProblemSolution getProblemSolution(int i){
		return problemsSolutions.get(i);
	}

}
