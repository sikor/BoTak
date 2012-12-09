package tspsolver.model;

import java.util.Properties;

public class CockroachParameters {
	
	
	private int iterationCount;
	
	public CockroachParameters(int iterationCount){
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
