package com.mime.minefront.graphics;

//import com.mime.minefront.Display;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	//private Display display; // import display width and height but actually can just use Display
	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= height) {
				continue;
			}
				for (int x = 0; x < render.width; x++) {
					int xPix = x + xOffset;
					if (xPix <0 || xPix >= width) {
						continue;
					}
					
					
					
					int alpha = render.pixels[x + y * render.width];
					
					if (alpha>0) {  //avoid void data if screen.java add (random.nextInt(5)/4) //> < flips
					pixels[xPix + yPix * width] = alpha;
					// System.out.println("x: "+ x + "y:"+y);
					}
				}

		}

	}

}
