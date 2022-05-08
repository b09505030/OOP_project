package com.mime.minefront.graphics;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	public Render(int width, int height) { //parameter comes from render = new Render(WIDTH, HEIGHT);
		this.width = width; //initializing an integer of 800
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < render.height; y++) { //in order to  fill the whole screen with two for loop
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= this.height) { //this can be omitted
				continue;
			}
				for (int x = 0; x < render.width; x++) {
					int xPix = x + xOffset;
					if (xPix <0 || xPix >= this.width) {
						continue;
					}
					
					//pixels[xPix + yPix * width] = render.pixels[x + y * render.width];
					
					int alpha = render.pixels[x + y * render.width]; //if there's nothing to render, don't bother rendering
					//transparency?
					if (alpha > 0) {  //avoid void pixels data if screen.java add  * (random.nextInt(5) / 4)
						//> < flips
						pixels[xPix + yPix * width] = alpha;
					
					}
					// System.out.println("x: "+ x + "y:"+y);
				}

		}

	}
}
