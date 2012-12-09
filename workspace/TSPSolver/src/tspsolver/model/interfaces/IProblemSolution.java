package tspsolver.model.interfaces;

import java.util.Properties;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.model.Problem;


/**
 * 
 * @author pawel
 *
 */
public interface IProblemSolution {
	
	public Problem getProblem();
	public Algorithm getAlgorithm();
	public Properties getAlgorithmProperties();
	public int getIterationsCount();
	public IterationResult getIterationResult(int i);
	public boolean isFinished();


}
