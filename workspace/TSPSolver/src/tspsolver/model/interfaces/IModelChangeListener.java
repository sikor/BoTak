package tspsolver.model.interfaces;

import tspsolver.algorithms.IterationResult;



/**
 * This is interface for actions callback.
 * After gui call an action, algorithms api will notify gui about algorithms progress by calling this interface methods.
 * 
 * This interface implementation should be thread safe since it will be called from many potentially running algorithms
 * 
 * 
 * @author pawel
 *
 */
public interface IModelChangeListener {

	
	
	/**
	 * when calling solveAction api immediatly calls this method with reference to solution where results will be stored.
	 * Each solveAction task has one corresponding ProblemSolution. 
	 * @param problemSolution
	 */
	public void newSolutionAdded(IProblemSolution problemSolution);

	/**
	 * When a running algorithm finishes iteration, it calls this method with refernce to problemSolution which was given in the newSolutionAdded method and reference to results of the last iteration.
	 * Each ProblemSolution has many iterationResults
	 * @param problemSolution
	 * @param iterationResult
	 */
	public void newIterationResultAdded(IProblemSolution problemSolution, IterationResult iterationResult);

	
	
	/**
	 * When algorithm finishes all iterations it calls this method with reference to it solution
	 * @param problemSolution
	 */
	public void solutionFinished(IProblemSolution problemSolution);
	
	
	

}
