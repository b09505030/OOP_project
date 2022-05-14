package com.mime.minefront.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.mime.minefront.Configuration;
import com.mime.minefront.RunGame;

public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private JTextField twidth, theight;
	private JLabel lwidth, lheight;
	private Rectangle rplay, roptions, rhelp, rquit;
	Configuration config = new Configuration();

	private int width = 240;
	private int height = 320;
	protected int button_width = 80;
	protected int button_height = 40;

	public Launcher(int id) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("c8763 Launcher");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		//drawButtons();
		
		
		if(id == 0) {
			drawButtons();
		}
		repaint(); //button won't disappear until mouse go through
	}

	private void drawButtons() {
		play = new JButton("Play!");
		rplay = new Rectangle((width / 2) - (button_width / 2), 90, button_width, button_height);
		play.setBounds(rplay);
		window.add(play);
		
		options = new JButton("Options!");
		roptions = new Rectangle((width / 2) - (button_width / 2), 140, button_width, button_height);
		options.setBounds(roptions);
		window.add(options);
		
		help = new JButton("Help!");
		rhelp = new Rectangle((width / 2) - (button_width / 2), 190, button_width, button_height);
		help.setBounds(rhelp);
		window.add(help);
		
		quit = new JButton("Quit!");
		rquit = new Rectangle((width / 2) - (button_width / 2), 240, button_width, button_height);
		quit.setBounds(rquit);
		window.add(quit);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.loadConfiguration("res/setting/config.xml");
				dispose();
				new RunGame();
				//System.out.println("Play!!");
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				new Options();
			}
		});
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play!!");
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

}
