package com.mime.minefront.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener,FocusListener, MouseMotionListener, MouseListener {

	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX;
	public static int MouseDY;
	public static int MousePX;
	public static int MousePY;
	public static int MouseButton;
	public static boolean dragged = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MouseButton = e.getButton();
		long previousTime = System.nanoTime();
		while (MouseButton==1) {		
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			if(passedTime > 100000000) {
				MouseButton = 0;
			}
			//previousTime = currentTime;
		}
		//System.out.println(MouseButton);
		//MouseButton = 0;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		MousePX = e.getX();
		MousePY = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		dragged = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		dragged = true;
		MouseDX = e.getX();
		MouseDY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i=0;i < key.length;i++) {
			key[i]=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length)
			key[keyCode] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		for (int i =0; i < key.length; i++) { //weird
//			key[i] = false;
//		}
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

}