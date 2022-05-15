package com.mime.minefront.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mime.minefront.Configuration;

public class Options{// extends Launcher {
	private static final long serialVersionUID = 1L;

	private Choice resolution = new Choice();

	private int width = 550;
	private int height = 450;
	private JButton OK;
	private JTextField twidth, theight;
	private JLabel lwidth, lheight;
	private Rectangle rOK, rresolution;
	int w = 0;
	int h = 0;
	private int button_width = 80;
	private int button_height = 40;
	
	Configuration config = new Configuration();
	JFrame frame = new JFrame();
	JPanel window = new JPanel();
	
	public Options() {
		//super(1);
		frame.setTitle("Options");
		frame.setSize(new Dimension(width, height));
		frame.add(window);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		window.setLayout(null);

		drawButtons();
		window.repaint();
	}

	private void drawButtons() {
		OK = new JButton("OK");
		rOK = new Rectangle((width - 100), (height - 70), button_width, button_height - 10);
		OK.setBounds(rOK);
		window.add(OK);

		rresolution = new Rectangle(50, 80, 80, 25);
		resolution.setBounds(rresolution);
		resolution.add("640, 480");
		resolution.add("800, 600");
		resolution.add("1024, 768");
		resolution.select(1);
		window.add(resolution);

		lwidth = new JLabel("Width:");
		lwidth.setBounds(30, 150, 120, 20);
		// lwidth.setFont(new Font("verdana", 2, 50));
		window.add(lwidth);

		lheight = new JLabel("Height:");
		lheight.setBounds(30, 180, 120, 20);
		window.add(lheight);

		twidth = new JTextField();
		twidth.setBounds(80, 150, 60, 20);
		window.add(twidth);

		theight = new JTextField();
		theight.setBounds(80, 180, 60, 20);
		window.add(theight);

		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Launcher(0);
				config.saveConfiguration("width", parseWidth());
				config.saveConfiguration("height", parseHeight());
				
			}
		});
	}

	public void drop() {
		int selection = resolution.getSelectedIndex();
		

		if (selection == 0) {
			w = 640;
			h = 480;
		}
		if (selection == 1 || selection == -1) {
			w = 800;
			h = 600;
		}
		if (selection == 2) {
			w = 1024;
			h = 768;
		}
		
	}

	private int parseWidth() {
		try {
			int w = Integer.parseInt(twidth.getText());
			return w;
		} catch (NumberFormatException e) {
			drop();
			return w;
		}
	}
	private int parseHeight() {
		try {
			int h = Integer.parseInt(theight.getText());
			return h;
		} catch (NumberFormatException e) {
			drop();
			return h;
		}
	}

}
