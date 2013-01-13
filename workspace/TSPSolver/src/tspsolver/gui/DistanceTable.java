package tspsolver.gui;

public class DistanceTable {
	
	/* Fields */
	private double [][] distanceTable;
	private int size = 0;

	/* Constructors */
	public DistanceTable ( PointList _list ) { this.calc(_list); }
	
	/* Methods */
	public double [][] get ( ) { return this.distanceTable; }
	
	public void calc ( PointList _list ) {
		this.size = _list.getSize();
		this.distanceTable = new double [size][size];
		Point point = null;

		for ( int i = 0; i < this.size; ++i ) {
			point = _list.getPoint(i);
			for ( int j = i; j < this.size; ++j )
				if ( i == j ) this.distanceTable[i][i] = 0.0;
				else this.distanceTable[i][j] = this.distanceTable[j][i] = point.calcDistance(_list.getPoint(j));
		}
	}
	
	public void print ( ) {
		for ( int i = 0; i < this.size; ++i ) {
			for ( int j = 0; j < this.size; ++j )
				System.out.print(distanceTable[i][j]+" | ");
			System.out.println();
		}
	}
}
