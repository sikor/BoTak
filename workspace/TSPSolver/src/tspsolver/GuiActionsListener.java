package tspsolver;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.genetic.GeneticSolver;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;
import tspsolver.model.Problem;

public class GuiActionsListener {

	
	private final ProgramState programState;
	
	public GuiActionsListener(ProgramState programState){
		this.programState = programState;
		
	}
	
	
	public void solveCurrentProblemWithGenetic(GeneticParameters geneticParameters, Problem currentProblem){
		GeneticSolver geneticSolver = new GeneticSolver(currentProblem.getDistances(), new java.util.Properties());
		ProblemSolution problemSolution = programState.addNewSolution(currentProblem, Algorithm.Genetic, geneticParameters);
		solveProblem(geneticSolver, problemSolution);
	}
	
	public void solveCurrentProblemWithCockRoach(CockroachParameters cockroachParameters, Problem currentProblem){
		GeneticSolver geneticSolver = new GeneticSolver(currentProblem.getDistances(), cockroachParameters.getAsProperties());
		ProblemSolution problemSolution = programState.addNewSolution(currentProblem, Algorithm.Cockroach, cockroachParameters);
		solveProblem(geneticSolver, problemSolution);
	}
	
	private void solveProblem(ISolver solver, ProblemSolution problemSolution){
		SolutionRunnerThread srt = new SolutionRunnerThread(solver, problemSolution);
		srt.start();
	}
}
