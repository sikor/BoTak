package tsplibparser.test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import tspsolver.model.Point;
import tspsolver.parser.Parser;


public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		deleteNotEuc2d("resource");
	}
	
	
	
	public static void test() throws FileNotFoundException, IOException{
		File file = new File("resource/a280.tsp");
		List<Point> points = Parser.parseTspLibFile(new FileReader(file));
		System.out.println(points);
	}
	
	
	public static void deleteNotEuc2d(String dir) throws FileNotFoundException, IOException{
		File directory = new File(dir);
		for(File f: directory.listFiles()){
			System.out.println(f.getName());
			boolean error = false;
			FileReader fr = null;
			try{
				fr = new FileReader(f);
				Parser.parseTspLibFile(fr);
				fr.close();
			}catch(IllegalStateException e){
				e.printStackTrace();
				error = true;
			}finally{
				if(fr!= null)
					fr.close();
			}
			if(error){
				System.out.println("delet ? "+ f.delete());
			}
		}
		
	
	}
}
