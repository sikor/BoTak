package tspsolver.model;

import java.util.Properties;

public class GeneticParameters {

	
	private int iterationCount;
	
	public GeneticParameters(int iterationCount){
		this.iterationCount = iterationCount;
	}
	
	public Properties getAsProperties() {
		Properties prop = new Properties();
		prop.put("iterationCount", iterationCount);
		return prop;
	}


	public int getIterationCount() {
		return iterationCount;
	}

}
