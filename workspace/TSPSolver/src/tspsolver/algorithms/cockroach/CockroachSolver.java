package tspsolver.algorithms.cockroach;

import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import tspsolver.algorithms.ISolver;
import tspsolver.algorithms.IterationResult;

public class CockroachSolver implements ISolver{
	
	Random random = new Random();

    double distances[][] = {{0, 4, 2, 4, 4},
    						{4, 0, 4, 6, 2},
    						{2, 4, 0, 2, 2},
    						{4, 6, 2, 0, 4},
    						{4, 2, 2, 4, 0}};
    int citiesNumber = distances[0].length;

    private List<Cockroach> cockroaches = new ArrayList<Cockroach>();      // cockroach is a path
    Cockroach bestCockroach;
    double bestDistance; 
    int bestIndex;
    
    private int iterationNumber = 0;
    private int population = 5;
    private int iterations = 3;
    private int steps = 1;

//    public CockroachSolver(double [][]distances){
//    }

    public void setIterations(int it){
        iterations = it;
    }

    public void setPopulation(int pop){
        population = pop;
    }

    public void setStep(int st){
        steps = st;
    }

    public class Cockroach{
    	List<Integer> cities = new ArrayList<Integer>();
    	
	    // Step of cockroach crawling
	    /* swaps two cities for a given cockroach */
	    private void moveSomewhere(int city1, int city2){
	        int firstIndex = -1;
	        boolean foundFirst = false;
	        int tmp;
	        for(int i=0; i < citiesNumber; i++) {
	            int current = cities.get(i);
	            if(current == city1 || current == city2){
	            	if(foundFirst) {
	                    tmp = cities.get(i);
	                    cities.set(i, cities.get(firstIndex));
	                    cities.set(firstIndex, tmp);
	                    break;
	                }
	                firstIndex = i;
	                foundFirst = true;
	            }
	        }
	    }
	
		public int [] toIntArray() {
			int tab[] = new int[citiesNumber], i = 0;
			for(Integer city : cities) {
				tab[i] = city;
				++i;
			}
			return tab;
		}
	    
	    public double getDistance(){
	        double result = 0;
	        for(int i=0; i < citiesNumber - 1 ; i++)
	            result += distances[cities.get(i)][cities.get(i+1)];
	        return result;
	    }
	
	    /* Option 1: cockroaches try to have the same order of cities, also when
	    * 			 it comes to exact position of cities.
	    * Option 2: cockroaches try to achieve only the same order of some number
	    * 			 of consecutive cities.
	    * We are doing option 2, because with the assumption, that all cockroaches
	    * have some good idea of an ideal path, the 1st option would make much more
	    * changes to their road, which we assume may have a negative effect.
	    */    
	    private void crawlToBest(){
	    	int step = 0;
	    	while(step++<steps){
	            int index;
	            
	            // losujemy miasto inne ni¿ ostatnie miasto u lokalnie najlepszego karalucha
	            do {
	            	index = random.nextInt(citiesNumber-1);
	            }while(cities.get(index) == bestCockroach.cities.get(citiesNumber-1));
	            
	            // szukamy miasta nastepujacego po wylosowanym miescie u lokalnie najlepszego karalucha  
	            int prevCity = cities.get(index);
	            int nextCity = bestCockroach.cities.get(0);
	            for(int i=0; i < citiesNumber - 1 && nextCity==bestCockroach.cities.get(0); i++)
	                if(bestCockroach.cities.get(i)==prevCity)
	                    nextCity = bestCockroach.cities.get(i+1);
	
	            // podstawiamy nextCity jako nastêpne miasto u naszego karalucha
	            boolean citySwapped = false;
	            for(int i=0; i < citiesNumber && !citySwapped; i++){
	                if(cities.get(i)==nextCity) {
	                    cities.set(i, cities.get(index+1));
	                    cities.set(index+1, nextCity);
	                    citySwapped = true;
	                }
	            }
	    	}
	    }
	    
	    private void disperse(){
	    	int step = 0;
	    	while(step++ < steps){
	    		this.moveSomewhere(cities.get(random.nextInt(citiesNumber)), cities.get(random.nextInt(citiesNumber)));
	    	}
	    }
	    
	    private void eat(Cockroach cockroach){
	    	for(int i = 0; i < citiesNumber; i++){
	    		cockroach.cities.set(i, cities.get(i));
	    	}
	    }
    }
	    
    /* Eeach cockroach is a list of randomly shuffled numbers of cities. */
    private void generateCockroaches() {
        for(int i=0; i < population; i++) {
            Cockroach cockroach = new Cockroach();
            for(int j = 0 ; j < citiesNumber; j++)
                cockroach.cities.add(j);
            Collections.shuffle(cockroach.cities);
            cockroaches.add(cockroach);
        }
    }
    
    private void allCrawlToBest(){
    	for(int i=0; i<population; i++){
    		Cockroach cockroach = cockroaches.get(i);
      		if(i != bestIndex){		// zwycieskiego karalucha sie nie zmienia
    			cockroach.crawlToBest();
                if(cockroach.getDistance() < bestDistance) {
                	bestCockroach = cockroach;
                    bestDistance = cockroach.getDistance();
                    bestIndex = i;
                }
            }    
    	}
    }	
    
    private void allDisperse(){
		for(int i=0; i<population; i++){
			Cockroach cockroach = cockroaches.get(i);
 			if(i != bestIndex){		// zwycieskiego karalucha sie nie zmienia
                cockroach.disperse();
                if(cockroach.getDistance() < bestDistance) {
                    bestCockroach = cockroach;
                    bestDistance = cockroach.getDistance();
                    bestIndex = i;
                }
            }
		}
    }
    
    private void ruthlessBehaviour(){
        bestCockroach.eat(cockroaches.get(random.nextInt(population)));
    }
    
    @Override
    public IterationResult nextIteration() {
    	if(iterationNumber > iterations){
    		return null;
    	}
    	
    	if(cockroaches.isEmpty()) {
    		generateCockroaches();
			bestCockroach = cockroaches.get(0);
    	    bestDistance = bestCockroach.getDistance();
    	    bestIndex = 0;
    		for (int i = 1; i < population ; i++){
    			if (cockroaches.get(i).getDistance() < bestDistance){
    				bestCockroach = cockroaches.get(i);
    	    	    bestDistance = bestCockroach.getDistance();
    	    	    bestIndex = i;
    			}
    		}
    	} 
    	else {
    		//System.out.println("Nie spimy, biegamy");
    		allCrawlToBest();
    		allDisperse();
    		ruthlessBehaviour();
    	}
    	
		System.out.println("iteration: " + iterationNumber);
		
		System.out.println("Kolejnosc zwiedzanych miast:");
		for(int i : bestCockroach.toIntArray())
			System.out.print(i + ", ");
		
		System.out.println("\nNajlepszy rezultat to dystans: " + bestCockroach.getDistance());
    	
    	IterationResult result = new IterationResult(bestCockroach.toIntArray(), bestCockroach.getDistance(), iterationNumber++);
    	return result;
    }
}