package main;





import javax.swing.JFrame;

public class RunGame {

	public RunGame() {
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		//frame.getContentPane().setCursor(blank);
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
 }
