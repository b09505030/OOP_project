package main;

import javax.swing.JFrame;

import gui.Gameover;
import gui.Launcher;

public class RunGame {
	static JFrame frame = new JFrame();

	public RunGame() {
		Display game = new Display();

		frame.add(game);
		// frame.getContentPane().setCursor(blank);
		frame.setTitle("Lamster");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		game.start();

		stopMenuThread();
	}

	private void stopMenuThread() {
		try {
			Display.getLanucher().stopMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void win() {
		System.out.println("you win");
		
		
		frame.dispose();
		new Gameover();
	}
}
