package com.mime.minefront.input;

public class Controller {

	public double x,y, z, rotation, xa, za, rotationa;
	public static boolean MturnLeft = false;
	public static boolean MturnRight = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runWalk = false;

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean turnLeft, boolean turnRight,boolean jump,boolean crouch,boolean run) {
		double rotationSpeed = 0.025;
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
			rotationa -= rotationSpeed;
			walk = true;
		}
		if (turnRight) {
			rotationa += rotationSpeed;
			walk = true;
		}
		
		if (MturnLeft) {
			rotationa -= rotationSpeed;
			walk = true;
		}
		if (MturnRight) {
			rotationa += rotationSpeed;
			walk = true;
		}
		
		if (jump) {
			y+= jumpHeight;
			//run = false;
						
		}
		
		if (crouch) {
			y-= crouchHeight;
			run = false;
			crouchWalk = true;
			walkSpeed = 0.2;
			
		}
		
		if(run) {
			walkSpeed = 1;
			walk = true;
		}
		
		if(!forward && !back && !left && !right && !turnLeft && !turnRight){
			walk = false;
		}
		
		if(!crouch) {
			crouchWalk = false;
		}
		
		if(!run) {
			runWalk = false;
		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa;
		y*= 0.9;
		z += za;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.5;

	}

}
