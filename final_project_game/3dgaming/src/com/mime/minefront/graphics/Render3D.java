package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;
import com.mime.minefront.input.Controller;

public class Render3D extends Render {

	public double[] zBuffer;
	public double renderDistance = 5000;
	

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
	}

	public void floor(Game game) {

		double floorPosition = 8;
		double ceilingPosition = 8; // 增加使天花板更高
		double forward = game.controls.z / 0.8; // 前進
		double right = game.controls.x / 0.8; // 右走
		double up = game.controls.y;// Math.sin(game.time / 10.0) * 2 ;//
		double walking = Math.sin(game.time / 6.0) * 0.5;
		if (Controller.crouchWalk) {
			walking = Math.sin(game.time / 6.0) * 0.2;
		}
		if (Controller.runWalk) {
			walking = Math.sin(game.time / 6.0) * 0.8;
		}

		double rotation =0;// game.controls.rotation; // game.time/100.0
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;// changeable but need to use float

			double z = (floorPosition + up) / ceiling; // must after if ceiling statement or screen will rotation

			if (Controller.walk) {
				z = (floorPosition + up + walking) / ceiling;

			}

			if (ceiling < 0) {
				z = (ceilingPosition - up) / -ceiling; // fix distortion on screen
				if (Controller.walk) {
					z = (floorPosition - up - walking) / -ceiling;

				}
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;

				double xx = (depth * cosine + z * sine);
				double yy = (z * cosine - depth * sine);
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);

				zBuffer[x + y * width] = z;

				/**
				 * change to texture
				 */

				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 8];// using ((xPix & 15) * 16) |
																							// ((yPix & 15) * 16) << 8;
				// and bitwise operator
				//

				if (z > 500) { // 消失點設定(可提升frame per second) 醜醜的
					pixels[x + y * width] = 0;
				}

				// because it is to limit xx and yy
				// System.out.println(xx);
				// System.out.println(xx);

			}
		}

		// wall generate (render)
		Random random = new Random(); // from Screen.java
		for (int i = 0; i < 10000; i++) {
			double xx = random.nextDouble();
			double yy = random.nextDouble();
			double zz = 2; // minus to 

			int xPixel = (int) (xx / zz * height / 2 + width / 2);// /2
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) { // if object not in vision ,then
																					// don't rendering.
				pixels[xPixel + yPixel * width] = 0xfffff;

			}
		}

	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));

			if (brightness < 0) {
				brightness = 0;
			}

			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}
