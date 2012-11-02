package tspsolver.algorithms.cockroach;

import java.awt.Point;
import java.lang.Math;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;


public class CocroachSwarmOptimization {
	private List<Point> cities;
	private List<List<Double>> distanceGrid;
	/* We number the cities starting with 1. */
	private List<List<Integer>> cocroaches;
	private int population = 60, iterations = 200;
	private double step = 1.5;
	private double w = 0.618;
	private Point startingPoint;		// Unused for now.
	
	public void setCities(List<Point> c){
		cities = new ArrayList(c);
		createDistanceGrid(c);
	}
	
	public void setIterations(int it){
		iterations = it;
	}
	
	public void setPopulation(int pop){
		population = pop;
	}
	
	public void setStartingPoint(Point st){
		startingPoint = st;
	}
	
	public void setStep(double st){
		step = st;
	}
	
	private void createDistanceGrid(List<Point> cities) {
		distanceGrid = new ArrayList<List<Double>>(cities.size());
		for(int i=0; i<cities.size(); i++) {
			distanceGrid.set(i, new ArrayList<Double>(cities.size()));
			for(int j=0; j<cities.size(); j++) {
				double result = 0;
				result += Math.sqrt( 
					Math.pow(cities.get(i).getX() - cities.get(j).getX(), 2) + 
					Math.pow(cities.get(i).getY() - cities.get(j).getY(), 2) );	
				distanceGrid.get(i).set(j, result);
			}
		}
	}
	
	
	/* Eeach cocroach is a fist of randomly shuffled numbers of cities. */
	private void generateCocroaches() {
		for(int i=0; i<population; i++) {
			List<Integer> cocroach = new ArrayList<Integer>(cities.size());
			for(int j=1; j<=cities.size(); j++) 
				cocroach.add(i);
			Collections.shuffle(cocroach);
			cocroaches.add(cocroach);
		}
	}

	// Deprecated
	/* swaps two cities for a given cocroach */
	private void step(List<Point> cocroach, Point city1, Point city2){
		int firstIndex = -1;
		boolean foundFirst = false;
		for(int i=0; i < cocroach.size() && !foundFirst; i++) {
			Point current = cocroach.get(i);
			if(current.equals(city1) || current.equals(city2)){
				if(foundFirst) {
					Point tmp = cocroach.get(i);
					cocroach.set(i, cocroach.get(firstIndex));
					cocroach.set(firstIndex, tmp);
					break;
				}
				firstIndex = i;
				foundFirst = true;
			}	
		}
	}
	
	private double calculateDistance(List<Integer> cocroach){
		double result = 0;
		for(int i=0; i<cocroach.size()-1; i++)
			result += distanceGrid.get(cocroach.get(i)).get(cocroach.get(i+1));
		return result;
	}
	
	
	/* Option 1: cocroaches try to have the same order of cities, also when 
	 * 			 it comes to exact position of cities.
	 * Option 2: cocroaches try to achieve only the same order of some number
	 * 			 of consecutive cities.
	 * We are doing option 2, because with the assumption, that all cocroaches
	 * have some good idea of an ideal path, the 1st option would make much more
	 * changes to their road, which we assume may have a negative effect.
	 */

	// Returns the new distance after making at most maxSteps steps.
	private double crawlToLOF(List<Integer> cocroach, List<Integer> LOF, int maxSteps, double LOFval){
		double maybeNewLOF = -1;
		while(maxSteps-->0){
			if(LOF.equals(cocroach))
				return 0;
			
			int LOFLastCity = LOF.get(LOF.size()-1);
			
			int index;
			do {
				index = (int) Math.round(Math.random()*(cities.size()-1));
			}while(LOFLastCity != cocroach.get(index));
			
			int prevCity = cocroach.get(index);
			int nextCity = 0;
					
			for(int i=0; i<LOF.size()-1 && nextCity==0; i++)
				if(LOF.get(i)==prevCity)
					nextCity = LOF.get(i+1);
			
			boolean citySwapped = false;
			for(int i=0; i<cocroach.size() && !citySwapped; i++){
				if(cocroach.get(i)==nextCity) {
					cocroach.set(i, cocroach.get(index+1));
					cocroach.set(index+1, nextCity);
					citySwapped = true;
				}
			}
				
			maybeNewLOF = calculateDistance(cocroach);
			if(maybeNewLOF <LOFval)
				return maybeNewLOF;
		}
		return maybeNewLOF;
	}
	
	
	public void optimize(){
		generateCocroaches();
		List<Integer> Pg = cocroaches.get(0); 	// Potential global (optimum)
		int PgIndex = 0;
		double PgVal = calculateDistance(Pg);		
		
		for(int i=0; i<cocroaches.size(); i++){
			double currentDist = calculateDistance(cocroaches.get(i));
			if(currentDist < PgVal) {
				PgVal = currentDist;
				Pg = cocroaches.get(i);
				PgIndex = i;
			}
		}
		
		boolean stop = false;
		
		while(!stop && iterations-->0) {
			
			
			// Step 1: All of the cockroaches crawl to Potential Global Optimum (Pg)
			/*
			    {
			    evaluate the solution which is generated when cockroach crawls to LOF.
				If(solution is better than LOF)
					{update LOF with the solution;}
				}
				The cocroach are set a new solution(new nest)
			 */
			
			for(int i=0; i<cocroaches.size(); i++) 
				if(i!=PgIndex){
					int maxSteps = 5;
					double oldDistance = calculateDistance(cocroaches.get(i));
					double newDistance = crawlToLOF(cocroaches.get(i), Pg, maxSteps, PgVal);
					
					/* Modification: 
					 * a) if the crawling has made the new distance longer
					 * b) or the cocroach has exactly the same order of cities as Pg 
					 * we eat that cocroach. 
					 * --> Ruthless behavior */
					if(newDistance > 0 && newDistance<PgVal) {
						Pg = cocroaches.get(i);
						PgVal = newDistance;
						PgIndex = i;
					}	
					else if(newDistance == 0 || newDistance > oldDistance) 
						/* Ruthless behavior */
						cocroaches.remove(i);
					else
						System.out.println("We should never get here.");
				}
			
			
			// Step 2: Dispersing behavior
			
			
			// Step 3: Ruthless behavior (save LOF) - this step can be done as well in Step 2.
			
			
			// Step 4: find lof
		}
		
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		double[] oliver30list = {
			54, 67, 
			54, 62, 
			37, 84, 
			41, 94, 
			2, 99, 
			7, 64,
			25, 62,
			22, 60,
			18, 54,
			4, 50,
			13, 40,
			18, 40,
			24, 42,
			25, 38,
			44, 35,
			41, 26,
			45, 21,
			58, 35,
			62, 32,
			82,  7,
			91, 38,
			83, 46,
			71, 44,
			64, 60,
			68, 58,
			83, 69,
			87, 76,
			74, 78,
			71, 71,
			58, 69};	
		
		List<Point> oliver30 = new ArrayList<Point>();
		for(int i=0; i<oliver30list.length; i+=2) {
			Point p = new Point();
			p.setLocation(oliver30list[i], oliver30list[i+1]);
			oliver30.add(p);
		}
		
		// The shortest cycle length is 423.741
		// 1  3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23  25 24  26 27 28 29 30  2
		
	}

}
