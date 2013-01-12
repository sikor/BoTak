package tspsolver.model;

import java.util.Properties;

import tspsolver.annotations.Immutable;


@Immutable
public class AlgorithmParameters {

	private final Integer iterationCount;
	
	public AlgorithmParameters(int iterationCount){
		this.iterationCount = iterationCount;
	}
	
	public Properties getAsProperties(){
		Properties prop = new Properties();
		prop.setProperty("iterationCount", iterationCount.toString());
		return prop;
		
	}
	
	public int getIterationCount() {
		return iterationCount;
	}
}
