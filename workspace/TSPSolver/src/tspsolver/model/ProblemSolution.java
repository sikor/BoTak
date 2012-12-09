package tspsolver.model;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.FutureTask;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;

public class ProblemSolution implements IProblemSolution {
	
	
	private Problem problem;
	private ArrayList<IterationResult> iterationResults;
	private boolean isFinished;
	private Algorithm algorithm;
	private IModelChangeListener programStateChangeListener;
	private Properties algorithmProperties;
	
	public ProblemSolution(IModelChangeListener programStateChangeListener, Problem problem, Algorithm algorithm, Properties algorithmProperties) {
		super();
		this.problem = problem;
		this.isFinished = false;
		this.algorithm = algorithm;
		this.algorithmProperties = algorithmProperties;
	}
	
	public synchronized void addIterationResult(final IterationResult ir){
		if(isFinished){
			throw new IllegalStateException("cant add iteration result to finished solution");
		}
		iterationResults.add(ir);
		final ProblemSolution problemSolution = this;
		new FutureTask<Object>(new Runnable() {
			@Override
			public void run() {
				programStateChangeListener.newIterationResultAdded(problemSolution, ir);
			}
		}, null);
	}

	public synchronized void setFinished(){
		this.isFinished = true;
		
		final ProblemSolution problemSolution = this;
		new FutureTask<Object>(new Runnable() {
			@Override
			public void run() {
				programStateChangeListener.solutionFinished(problemSolution);
			}
		}, null);
	}
	
	public synchronized boolean isFinished(){
		return isFinished;
	}

	public Problem getProblem() {
		return problem;
	}


	public Algorithm getAlgorithm() {
		return algorithm;
	}
	
	public Properties getAlgorithmProperties(){
		return algorithmProperties;
	}
	
	public synchronized int getIterationsCount(){
		return iterationResults.size();
	}
	
	public synchronized IterationResult getIterationResult(int i){
		return iterationResults.get(i);
	}

}
