package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render;

	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);//
		}

	}

	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}

		// render ORDER
		render.floor(game);// first render
		render.renderWall(0, 0.5, 1.5, 1.5, 0);
		render.renderWall(0, 0, 1, 1.5, 0);
		render.renderWall(0, 0.5, 1, 1, 0);
		render.renderWall(0.5, 0.5, 1, 1.5, 0);
		render.renderWall(0.5, 0, 1.5, 1.5, 0);
		render.renderWall(0, 0, 1.5, 1.0, 0);
		render.renderWall(0.0, 0.5, 1.0, 1.0, 0);
		render.renderWall(.5, .5, 1.0, 1.5, 0);
		render.renderDistanceLimiter();// second render

		// render.walls();//third render
		draw(render, 0, 0);

//		for (int i = 0; i < 50; i++) { // 256*256*200 = 13107200
//			int anim0 = (int) (Math.sin(System.currentTimeMillis() % 1000.0 / 1000 * Math.PI * 2) * 100);
//			int anim = (int) (Math.sin((game.time + i * 2) % 1000.0 / 100) * 100); // Math.sin((System.currentTimeMillis()
//																					// + i*8) % 2000.0 / 2000 * Math.PI
//																					// * 2) * 200 //performance improved
//																					// : change i*k=1000
//			int anim2 = (int) (Math.cos((game.time + i * 2) % 1000.0 / 100) * 100);
//
//			// draw(test, (width - 256) / 2 + anim, (height - 256) / 2 + anim2); // name\xOffset\yOffset
//		}

	}

}
