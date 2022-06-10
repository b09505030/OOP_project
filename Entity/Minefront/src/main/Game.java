package main;

import input.InputHandler;
import input.Player;

import java.awt.event.KeyEvent;

import level.Level;

public class Game {

	public int time;
	public Player player;
	
	public Level level;
	
	public Game(InputHandler input) {
		player = new Player(input);
		level = new Level(20, 20);
		
		
		
		level.addEntity(player);
		
	}

	public void tick() {
		time++;
		level.tick();
	}
}
