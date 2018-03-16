package com.pawel.DVD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		AudioInputStream ais;
		MoveLogoThreads mlt;
		StopThread stopThread;
		ResetCounter resetCounter;
		JPanel panel;
		MoveLogoPanel mlp;
		SpeedSelectionBox speedSelectionBox;
		ColorSelectionBox colorSelectionBox;
		WallHitLabel wallHitLabel;
		CornerHitLabel cornerHitLabel;
		SetSpeedLabel setSpeedLabel;
		SetColorLabel setColorLabel;
		InfoLabel infoLabel;
		StartButton startButton;
		StopButton stopButton;
		ResetCounterButton resetCounterButton;
		ReadFiles readFiles;
		InfoText infoText;
	
	public static void main(String[] args) throws Exception  {
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
		   
		//reads all the files appended to the program in /data/ folder
		readFiles = new ReadFiles();				
		
		wallHitLabel = new WallHitLabel(30, 606, 125, 24);
		wallHitLabel.setLocation(30, 634);
		getContentPane().add(wallHitLabel);
		
		speedSelectionBox = new SpeedSelectionBox(196, 636, 97, 22);
		getContentPane().add(speedSelectionBox);
		
		colorSelectionBox = new ColorSelectionBox(324, 636, 97, 22);
		colorSelectionBox.setBounds(324, 636, 125, 22);
		getContentPane().add(colorSelectionBox);
					
		//activates the panel in which the logo is animated
		mlp = new MoveLogoPanel(readFiles, colorSelectionBox);
		getContentPane().add(mlp);
		
		setColorLabel = new SetColorLabel(324, 606, 125, 24);
		getContentPane().add(setColorLabel);
				
		cornerHitLabel = new CornerHitLabel(30, 636, 125, 24);
		cornerHitLabel.setLocation(30, 672);
		getContentPane().add(cornerHitLabel);
		
		//class responsible for the logo animation
		mlt = new MoveLogoThreads(speedSelectionBox, readFiles, wallHitLabel, cornerHitLabel, DVDimg, this, mlp);
			
		setSpeedLabel = new SetSpeedLabel(196, 606, 125, 24);
		getContentPane().add(setSpeedLabel);
	
		startButton = new StartButton(196, 673, 97, 25);
		getContentPane().add(startButton);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				mlt.run();				
			}
		});
				
		//class responsible for making the stop button actually stop the animation
		stopThread = new StopThread(mlt.getThreadX(), mlt.getThreadY(), mlp, mlt);	
		
		stopButton = new StopButton(488, 673, 97, 25);
		stopButton.setLocation(482, 673);
		getContentPane().add(stopButton);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				stopThread.stop();			
			}			
		});
		
		//button that resets the hit counter
		resetCounter = new ResetCounter(wallHitLabel, cornerHitLabel);		
		resetCounterButton = new ResetCounterButton(325, 673, 125, 25);
		getContentPane().add(resetCounterButton);
		
		infoLabel = new InfoLabel(605, 609, 195, 87);

		getContentPane().add(infoLabel);

		resetCounterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetCounter.reset();
			}			
		});		
		
		//sets the info tooltips
		infoText = new InfoText(this);
		infoText.setMouseListeners();

	}
	
}		
