package tspsolver.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class PointList {
	
	/* Fields */
	private List <Point> pointList;
	
	/* Constructors */
	public PointList ( List <Point> _list ) { this.pointList = _list; }
	public PointList ( ) { this.pointList = new LinkedList <Point> (); }

	/* Methods */
	public Point getPoint ( int _index ) { return this.pointList.get(_index); }
	public void add ( Point _point ) { this.pointList.add(_point); }
	public int getSize ( ) { return this.pointList.size(); }
	public List <Point> get ( ) { return this.pointList; }
	
	public static boolean save ( PointList _pointList, String _path ) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(_path));
			out.writeInt(_pointList.getSize());
			for ( Point point : _pointList.get() )
				out.writeObject(point);
			return true;
		} catch ( IOException e ) {
			e.printStackTrace();
			return false;
		} finally { 
			if ( out != null ) try {
					out.flush();
					out.close();
			} catch ( IOException e ) {
				e.printStackTrace();
				return false;
			} 
		}
	}
	
	public static PointList load ( String _path ) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(_path));
			List <Point> list = new LinkedList <Point> ();
			int size = in.readInt();
			while ( size-- > 0 )
				list.add((Point)in.readObject());
			PointList pointList = new PointList(list);
			return pointList;
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		} finally {
			if ( in != null) try { in.close(); } catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	public static PointList generate ( int _size, int _a, int _b ) {
		PointList pointList = new PointList();
		for ( int i = 0; i < _size; ++i )
			pointList.add(new Point(_a,_b));
		return pointList;
	}
}
