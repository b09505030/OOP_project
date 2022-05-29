package main;

import graphics.Screen;
import gui.Launcher;

import input.Player;
import input.InputHandler;

import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable {
	public static int width = 800;
	public static int height = 600;
	private static final long serialVersionUID = 1L;

	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Game game;
	private boolean running = false;
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;

	public static int selection = 0;

	long lastFPS;
	long fps;

	public static int mouseSpeed;

	static Launcher lanucher;

	public Display() {
		Dimension size = new Dimension(getGameWidth(), getGameHeight());
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(getGameWidth(), getGameHeight());
		input = new InputHandler();
		game = new Game(input);
		img = new BufferedImage(getGameWidth(), getGameHeight(),
				BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

	}

	public static Launcher getLanucher() {
		if (lanucher == null) {
			lanucher = new Launcher(0);
		}
		return lanucher;
	}

	public int getGameWidth() {
		return width;
	}

	public int getGameHeight() {
		return height;
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "game");
		thread.start();
		
	}

	public synchronized void stop() {
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

	long actualfps;

	public void run() {

		long previousTime = System.nanoTime();
		double na = 1000000000.0 / 60.0;
		double delta = 0;
		// int tickCount = 0;
		// boolean ticked = false;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		requestFocus();
		while (running) {

			long currentTime = System.nanoTime();
			delta += (currentTime - previousTime) / na;
			previousTime = currentTime;

			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}

			render();
			frames++;

			while (System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			actualfps = frames;
			frames = 0;
			updates = 0;
			}

		}
	}

	private void tick() {
		input.tick();
		game.tick();

		newX = InputHandler.MouseX;
		if (newX > oldX) {
			Player.turnRight = true;
		}
		if (newX < oldX) {
			Player.turnLeft = true;
		}
		if (newX == oldX) {
			Player.turnLeft = false;
			Player.turnRight = false;
		}
		mouseSpeed = Math.abs(newX - oldX);
		oldX = newX;
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(game);

		for (int i = 0; i < getGameWidth() * getGameHeight(); i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getGameWidth(), getGameHeight(), null);
		g.setFont(new Font("Verdana", 2, 50));
		g.setColor(Color.yellow);
		g.drawString(actualfps + " FPS", 20, 50);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {

		getLanucher();

	}

}
