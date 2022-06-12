package main;

import graphics.Sound;
import input.InputHandler;
import input.Player;


import level.Level;

public class Game {

	public int time;
	public Player player;
	public Menu menu;
	
	public Level level;
	
	public Game(InputHandler input) {
		player = new Player(input);
		//level = new Level(20, 20);
		level = Level.loadLevel(this, "start");
		player.level = level;
		level.player = player;
//		System.out.println(level.xSpawn);
//		System.out.println(level.ySpawn);
		player.x = level.xSpawn * 8;
		player.z = level.ySpawn * 8;
		player.ff=1;
		Sound.cave5.play();
		
		level.addEntity(player);
		
	}

	public void tick() {
		time++;
		level.tick();
	}
	public static int w = 640;
	public static int h = 480;
	public void win() {
		System.out.println("3");
		RunGame.win();
		
		//Render render = new Render(w, h);
		//render.draw(Texture.logo, 0, 10, 0, 65, 160, 23, Texture.getCol(0xffffff));

		//setMenu(new WinMenu(player));
		// TODO Auto-generated method stub
		
		
		
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
