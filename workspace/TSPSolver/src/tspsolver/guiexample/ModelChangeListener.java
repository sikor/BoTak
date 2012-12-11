package tspsolver.guiexample;

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
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void newIterationResultAdded(IProblemSolution problemSolution,
			IterationResult ir) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void solutionFinished(IProblemSolution problemSolution) {
		// TODO Auto-generated method stub

	}

}
