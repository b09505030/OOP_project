package com.mime.minefront;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RunGame {
	
	public RunGame() {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // invisible mouse
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game); // adding frame into game
		frame.pack();
		// frame.getContentPane().setCursor(blank); //invisible mouse
		frame.setTitle(Display.TITLE); // title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // hit close button, want to terminate itself
		// frame.setSize(WIDTH,HEIGHT); //set size; can be remove because:Dimension size
		// = new Dimension(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); // this and frame.pack() =>in the center
		frame.setResizable(false); // don't want to be resize
		frame.setVisible(true);

		System.out.println("Running...");

		game.start();
		stopMenuThread();
	}
	private void stopMenuThread() {
		Display.getLauncherInstance().stopMenu();
	}
}