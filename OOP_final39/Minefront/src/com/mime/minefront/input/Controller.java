package com.mime.minefront.input;

import com.mime.minefront.Display;

public class Controller {

	public double x,y, z, rotation, xa, za, rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runWalk = false;
	public static boolean jumpRun = false;
	
	
	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean run){//, boolean turnLeft, boolean turnRight) {
		double rotationSpeed = 0.002 * Display.MouseSpeed;
		double walkSpeed = 0.5;
		double jumpHeight = 0.5;
		double crouchHeight = 0.25;
		double xMove = 0;
		double zMove = 0;
		
		if (forward) {
			zMove++;
			walk = true;
		}
		
		if (back) {
			zMove--;
			walk = true;
		}
		
		if (left) {
			xMove--;
			walk = true;
		}
		
		if (right) {
			xMove++;
			walk = true;
		}
		
		if (turnLeft) {
			if(InputHandler.MouseButton == 3) {
				
			}else {
				rotationa -= rotationSpeed;
			//walk = true;
			}
			
		}
		
		if (turnRight) {
if(InputHandler.MouseButton == 3) {
				
			}else {
				rotationa += rotationSpeed;
			//walk = true;
			}
		}
		
		if (jump) {
			y+= jumpHeight;
			//jumpRun = true;
			//run = false;
						
		}
		
//		if (jumpRun) {
//			y+= jumpHeight;
//		}
		
		if (crouch) {
			y-= crouchHeight;
			run = false;
			crouchWalk = true;
			walkSpeed = 0.2;
			
		}
		
		if(run) {
			walkSpeed = 1;
			runWalk = true;
//			if(jump) {
//				jumpRun = true;
//			}
		}
		
		if(!forward && !back && !left && !right){// && !turnLeft && !turnRight){
			walk = false;
		}
		
		if(!crouch) {
			crouchWalk = false;
		}
		
		if(!run) {
			runWalk = false;
		}
		
//		if(!jump) {
//			jumpRun = false;
//		}
		
		
		
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa; //?
		y*= 0.9; //maximum height can reach
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.5;
	}

}
