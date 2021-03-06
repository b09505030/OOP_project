package com.mime.minefront;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

//import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String TITLE = "Minecraft hehehe";

	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	
	private boolean running = false;
	// private Render render;
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;
	private int newY = 0;
	private int oldY = 0;
	private int fps;//String
	
	public static int MouseSpeed;

	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		screen = new Screen(WIDTH, HEIGHT);
		game = new Game(); ///// ???????????sometime bugged
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
		
	}

	public synchronized void start() { //synchronized helps us with threading
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();

		// System.out.println("Working!");

	}

	public synchronized void stop() { //synchronized helps us with threading
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();//////???????J?C???]?????I???e???~?i?????^
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + "fps");
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
			//System.out.println("X: " + InputHandler.MouseX + "Y: " + InputHandler.MouseY); //check x y position
			
			newX = InputHandler.MouseX;//mouse controller combine with input_handler
			
			if (newX > oldX) {
				//System.out.println("Right");
				Controller.MturnRight = true;
			}
			
			if (newX < oldX) {
				//System.out.println("Left");
				Controller.MturnLeft = true;
			}
			
			if (newX == oldX) {
				//System.out.println("Still");
				Controller.MturnLeft = false;
				Controller.MturnRight = false;
			}
			MouseSpeed = Math.abs(newX - oldX);
			oldX = newX;			
			
//			newY = InputHandler.MouseY;
//			
//			if (newY > oldY) {
//				System.out.println("Down");
//			}
//			
//			if (newY < oldY) { 
//				System.out.println("Up");
//			}
//			
//			if (newY == oldY) {
//				System.out.println("Still");
//			}
//			
//			oldY = newY;
			
		}
	}

	private void tick() {
		game.tick(input.key);

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // 3D game
			return;
		}

		screen.render(game);

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];

		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null); /// ?p?G?e???????{?{????width ,height+?`??
		g.setFont(new Font("Verdana",0,50)); //type,style(0:normal/1:bold/2:???u/3:both),size
		g.setColor(Color.YELLOW);
		g.drawString("fps"+fps, 20, 50);
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0 , 0), "blank");
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.getContentPane().setCursor(blank);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true); /////// ?O?_?i?H?????????j?p
		frame.setVisible(true);

		System.out.println("Running...");
		game.start();

	}

}
