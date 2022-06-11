package main;

import input.InputHandler;
import input.Player;

import java.awt.event.KeyEvent;

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
		player.x = level.xSpawn * 8.1;
		player.z = level.ySpawn * 8.1;
		
		level.addEntity(player);
		
	}

	public void tick() {
		time++;
		level.tick();
	}

	public void win(Player player) {
		System.out.println("you win!");
		setMenu(new WinMenu(player));
		// TODO Auto-generated method stub
		
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
