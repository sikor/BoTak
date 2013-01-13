package gui.guielements;

import java.util.List;

import javax.swing.SwingWorker;

import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.IterationResult;

public class SolutionWorker extends SwingWorker<Void, IterationResult> {

	ISolver solver;
	AlgorithmMainPanel panel;
	public SolutionWorker(ISolver solver, AlgorithmMainPanel panel){
		this.solver = solver;
		this.panel = panel;
	}
	@Override
	protected Void doInBackground() throws Exception {
		
		IterationResult result;
		while((result = solver.nextIteration()) != null && !this.isCancelled()){
			publish(result);
		}		
		return null;
	}
	
	@Override
	protected void process(List<IterationResult> result){
		this.setProgress(this.getProgress()+result.size());
		panel.addIterationResults(result);
	}

}
