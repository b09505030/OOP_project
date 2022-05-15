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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.gui.Launcher;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L; // java thing?

//	public static final int WIDTH = 800; // constant cannot be changed
//	public static final int HEIGHT = 600;
	
	public static int width = 800;
	public static int height = 600;
	
	
	public static final String TITLE = "Minecraft �s��"; // title

	private Thread thread; // do multiple tasks simultaneously, render multiple things at once
	private Screen screen; // make an object
	private Game game;
	private BufferedImage img;
	private boolean running = false; // isn't running at the moment
	private Render render;
	private int[] pixels;
	private InputHandler input;

	private int newX = 0;
	private int oldX = 0;
	private int newY = 0;
	private int oldY = 0;
	private int fps; // print in frame
	public static int selection = 0;

	public static int MouseSpeed;
	
	static Launcher launcher;

	public Display() {
		Dimension size = new Dimension(getGameWidth(), getGameHeight()); // allow to put width and height into one object
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		screen = new Screen(getGameWidth(), getGameHeight());
		// render = new Render(WIDTH, HEIGHT); //because change the subclass to Screen
		game = new Game();
		img = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_RGB); // graphic things
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData(); // assign in Render

		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public static Launcher getLauncherInstance() {
		if(launcher == null) {
			launcher = new Launcher(0);
		}
		return launcher;
	}
	
	public int getGameWidth() {
//		if(selection == 0) {
//			width = 640;
//		}
//		if(selection == 1|| selection == -1) {
//			width = 800;
//		}
//		if(selection == 2) {
//			width = 1024;
//		}
		return width;
	}
	public int getGameHeight() {
//		if(selection == 0) {
//			height = 480;
//		}
//		if(selection == 1|| selection == -1) {
//			height = 600;
//		}
//		if(selection == 2) {
//			height = 768;
//		}
		return height;
	}

	public void start() { // initialize
		if (running) // if running is true, exit this method
			return;
		running = true; // initialize
		thread = new Thread(this, "game");
		thread.start();

		// System.out.println("Working!"); //test

	}

	public void stop() { // in order to use the applet
		if (!running)
			return;
		running = false;
		try { // ?
			thread.join(); // Java�������thread.join()�i�H���ثe���b���檺������Ȱ�
		} catch (Exception e) { // java syntax
			e.printStackTrace(); // ?
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

		// requestFocus(); //comments say!?

		while (running) {
//			tick(); //handle the time, frame per second
//			render(); //render thing to the screen
			// Episode 7
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();

			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					//System.out.println(frames + "fps");
					fps = frames; // print on frame
					previousTime += 1000;
					frames = 0;
				}
				
				
				
			}
			if (ticked) {
				render();
				//rendermenu();
				frames++;
			}
			//render();
			//frames++;
			
			
			// test
			// System.out.println("X: " + InputHandler.MouseX + "Y: " +
			// InputHandler.MouseY); //check x y position

			//newX = InputHandler.MouseX :move to tick()
//			
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
		game.tick(input.key); // ?
		// requestFocus(); //comments say!?
		newX = InputHandler.MouseX;

		if (newX > oldX) {
			// System.out.println("Right");
			Controller.turnRight = true;
		}

		if (newX < oldX) {
			// System.out.println("Left");
			Controller.turnLeft = true;
		}

		if (newX == oldX) {
			// System.out.println("Still");
			Controller.turnLeft = false;
			Controller.turnRight = false;
		}

		MouseSpeed = Math.abs(newX - oldX);

		oldX = newX;
	}

	
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); // graphic things, �u�Q����@��
		if (bs == null) {
			createBufferStrategy(3); // 3 dimensional
			return;
		}

		screen.render(game);

		for (int i = 0; i < getGameWidth() * getGameHeight(); i++) {
			pixels[i] = screen.pixels[i];

		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getGameWidth(), getGameHeight(), null); // frame.pack(); after frame.setResizable(false); ?
		g.setFont(new Font("Verdana", 2, 50)); // type,style(0:normal/1:bold/2:italics/3:bool and italics),size
		g.setColor(Color.YELLOW);
		g.drawString(fps + " FPS", 20, 50);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		//Display display = new Display();
		//new Launcher(0, display);
		getLauncherInstance();
	}

}