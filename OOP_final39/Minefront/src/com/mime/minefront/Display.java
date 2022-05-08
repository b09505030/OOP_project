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

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L; //java thing?
	
	public static final int WIDTH = 800; //constant cannot be changed
	public static final int HEIGHT = 600;
	public static final String TITLE = "Minecraft �s��"; //title
	
	private Thread thread; //do multiple tasks simultaneously, render multiple things at once
	private Screen screen; //make an object
	private Game game;
	private BufferedImage img;
	private boolean running = false; //isn't running at the moment
	private Render render;
	private int[] pixels;
	private InputHandler input;
	
	private int newX = 0;
	private int oldX = 0;
	private int newY = 0;
	private int oldY = 0;
	private int fps; //print in frame
	
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT); //allow to put width and height into one object
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		screen = new Screen(WIDTH, HEIGHT);
		//render = new Render(WIDTH, HEIGHT); //because change the subclass to Screen
		game = new Game();
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //graphic things
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData(); //assign in Render
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	private void start() { //initialize
		if (running) //if running is true, exit this method
			return;
		running = true; //initialize
		thread = new Thread(this);
		thread.start();

		//System.out.println("Working!"); //test

	}
	public void stop() { //in order to use the applet
		if (!running)
			return;
		running = false;
		try { //?
			thread.join(); //Java�������thread.join()�i�H���ثe���b���檺������Ȱ�
		} catch (Exception e) { //java syntax
			e.printStackTrace(); //?
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
		
		//requestFocus(); //comments say!?

		while (running) {
//			tick(); //handle the time, frame per second
//			render(); //render thing to the screen
			//Episode 7
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
					fps = frames; //print on frame
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
			//test
			//System.out.println("X: " + InputHandler.MouseX + "Y: " + InputHandler.MouseY); //check x y position
			
			newX = InputHandler.MouseX;
			
			if (newX > oldX) {
				//System.out.println("Right");
				Controller.turnRight = true;
			}
			
			if (newX < oldX) {
				//System.out.println("Left");
				Controller.turnLeft = true;
			}
			
			if (newX == oldX) {
				//System.out.println("Still");
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}
			
			oldX = newX;			
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
		game.tick(input.key); //?
		//requestFocus(); //comments say!?
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); //graphic things, �u�Q����@��
		if (bs == null) {
			createBufferStrategy(3); //3 dimensional
			return;
		}
		
		screen.render(game);
		
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];

		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null); //frame.pack(); after frame.setResizable(false); ?
		g.setFont(new Font("Verdana",2,50)); //type,style(0:normal/1:bold/2:italics/3:bool and italics),size
		g.setColor(Color.YELLOW);
		g.drawString(fps + " FPS", 20, 50);
		g.dispose();
		bs.show();
	}
	
	
	public static void main(String[] args) {
		BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB); //invisible mouse
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0 , 0), "blank");
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game); //adding frame into game
		frame.pack();
		frame.getContentPane().setCursor(blank); //invisible mouse
		frame.setTitle(TITLE); //title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //hit close button, want to terminate itself
		//frame.setSize(WIDTH,HEIGHT); //set size; can be remove because:Dimension size = new Dimension(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); //this and frame.pack() =>in the center
		frame.setResizable(false); //don't want to be resize
		frame.setVisible(true);
		
		System.out.println("Running...");
		
		game.start();
	}

}