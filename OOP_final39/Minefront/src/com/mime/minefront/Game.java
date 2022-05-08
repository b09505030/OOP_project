package com.mime.minefront;

import java.awt.event.KeyEvent;

import com.mime.minefront.input.Controller;

public class Game {
	public int time;
	public Controller controls;
	
	public Game() {
		controls = new Controller();

	}
	
	public void tick(boolean[] key) { //one unit at a time every time it gets called
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		//boolean turnLeft = key[KeyEvent.VK_LEFT];
		//boolean turnRight = key[KeyEvent.VK_RIGHT];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_SHIFT];
		boolean run = key[KeyEvent.VK_CONTROL];
		
		controls.tick(forward, back, left, right, jump, crouch, run);//, turnLeft, turnRight);
	}
}
