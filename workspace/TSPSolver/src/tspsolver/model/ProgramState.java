package tspsolver.model;

public class ProgramState {
	 
	private SolvedProblems solvedProblems;
	private Problem currentProblem;
	private GeneticParameters geneticParameters;
	private CockroachParameters cockroachParameters;
	
	
	
	
	public SolvedProblems getSolvedProblems() {
		return solvedProblems;
	}
	public Problem getCurrentProblem() {
		return currentProblem;
	}
	public GeneticParameters getGeneticParameters() {
		return geneticParameters;
	}
	public CockroachParameters getCockroachParameters() {
		return cockroachParameters;
	}
	
	
}
