package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;
import com.mime.minefront.input.Controller;

public class Render3D extends Render {

	public double[] zBuffer;
	public double renderDistance = 5000;
	private double forwardGlobal;
	private double forward, right, cosine, sine, up, walking;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
	}

	public void floor(Game game) {

		double floorPosition = 8;
		double ceilingPosition = 8; // 增加使天花板更高
		forward = game.controls.z / 0.8; // 前進 game.time % 100 / 20.0;//
		forwardGlobal = forward;
		right = game.controls.x / 0.8; // 右走

		up = game.controls.y;// Math.sin(game.time / 10.0) * 2 ;//
		walking = 0;
		double rotation = game.controls.rotation;// Math.sin(game.time % 1000.0 / 80);// // game.time/100.0
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;// changeable but need to use float

			double z = (floorPosition + up) / ceiling; // must after if ceiling statement or screen will rotation

			if (Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;
				z = (floorPosition + up + walking) / ceiling;
			}
			if (Controller.crouchWalk && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.2;
				z = (floorPosition + up + walking) / ceiling;
			}
			if (Controller.runWalk && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.8;
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

	}

	// wall generate (render) , BUT use in screen
	public void walls() {
		Random random = new Random(); // from Screen.java
		for (int i = 0; i < 10000; i++) {
			double xx = random.nextDouble();// plus to let obj move right
			double yy = random.nextDouble();
			double zz = 1.5 - forwardGlobal / 16; // minus to let closer

			int xPixel = (int) (xx / zz * height / 2 + width / 2);// /2
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) { // if object not in vision ,then
																					// don't rendering.
				pixels[xPixel + yPixel * width] = 0xfffff;

			}
		}

		for (int i = 0; i < 10000; i++) {
			double xx = random.nextDouble() - 1;// plus to let obj move right
			double yy = random.nextDouble();
			double zz = 1.5 - forwardGlobal / 16; // minus to let closer

			int xPixel = (int) (xx / zz * height / 2 + width / 2);// /2
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) { // if object not in vision ,then
																					// don't rendering.
				pixels[xPixel + yPixel * width] = 0xfffff;

			}
		}

		for (int i = 0; i < 10000; i++) {
			double xx = random.nextDouble() - 1;// plus to let obj move right
			double yy = random.nextDouble() - 1;
			double zz = 1.5 - forwardGlobal / 16; // minus to let closer

			int xPixel = (int) (xx / zz * height / 2 + width / 2);// /2
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) { // if object not in vision ,then
																					// don't rendering.
				pixels[xPixel + yPixel * width] = 0xfffff;

			}
		}

		for (int i = 0; i < 10000; i++) {
			double xx = random.nextDouble();// plus to let obj move right
			double yy = random.nextDouble() - 1;
			double zz = 1.5 - forwardGlobal / 16; // minus to let closer

			int xPixel = (int) (xx / zz * height / 2 + width / 2);// /2
			int yPixel = (int) (yy / zz * height / 2 + height / 2);
			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) { // if object not in vision ,then
																					// don't rendering.
				pixels[xPixel + yPixel * width] = 0xfffff;

			}
		}
	}

	public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
		double upCorrect = 0.062;// (Math.PI * 2.0) * 0.01 simplified
		double rightCorrect = 0.062;
		double forwardCorrect = 0.062;
		double walkCorrect = -0.062;

		double xcLeft = ((xLeft) - (right * rightCorrect)) * 2;
		double zcLeft = ((zDistanceLeft) - (forward * forwardCorrect)) * 2;

		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;// top_left //(-up * 0.062)let
																							// wall not move
		double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;// bottom_left
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight) - (right * rightCorrect)) * 2;
		double zcRight = ((zDistanceRight) - (forward * forwardCorrect)) * 2;

		double rotRightSideX = xcRight * cosine - zcRight * sine;///
		double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

		// what if left side of wall is greater than right side of wall ?(don't generate
		// negative pixel)
		if (xPixelLeft >= xPixelRight) {
			return;
		}

		int xPixelLeftInt = (int) (xPixelLeft);
		int xPixelRightInt = (int) (xPixelRight);

		if (xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}
		if (xPixelRightInt > width) {
			xPixelRightInt = width;
		}

		double yPixelLeftTop = (yCornerTL / rotLeftSideZ * height + height / 2.0);
		double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * height + height / 2.0);
		double yPixelRightTop = (yCornerTR / rotRightSideZ * height + height / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightSideZ * height + height / 2.0);

		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = 0 / rotLeftSideZ;
		double tex4 = 8 / rotLeftSideZ - tex3;///////// 8BITS only

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / (tex1 + (tex2 - tex1) * pixelRotation));

			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if (yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if (yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {

				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);
				try {// when error occurs on array,then use try catch to force code continue
					
					pixels[x + y * width] = Texture.floor.pixels[(xTexture & 7) + (yTexture & 7) * 8];
					//pixels[x + y * width] = xTexture * 100 + yTexture * 100 * 256;// 0x1B91E0;
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					continue;
				}
				zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;// change brightness from *8
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
