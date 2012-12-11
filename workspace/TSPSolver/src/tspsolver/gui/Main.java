package tspsolver.gui;

public class Main {
	public static void main ( String [] args ) {
		PointList.save(PointList.generate(10,0,10),("d:/save.sav"));
		DistanceTable table = new DistanceTable(PointList.load("d:/save.sav"));
		table.print();
	}
}