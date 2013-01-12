package tspsolver.gui;


import java.util.Arrays;
import java.util.List;

import tspsolver.GuiActionsListener;
import tspsolver.Initializator;
import tspsolver.SolvedProblems;
import tspsolver.model.GeneticParameters;
import tspsolver.model.Point;
import tspsolver.model.Problem;

public class Main {
	
	
	
	public static void main(String[] args) {
		ModelChangeListener modelChangeListener = new ModelChangeListener(); //Gui implements change listener class
		Initializator initializator = new Initializator(modelChangeListener); // Gui initialize model and controller
		GuiActionsListener actionsListener = initializator.getActionsListener(); //Gui get api interface for calling actions
		SolvedProblems solvedProblems = initializator.getSolvedProblems(); //GUI takes lists of solvedProblems if want
		
		
		/**
		 * bind buttons to actions
		 * and read data from labels
		 * 
		 * 
		 */
		
		int iterationCount = 10; //read algo parameters from labels
		List<Point>	 pointList = Arrays.asList(new Point(1.0, 2.0), new Point(2.0, 3.0), new Point(2.5, 3.0)); //read points from labels or file
		
		Problem currentProblem = new Problem(pointList); //create problem
		
		GeneticParameters geneticParameters = new GeneticParameters(iterationCount, 4, 4, 2, 2); //create parameters Class
		actionsListener.solveCurrentProblemWithGenetic(geneticParameters, currentProblem);   //run action
	}

}
