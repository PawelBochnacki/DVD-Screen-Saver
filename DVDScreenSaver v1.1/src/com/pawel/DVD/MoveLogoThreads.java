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
			//reading the selected speed value from the combo box
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
		if((x == 519 && y == 479) || (x == 519 && y == 1) ||
				(x == 1 && y == 479) || (x == 1 && y == 1)) {
			cornerHitLabel.setCornerHitCount(cornerHitLabel.getCornerHitCount()+1);
			cornerHitLabel.setLabelText(cornerHitLabel.getCornerHitCount());
			//plays a sound when logo hits the corner			
			clip.start();
		}
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
