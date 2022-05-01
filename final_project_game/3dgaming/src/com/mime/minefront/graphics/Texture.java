package com.mime.minefront.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Texture {
	public static Render floor = loadBitmap("/textures/floor.png");

	public static Render loadBitmap(String fileName) {
		InputStream is = Texture.class.getResourceAsStream(fileName);
		try {
			BufferedImage image = ImageIO.read(is);//Texture.class.getResource(fileName)
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width,height);
			image.getRGB(0,0,width, height,result.pixels,0,width);
			return result;
			//testfileassess.class.getresourceasstream
		} catch (Exception e) {
			System.out.println("CRASH!");
			throw new RuntimeException(e);
			
		}
		
	}

}
