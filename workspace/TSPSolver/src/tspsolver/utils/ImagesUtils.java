package tspsolver.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagesUtils {

	static public ImageIcon getImageIcon(String path){
			//java.net.URL imgURL = getClass().getResource(path);
			BufferedImage myPicture=null;
			try {
				myPicture = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ImageIcon(myPicture);
		}
}
