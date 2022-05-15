package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;
import com.mime.minefront.input.Controller;
import com.mime.minefront.level.Block;
import com.mime.minefront.level.Level;

public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zBufferWall; //solve perspective problem
	public double renderDistance = 5000;
	// Random random = new Random();
	private double forwardGlobal;
	private double forward, right, up, cosine, sine, walking;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		zBufferWall = new double[width];
	}

	public void floor(Game game) {

		for(int x = 0; x < width; x++) { //solve perspective problem
			zBufferWall[x] = 0;
		}
		
		double floorPosition = 8;
		double ceilingPosition = 8; // set 800 can get rid of ceiling

		forward = game.controls.z;// .time % 100 / 20.0; // test for wall //game.controls.z; // �e�i
		forwardGlobal = forward;
		right = game.controls.x; // �k��
		up = game.controls.y;// Math.sin(game.time / 10.0) * 2

		walking = 0; // reset walking to 0, avoid stop at the moment while key released.

		double rotation = game.controls.rotation;// Math.sin(game.time / 40.0) * 0.5;// 0; // test for wall //game.time
													// / 100.0; // animate
		// the rotation
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;

			double z = (floorPosition + up) / ceiling;

//			if (ceiling < 0) { // the same way movement
//				ceiling = -ceiling;
//			}

			if (Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.5;
				z = (floorPosition + up + walking) / ceiling;

			}

			if (Controller.crouchWalk && Controller.walk) { // �ۤU�ɤ��n�̨���j
				walking = Math.sin(game.time / 6.0) * 0.25;
			}
			if (Controller.runWalk && Controller.walk) {
				walking = Math.sin(game.time / 6.0) * 0.8;
			}

			if (ceiling < 0) { // split up and down
				z = (ceilingPosition - up) / -ceiling;
				if (Controller.walk) {
					z = (floorPosition - up - walking) / -ceiling;
				}
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine; // rotation
				double yy = z * cosine - depth * sine;
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;

				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 16]; // *8 ->*16 ,because canvas
																							// expand

				// pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8; // OR?,
				// shift? ,// bitwise
				// operator AND

				if (z > 500) { // remove the infinite margin to increase the FPS
					pixels[x + y * width] = 0;
				} // public void renderDistanceLimiter() may replace it?
					// System.out.println(xx);
			}
		}

		// generate random level
		Level level = game.level;
		int size = 20;
		for (int xBlock = -size; xBlock <= size; xBlock++) { // xBlock means xLeft and xRight in renderWall
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.create(xBlock, zBlock);
				Block east = level.create(xBlock + 1, zBlock);
				Block south = level.create(xBlock, zBlock + 1);

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0);
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
					}
				}
			}
		}
		for (int xBlock = -size; xBlock <= size; xBlock++) { // xBlock means xLeft and xRight in renderWall
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.create(xBlock, zBlock);
				Block east = level.create(xBlock + 1, zBlock);
				Block south = level.create(xBlock, zBlock + 1);

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0.5);
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0.5);
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0.5);
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0.5);
					}
				}
			}
		}

	}

	public void walls() { // ����A���|�b��ɮ����C
		// 18
//		Random random = new Random(100); // ��ΰϰ�~���|�@���{��
//
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 0; // �C+1���k���@��
//			double yy = random.nextDouble() - 0;
//			double zz = 1.5 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 1; // �C+1���k���@��
//			double yy = random.nextDouble() - 0;
//			double zz = 1.5 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 0; // �C+1���k���@��
//			double yy = random.nextDouble() - 1;
//			double zz = 1.5 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 1; // �C+1���k���@��
//			double yy = random.nextDouble() - 1;
//			double zz = 1.5 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 0; // �C+1���k���@��
//			double yy = random.nextDouble() - 0;
//			double zz = 2 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 1; // �C+1���k���@��
//			double yy = random.nextDouble() - 0;
//			double zz = 2 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 0; // �C+1���k���@��
//			double yy = random.nextDouble() - 1;
//			double zz = 2 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}
//		for (int i = 0; i < 10000; i++) {
//			double xx = random.nextDouble() - 1; // �C+1���k���@��
//			double yy = random.nextDouble() - 1;
//			double zz = 2 - forwardGlobal / 16; // �`�רC0.5�|���ʤ@��
//
//			int xPixel = (int) (xx / zz * height / 2 + width / 2);
//			int yPixel = (int) (yy / zz * height / 2 + height / 2);
//			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
//				pixels[xPixel + yPixel * width] = 0xfffff;
//			}
//		}

	}

	public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) { // 19
		double upCorrect = (1.0 / 16.0);
		// System.out.println(upCorrect);
		double rightCorrect = (1.0 / 16.0);
		double forwardCorrect = (1.0 / 16.0);
		double walkCorrect = -(1.0 / 16.0);

		double xcLeft = ((xLeft/2) - (right * rightCorrect)) * 2;
		double zcLeft = ((zDistanceLeft/2) - (forward * forwardCorrect)) * 2;

		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2; // *0.062? /16? *pi/50?
		double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight/2) - (right * rightCorrect)) * 2;
		double zcRight = ((zDistanceRight/2) - (forward * forwardCorrect)) * 2;

		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		//move below clipping
//		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
//		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

		double tex30 = 0;
		double tex40 = 8;
		double clip = 0.5;
		// prevent error
		if (rotLeftSideZ < clip && rotRightSideZ < clip) {
			return;
		}
		// for broken texture at boundary.
		if (rotLeftSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}
		if (rotRightSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
			tex40 = tex30 + (tex40 - tex30) * clip0;
		}
		//after moving
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

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

		double yPixelLeftTop = yCornerTL / rotLeftSideZ * height + height / 2.0;
		double yPixelLeftBottom = yCornerBL / rotLeftSideZ * height + height / 2.0;
		double yPixelRightTop = yCornerTR / rotRightSideZ * height + height / 2.0;
		double yPixelRightBottom = yCornerBR / rotRightSideZ * height + height / 2.0;
		// System.out.println(yPixelRightBottom); //�W���|�椣��[cast:(int)

		double tex1 = 1 / rotLeftSideZ; // darker by distance
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex30 / rotLeftSideZ;
		double tex4 = tex40 / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);//solve perspective problem
			
			if(zBufferWall[x]>zWall) {
				continue;
			}
			zBufferWall[x] = zWall;

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / (tex1 + (tex2 - tex1) * pixelRotation)); // darker by
																											// distance

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
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop); // darker by distance
				int yTexture = (int) (8 * pixelRotationY); // darker by distance
				// pixels[x + y * width] = xTexture * 100 + yTexture * 100 * 256; // 0xe7d1ff;
				// //darker by distance
				pixels[x + y * width] = Texture.floor.pixels[((xTexture & 7) + 8) + (yTexture & 7) * 16];
				zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;// 0; //darker by distance
			}
		}
	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i])); // enable to fade off

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
//ep11 last