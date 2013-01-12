package tspsolver;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.cockroach.CockroachSolver;
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
		GeneticSolver geneticSolver = new GeneticSolver(
				currentProblem.getDistances(),
				geneticParameters.getIterationCount(),
				geneticParameters.getLiczbaOsobnikow(), 
				geneticParameters.getLiczbaKrzyzowan(), 
				geneticParameters.getLiczbaMutacji(), 
				geneticParameters.getLiczbaPodmianWObrebieMutacji()
			);
		ProblemSolution problemSolution = programState.addNewSolution(currentProblem, Algorithm.Genetic, geneticParameters);
		solveProblem(geneticSolver, problemSolution);
	}
	
	public void solveCurrentProblemWithCockRoach(CockroachParameters cockroachParameters, Problem currentProblem){
		CockroachSolver cockroachSolver = new CockroachSolver(currentProblem.getDistances(), cockroachParameters);
		ProblemSolution problemSolution = programState.addNewSolution(currentProblem, Algorithm.Cockroach, cockroachParameters);
		solveProblem(cockroachSolver, problemSolution);
	}
	
	private void solveProblem(ISolver solver, ProblemSolution problemSolution){
		SolutionRunnerThread srt = new SolutionRunnerThread(solver, problemSolution);
		srt.start();
	}
}
