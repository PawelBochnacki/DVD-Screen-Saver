package com.pawel.DVD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;


import java.io.File;

public class Main extends JFrame {

		BufferedImage DVDimg;
		//initial values of x and y are set to such absurd numbers to "force" the first draw of the logo 
		//(before clicking the start button) out of the screen
		int x = 10000;
		int y = 10000;
		int wallHitCount;
		int cornerHitCount;
		//value used to calculate the animation speed of the logo
		int logoMoveSpeed = 50;
		Label wallHitLabel;
		Label cornerHitLabel;
		//two threads used to calculate and run X and Y axis simultaneously 
		Thread yMoveLogo;
		Thread xMoveLogo;
	
	public static void main(String[] args) throws Exception {
		Main frame = new Main();		
		frame.setVisible(true);
	}

	public Main() throws Exception{
		super("DVD Screen Saver");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 750);
		setResizable(false);
		getContentPane().setLayout(null);
		   
		//import the sound file to play a sound when logo hits the corner (checkIfCornerHit method below)
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File("ding.wav").getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(ais);
				
		wallHitLabel = new Label("Wall hits: " + wallHitCount);
		wallHitLabel.setBounds(30, 606, 125, 24);
		getContentPane().add(wallHitLabel);

		//read the image of DVD logo
		DVDimg = ImageIO.read(new File("DVD_Logo.jpg"));
				
		JPanel panel = new JPanel( ) {			
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				super.paintComponent(g2d);		
				
				g.drawImage(DVDimg, x, y, null);
			}
		};
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panel.setBounds(12, 0, 788, 600);
		getContentPane().add(panel);
				
		cornerHitLabel = new Label("Corner hits: " + cornerHitCount);
		cornerHitLabel.setBounds(30, 636, 125, 24);
		getContentPane().add(cornerHitLabel);
		
		Label setSpeedLabel = new Label("Set speed:");
		setSpeedLabel.setBounds(196, 606, 125, 24);
		getContentPane().add(setSpeedLabel);
		
		//combo box that contains all the speed values
		JComboBox speedSelectionBox = new JComboBox();
		speedSelectionBox.setBounds(196, 636, 97, 22);
		getContentPane().add(speedSelectionBox);
		for (int i = 1; i < 11; i++) {
			speedSelectionBox.addItem(i);
		}
		speedSelectionBox.setSelectedIndex(4);
		
		//button that resets all the hit counters
		JButton resetCountersButton = new JButton("Reset counters");
		resetCountersButton.setBounds(325, 673, 125, 25);
		getContentPane().add(resetCountersButton);
		resetCountersButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wallHitCount = 0;
				cornerHitCount = 0;
				wallHitLabel.setText("Wall hits: " + wallHitCount);
				cornerHitLabel.setText("Corner hits: " + cornerHitCount);				
			}			
		});
		
		//start button responsible for starting the two threads that allow the logo to move
		JButton startButton = new JButton("Start");
		startButton.setBounds(196, 673, 97, 25);
		getContentPane().add(startButton);	
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//runs the threads only if the thread objects are null to avoid unintended behavior
				if(xMoveLogo == null || yMoveLogo == null) {
					//reading the selected speed value from the combo box
					String readSpeedString = speedSelectionBox.getSelectedItem().toString();
					int readSpeed = Integer.parseInt(readSpeedString);
					logoMoveSpeed = logoMoveSpeed / readSpeed;
										
					//move the logo left and right
					xMoveLogo = new Thread(new Runnable() {
						@Override
						public void run() {
							//starting X and Y values are random
							int randomStartingPositionX = (int)(Math.random()*520);
							for (;;) {
								for (int i = 0; randomStartingPositionX < 520; randomStartingPositionX++) {
									x = randomStartingPositionX;
									panel.repaint();
									try {
										Thread.sleep(logoMoveSpeed);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								
								//checks if the logo hits one of the corners and counts the wall hits
								checkIfCornerHit(clip);
								wallHitCount++;					
								wallHitLabel.setText("Wall hits: " + wallHitCount);
																											
								for (int i = 520; i > 0; i--) {
									x = i;
									panel.repaint();
									try {
										Thread.sleep(logoMoveSpeed);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}	
								checkIfCornerHit(clip);
								wallHitCount++;
								wallHitLabel.setText("Wall hits: " + wallHitCount);
								randomStartingPositionX = 0;
							}							
						}		
					});
					
					//move the logo up and down
					yMoveLogo = new Thread(new Runnable() {
						@Override
						public void run() {
							int randomStartingPositionY = (int)(Math.random()*480);
							for (;;) {
								for (int i = 0; randomStartingPositionY < 480; randomStartingPositionY++) {
									y = randomStartingPositionY;
									panel.repaint();
									try {
										Thread.sleep(logoMoveSpeed);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}									
								}
								
								checkIfCornerHit(clip);
								wallHitCount++;
								wallHitLabel.setText("Wall hits: " + wallHitCount);
								
								for (int i = 480; i > 0; i--) {
									y = i;
									panel.repaint();
									try {
										Thread.sleep(logoMoveSpeed);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}								
								}
								checkIfCornerHit(clip);
								wallHitCount++;
								wallHitLabel.setText("Wall hits: " + wallHitCount);
								randomStartingPositionY = 0;
							}		
						}		
					});		
					xMoveLogo.start();
					yMoveLogo.start();
				}				
			}		
		});
		
		//button responsible for stopping the threads
		JButton stopButton = new JButton("Stop");
		stopButton.setBounds(488, 673, 97, 25);
		getContentPane().add(stopButton);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				//stop() method used instead of interrupt() to stop the threads as the latter causes errors, 
				//so the code below is kind of a workaround
				xMoveLogo.stop();
				yMoveLogo.stop();				
				//sets the two threads to null so they can be ran again after stopping
				xMoveLogo = null;
				yMoveLogo = null;
				logoMoveSpeed = 50;
				//sets the coords to values way beyond the window to make it "disappear" again
				x = 10000;
				y = 10000;
				//sets the coords to random values again
				int randomStartingPositionX = (int)(Math.random()*520);
				int randomStartingPositionY = (int)(Math.random()*480);	
				panel.repaint();
			
			}			
		});
	}
		
	void checkIfCornerHit(Clip clip) {
		if((x == 519 && y == 479) || (x == 519 && y == 1) ||
				(x == 1 && y == 479) || (x == 1 && y == 1)) {
			cornerHitCount++;
			cornerHitLabel.setText("Corner hits: " + cornerHitCount);
			//plays a sound when logo hits the corner
			clip.start();
		}
	}
}
