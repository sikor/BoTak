package tspsolver.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

import tspsolver.model.Point;

public class Parser {
	
	
	/**
	 * Parses input reader and if it is of type EUC_2D returns list of points.
	 * never returns null
	 * throws Illegal state exception when type is different than EUC_2D or file is in incorrect format.
	 * example: 
	 * FileReader fr = null;
			try{
				File file = new File("resource/a280.tsp");
				fr = new FileReader(f);
				List<Point> points = Parser.parseTspLibFile(fr);
				fr.close();
			}catch(IllegalStateException e){
				e.printStackTrace();
				error = true;
			}finally{
				if(fr!= null)
					fr.close();
			}
					
	 * @param in
	 * @return List of immutable points.
	 * @throws IOException
	 */
	public static List<Point> parseTspLibFile(Reader in) throws IOException, IllegalStateException{
		StreamTokenizer tokenizer = new StreamTokenizer(in);
		tokenizer.eolIsSignificant(false);
		tokenizer.wordChars('_', '_');
		tokenizer.whitespaceChars(':', ':');
		List<Point> points = null;

		while(tokenizer.nextToken() != StreamTokenizer.TT_EOF){
			if(tokenizer.ttype != StreamTokenizer.TT_WORD){
					continue;
			}
			if(tokenizer.sval.equals("EDGE_WEIGHT_TYPE")){
				String type = nextWord(tokenizer);
				if(! type.equals("EUC_2D")){
					throw new IllegalStateException("expected EUC_2D type but got: "+type);
				}
			}
		    if(tokenizer.sval.equals("NODE_COORD_SECTION")) {
		        points = readPoints(tokenizer);
		        break;
		    }
		}
		if(points == null){
			throw new IllegalStateException("parser did not find any points in file");
		}
		return points;
	}

	private static String nextWord(StreamTokenizer tokenizer) throws IOException {
		tokenizer.nextToken();
		if(tokenizer.ttype != StreamTokenizer.TT_WORD){
			throw new IllegalStateException("epected word type but got: "+tokenizer.nval);
		}
		return tokenizer.sval;
	}

	private static List<Point> readPoints(StreamTokenizer tokenizer) throws IOException {
		List<Point> points = new ArrayList<Point>();
		while(tokenizer.nextToken() == StreamTokenizer.TT_NUMBER){
			double x = nextNumber(tokenizer);
			double y = nextNumber(tokenizer);
			points.add(new Point(x, y));
		}
		return points;
	}

	private static double nextNumber(StreamTokenizer tokenizer) throws IOException {
		if(tokenizer.nextToken() != StreamTokenizer.TT_NUMBER){
			throw new IllegalStateException("parser expected number but found: "+tokenizer.sval);
		}
		double x = tokenizer.nval;
		return x;
	}

}
