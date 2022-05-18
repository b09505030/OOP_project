package input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseMotionListener,
		MouseListener, FocusListener {

	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX; // d = drag
	public static int MouseDY;
	public static int MousePX;
	public static int MousePY; // pressed
	public static int MouseButton;
	public static boolean dragged = false;

	boolean forward, back, left, right, rleft, rright, jump, crouch, run = false;
	
	public void tick() {
		forward = key[KeyEvent.VK_W];
		back = key[KeyEvent.VK_S];
		left = key[KeyEvent.VK_A];
		right = key[KeyEvent.VK_D];
		rleft = key[KeyEvent.VK_LEFT];
		rright = key[KeyEvent.VK_RIGHT];
		jump = key[KeyEvent.VK_SPACE];
		crouch = key[KeyEvent.VK_CONTROL];
		run = key[KeyEvent.VK_SHIFT];
	}
	
	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		MouseButton = e.getButton();
		MousePX = e.getX();
		MousePY = e.getY();
		
	}

	public void mouseReleased(MouseEvent e) {
		
		
		
		dragged = false;
		MouseButton = 0;
		
	}

	public void mouseDragged(MouseEvent e) {
		
		MouseDX = e.getX();
		MouseDY = e.getY();
		dragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		MouseX = e.getX();
		MouseY = e.getY();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}