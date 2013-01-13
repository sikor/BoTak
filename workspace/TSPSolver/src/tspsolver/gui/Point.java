package tspsolver.gui;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public class Point implements Serializable {
	
	/* Fields */
	private double xAxis;
	private double yAxis;
	static final private long serialVersionUID = 0;
	
	/* Constructors */
	public Point ( ) { 
		this.xAxis = 0.0;
		this.yAxis = 0.0;
	}

	public Point ( double _x, double _y ) {
		this.xAxis = _x;
		this.yAxis = _y;
	}

	public Point ( int _a, int _b ) {
		this.xAxis = Math.random() * (_b-_a) + _a;
		this.yAxis = Math.random() * (_b-_a) + _a;
	}

	/* Methods */
	public double [] get ( ) {
		double [] table = { this.xAxis, this.yAxis };
		return table;
	}
	
	public double getX(){
		return xAxis;
	}
	
	public double getY(){
		return yAxis;
	}

	public void set ( double _xAxis, double _yAxis ) {
		this.xAxis = _xAxis;
		this.yAxis = _yAxis;
	}

	public double calcDistance ( Point _point ) {
		double [] table = _point.get();
		return Math.sqrt((this.xAxis + table[0])*(this.xAxis + table[0])+(this.yAxis + table[1])*(this.yAxis + table[1]));
	}

	public void writeExternal ( ObjectOutput _out ) throws IOException {
		_out.writeDouble(this.xAxis);
		_out.writeDouble(this.yAxis);
	}

	public void readExternal ( ObjectInput _in ) throws IOException, ClassNotFoundException {
		this.xAxis = _in.readDouble();
		this.yAxis = _in.readDouble();
	}
}