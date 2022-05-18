package graphics;

import java.awt.image.BufferedImage;
//import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Texture {
	public static Render blocks = loadBitmap("/blocks.png");
	//public static Render floor = loadBitmap("/textures/floor1.png");

	private static Render loadBitmap(String fileName) {
		InputStream is = Texture.class.getResourceAsStream(fileName);
		try {
			BufferedImage image = ImageIO.read(is); //ImageIO.read(new FileInputStream(fileName));//comments say!?
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width,height);
			image.getRGB(0,0,width, height,result.pixels,0,width);
			return result;
			
		} catch (Exception e) {
			System.out.println("CRASH!");
			throw new RuntimeException(e);
			
		}
	}
	
}