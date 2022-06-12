package gui;


import java.awt.Canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.UIManager;

import graphics.Sound;
import input.InputHandler;

import main.Display;



public class Gameover extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private int width = 800;
	private int height = 400;

	int w = 0;
	int h = 0;
	
	
	boolean running = false;
	Thread thread;
	JFrame frame = new JFrame();
	
	JPanel window = new JPanel();
	
	public Gameover() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputHandler.MouseButton=0;
		frame.setUndecorated(true);
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		window.setLayout(null);
		
		frame.repaint();
		startMenu();
		
		
		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public void updateFrame() {
		if(InputHandler.dragged) {
			Point p = frame.getLocation(); //Canvas and JFrame both have a method called "gerLocation"
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}
	
	public void startMenu() {
		running = true;
		thread = new Thread(this, "logo");
		thread.start();
	}
	
	public void stopMenu() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			try {
			rendermenu();
			} catch (IllegalStateException e) {
				System.out.println("error");
			}
			updateFrame();
		}
		
	}
	
	private void rendermenu() throws IllegalStateException {
		
		BufferStrategy bs = this.getBufferStrategy(); // graphic things, �u�Q����@��
		
		if (bs == null) {
			createBufferStrategy(3); // 3 dimensional
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		
		String FileName = "/logo.png";
		InputStream iss = Display.class.getResourceAsStream(FileName);
		try {
			g.drawImage(ImageIO.read(iss), 0, 0, 800, 400, null);
			if(InputHandler.MouseX > 720 && InputHandler.MouseX < 720+80 && InputHandler.MouseY > 360&& InputHandler.MouseY < 360+30) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("EXIT", 720, 390);
				
				if(InputHandler.MouseButton == 1) {
					InputHandler.MouseButton = 0;
					System.out.println(Display.lanucher);
					//Display.getLanucher();
					//frame.setVisible(false);
					Sound.altar.play();
					frame.dispose();
					
					//Launcher.rest();
					System.exit(0);
				}
			} else {
				g.setColor(Color.GRAY);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("EXIT", 720, 390);
			}
			//System.out.println(InputHandler.MouseButton);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error2");
		}
		
		g.dispose();
		bs.show();
	}
	
	



}