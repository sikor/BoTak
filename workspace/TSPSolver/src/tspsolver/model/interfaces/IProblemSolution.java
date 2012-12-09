package tspsolver.model.interfaces;

import java.util.Properties;

import tspsolver.algorithms.Algorithm;
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

}
