package tspsolver.gui;

import tspsolver.algorithms.IterationResult;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;



/**
 * example implementation of IModelChangeListener
 * 
 * this class should be implemented by GUI
 * 
 * for informations check java doc of IModelChangeListener interface.
 * @author pawel
 *
 */
public class ModelChangeListener implements IModelChangeListener {

	@Override
	public synchronized void newSolutionAdded(IProblemSolution problemSolution) {
		System.out.println("new solution added");

	}

	@Override
	public synchronized void newIterationResultAdded(IProblemSolution problemSolution,
			IterationResult ir) {
		System.out.println("new iteratoin result added " + ir.getIterationNumber()+ " "+problemSolution.getAlgorithm());

	}

	@Override
	public synchronized void solutionFinished(IProblemSolution problemSolution) {
		System.out.println("solution finished");
		

	}

}
