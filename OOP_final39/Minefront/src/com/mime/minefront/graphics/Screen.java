package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;

public class Screen extends Render { //inherit Render

	private Render test;
	private Render3D render;
	
	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random(); //generate a stream of pseudo-random numbers
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4); // * (random.nextInt(5) / 4) (transparency?) need to use alpha support in Render.java
			
		}
		
	} 
	
	public void render(Game game) {
		for (int i = 0; i < width * height; i++) { //doesn't retain the trace
			pixels[i] = 0; //set pixel to 0 every frame
		}
		
		//for (int i = 0; i < 50; i++) {
//			int anim = (int) (Math.sin((System.currentTimeMillis()+i*8) % 2000.0 / 2000 * Math.PI * 2) * 200);
//			int anim2 = (int) (Math.cos((System.currentTimeMillis()+i*8) % 2000.0 / 2000 * Math.PI * 2) * 200);
//			draw(test, (width - 256) / 2 + anim, (height - 256) / 2 - anim2);
			
//			int anim = (int) (Math.sin((game.time + i * 2) % 1000.0 / 100) * 100);
//			int anim2 = (int) (Math.cos((game.time + i * 2) % 1000.0 / 100) * 100);
//			draw(test, (width - 256) / 2 + anim, (height - 256) / 2 - anim2);
		//}
		
		render.floor(game);
		render.renderWall(0, 0.5, 1.5, 1.5, 0);
		render.renderWall(0, 0, 1, 1.5, 0);
		render.renderWall(0, 0.5, 1, 1, 0);
		render.renderWall(0.5, 0.5, 1, 1.5, 0);



		render.renderDistanceLimiter();
		//render.walls();
		
		draw(render, 0, 0);
	}

}