package com.pawel.DVD;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MoveLogoThreads {
	

	private int x;
	private int y;
	private int logoMoveSpeed = 50;
	private WallHitLabel wallHitLabel;
	private CornerHitLabel cornerHitLabel;
	//two threads used to calculate and run X and Y axis simultaneously 
	private Thread yMoveLogo;
	private Thread xMoveLogo;
	private SpeedSelectionBox speedSelectionBox;
	private ReadFiles readFiles;
	private Clip clip;
	private Main main;
	private MoveLogoPanel mlp;
	private int randomStartingPositionX;
	private int randomStartingPositionY;
	private boolean shouldIsubstract = false;
	
	
	public MoveLogoThreads(SpeedSelectionBox speedSelectionBox, ReadFiles readFiles, WallHitLabel wallHitLabel, CornerHitLabel cornerHitLabel, BufferedImage dVDimg, Main main, MoveLogoPanel mlp) throws LineUnavailableException, IOException {
		this.speedSelectionBox = speedSelectionBox;
		this.readFiles = readFiles;
		this.wallHitLabel = wallHitLabel;
		this.cornerHitLabel = cornerHitLabel;
		this.main = main;
		this.mlp = mlp;	
		clip = readFiles.getAudio();
		
	}
	
	
	void run() {
		//runs the threads only if the thread objects are null to avoid unintended behavior
		if(xMoveLogo == null || yMoveLogo == null) {
			//read the selected speed value from the combo box
			String readSpeedString = speedSelectionBox.getSelectedItem().toString();
			int readSpeed = Integer.parseInt(readSpeedString);
			logoMoveSpeed = logoMoveSpeed / readSpeed;
			randomStartingPositionX = generateRandomPositionX();
			randomStartingPositionY = generateRandomPositionY();
								
			//move the logo left and right
			xMoveLogo = new Thread(new Runnable() {
				@Override
				public void run() {
					for (;;) {
						for (; randomStartingPositionX < 520; randomStartingPositionX++) {
							x = randomStartingPositionX;
							mlp.setX(x);
							mlp.repaint();
							try {
								Thread.sleep(logoMoveSpeed);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						//checks if the logo hits one of the corners and counts the wall hits

						checkIfCornerHit(clip);
						wallHitLabel.setWallHitCount(wallHitLabel.getWallHitCount()+1);					
						wallHitLabel.setLabelText(wallHitLabel.getWallHitCount());
																									
						for (int i = 520; i > 0; i--) {
							x = i;
							mlp.setX(x);
							mlp.repaint();
							try {
								Thread.sleep(logoMoveSpeed);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}	
						checkIfCornerHit(clip);
						wallHitLabel.setWallHitCount(wallHitLabel.getWallHitCount()+1);					
						wallHitLabel.setLabelText(wallHitLabel.getWallHitCount());
						randomStartingPositionX = 0;
					}							
				}		
			});
			
			//move the logo up and down
			yMoveLogo = new Thread(new Runnable() {
				@Override
				public void run() {
					for (;;) {
						for (; randomStartingPositionY < 480; randomStartingPositionY++) {
							y = randomStartingPositionY;
							mlp.setY(y);
							mlp.repaint();
							try {
								Thread.sleep(logoMoveSpeed);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}									
						}
						
						checkIfCornerHit(clip);
						wallHitLabel.setWallHitCount(wallHitLabel.getWallHitCount()+1);					
						wallHitLabel.setLabelText(wallHitLabel.getWallHitCount());
						
						for (int i = 480; i > 0; i--) {
							y = i;
							mlp.setY(y);
							mlp.repaint();
							try {
								Thread.sleep(logoMoveSpeed);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}								
						}
						checkIfCornerHit(clip);
						wallHitLabel.setWallHitCount(wallHitLabel.getWallHitCount()+1);					
						wallHitLabel.setLabelText(wallHitLabel.getWallHitCount());
						randomStartingPositionY = 0;
					}		
				}		
			});		
			xMoveLogo.start();
			yMoveLogo.start();
		}
	}
	
	void checkIfCornerHit(Clip clip) {
		//decided that those values are the best (the logo must be at least 5 pixels from the actual corner 
		//to score a corner hit)
		if((x > 515 && y > 475) || (x > 515 && y < 5) ||
				(x < 5 && y > 475) || (x < 5 && y < 5)) {

			//plays a sound when logo hits the corner			
			clip.start();
			
			//see below
			badCounterBugFix();
			cornerHitLabel.setLabelText(cornerHitLabel.getCornerHitCount());			
		}
	}
	
	void badCounterBugFix() {
		//the initial problem with the method counting the corner hits was that despite 
		//using a thread forcing it to sleep for one second, it counted one corner hit
		//two times instead of one ONLY after scoring the first hit (so it was going like 1>3>5>7 etc)
		//honestly I do not understand why is that so I've made a rough "fix" based on substracting
		//the value by 1 after each bad count to make it appear to the user that it goes like 1>2>3>4 etc
		
		new Thread(new Runnable() {				
			@Override
			public void run() {
				cornerHitLabel.setCornerHitCount(cornerHitLabel.getCornerHitCount()+1);	
				if(shouldIsubstract) {
					cornerHitLabel.setCornerHitCount(cornerHitLabel.getCornerHitCount()-1);						
				}
				try {
					Thread.sleep(1000);
					} catch (InterruptedException e) {
				}
				shouldIsubstract = true;
			}				
		}).start();		
	}
	
	Thread getThreadX() {
		return xMoveLogo;
	}
	Thread getThreadY() {
		return yMoveLogo;
	}
	
	void setThreadX(Thread xMoveLogo) {
		this.xMoveLogo = xMoveLogo;
	}
	
	void setThreadY(Thread yMoveLogo) {
		this.yMoveLogo = yMoveLogo;
	}
	
	int generateRandomPositionX() {
		return(int)(Math.random()*520);
	}
	
	int generateRandomPositionY() {
		return (int)(Math.random()*480);	
	}
	
	void setLogoMoveSpeed(int logoMoveSpeed) {
		this.logoMoveSpeed = logoMoveSpeed;
	}
	
	int getLogoMoveSpeed() {
		return logoMoveSpeed;
	}

}
