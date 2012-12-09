package tspsolver;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.genetic.GeneticSolver;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;
import tspsolver.model.Problem;
import tspsolver.model.ProblemSolution;
import tspsolver.model.ProgramState;

public class GuiActionsListener {

	
	ProgramState programState;
	
	public GuiActionsListener(ProgramState programState){
		this.programState = programState;
	}
	
	
	
	public void solveCurrentProblemWithGenetic(GeneticParameters geneticParameters, Problem currentProblem){
		GeneticSolver geneticSolver = new GeneticSolver(currentProblem.getDistances(), geneticParameters.getAsProperties());
		ProblemSolution problemSolution = programState.addNewSolution(currentProblem, Algorithm.Genetic, geneticParameters.getAsProperties());
		solveProblem(geneticSolver, problemSolution);
	}
	
	public void solveCurrentProblemWithCockRoach(CockroachParameters cockroachParameters, Problem currentProblem){
		GeneticSolver geneticSolver = new GeneticSolver(currentProblem.getDistances(), cockroachParameters.getAsProperties());
		ProblemSolution problemSolution = programState.addNewSolution(currentProblem, Algorithm.Genetic, cockroachParameters.getAsProperties());
		solveProblem(geneticSolver, problemSolution);
	}
	
	private void solveProblem(ISolver solver, ProblemSolution problemSolution){
		SolutionRunnerThread srt = new SolutionRunnerThread(solver, problemSolution);
		srt.start();
	}
}
