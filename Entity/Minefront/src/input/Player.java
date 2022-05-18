package input;

import entity.Mob;

public class Player extends Mob {

	public double y;
	// public double z;
	public double rotation;
	public double xa;
	public double za;
	public double rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runWalk = false;

	private InputHandler input;
	
	public Player(InputHandler input) {
		this.input = input;
	}
	
	public void tick() {
		double rotationSpeed = 0.15;

		double xMove = 0;
		double zMove = 0;
		double walkSpeed = 1.8;
		double jumpHeight = 0.9;
		double crouchHeight = 0.3;
		int xa = 0;
		int za = 0;
		if (input.forward) {
			za++;
			// zMove++;
			walk = true;
		}
		if (input.back) {
			za--;
			// zMove--;
			walk = true;
		}
		if (input.left) {
			xa--;
			// xMove--;
			walk = true;
		}
		if (input.right) {
			xa++;
			// xMove++;
			walk = true;
		}

		if (input.rleft) {
			rotationa -= rotationSpeed;
		}

		if (input.rright) {
			rotationa += rotationSpeed;
		}
		rotation += rotationa;
		rotationa *= 0.5;
		
		if (xa != 0 || za != 0) {
			move(xa, za, rotation);
		}
		if (input.jump) {
			y += jumpHeight;
			input.run = false;
		}

		if (input.crouch) {
			y -= crouchHeight;
			input.run = false;
			crouchWalk = true;
			walkSpeed = 1.0;
		}

		if (input.run) {
			walkSpeed = 2.0;
			walk = true;
			runWalk = true;
		}

		if (!input.forward && !input.back && !input.left && !input.right) {
			walk = false;
		}

		if (!input.crouch) {
			crouchWalk = false;
		}

		if (!input.run) {
			runWalk = false;
		}

		// xa += ((xMove * Math.cos(rotation)) + (zMove * Math.sin(rotation)))
		// * walkSpeed;
		// za += ((zMove * Math.cos(rotation)) - (xMove * Math.sin(rotation)))
		// * walkSpeed;

		//x += xa;
		// System.out.println(x);
		y *= 0.9;
		//z += za;

		// xa *= 0.1;
		// za *= 0.1;
	}
	
	
	public void tick(boolean forward, boolean back, boolean left,
			boolean right, boolean rleft, boolean rright, boolean jump,
			boolean crouch, boolean run) {
		

	}
}