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
			
			//下面會使遊戲畫面變很暗，在原遊戲中和RGB調色相關
//			for (int i = 0; i < result.pixels.length; i++) {
//				int in = result.pixels[i];
//				int col = (in & 0xf) >> 2;
//				if (in == 0xffff00ff) col = -1;
//				result.pixels[i] = col;
//			}
			
			
			return result;
			
		} catch (Exception e) {
			System.out.println("CRASH!");
			throw new RuntimeException(e);
			
		}
	}
	
	
	public static int getCol(int c) {
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = (c) & 0xff;

		r = r * 0x55 / 0xff;
		g = g * 0x55 / 0xff;
		b = b * 0x55 / 0xff;

		return r << 16 | g << 8 | b;
	}



}
