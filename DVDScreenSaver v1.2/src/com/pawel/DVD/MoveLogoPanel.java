package com.pawel.DVD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MoveLogoPanel extends JPanel{ 
	private BufferedImage[] DVDimgFiles;
	private BufferedImage chosenImage;
	private int x = 10000;
	private int y = 10000;
	private ReadFiles readFiles;
	private ColorSelectionBox colorSelectionBox;
	private String selectedFromBox;
	
	public MoveLogoPanel(ReadFiles readFiles, ColorSelectionBox colorSelectionBox) throws IOException {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 5));
		setBounds(12, 0, 788, 600);
		
		this.readFiles = readFiles;
		this.colorSelectionBox = colorSelectionBox;
		DVDimgFiles = readFiles.getDVDimage();
			
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);		
		
		g.drawImage(checkForImage(), x, y, null);
	}	
	
	//chooses the proper image depending on user's color choice from the "set color" box
	BufferedImage checkForImage() {
		selectedFromBox = colorSelectionBox.getSelectedItem().toString();
		if(selectedFromBox.equals("BLACK")) {
			return DVDimgFiles[0];
		}else if(selectedFromBox.equals("BLUE")) {
			return DVDimgFiles[1];			
		}else if(selectedFromBox.equals("GREEN")) {
			return DVDimgFiles[2];			
		}else if(selectedFromBox.equals("RED")) {
			return DVDimgFiles[3];			
		}
		return DVDimgFiles[0];
	}
	
	void setX(int x){
		this.x = x;
	}
	
	void setY(int y) {
		this.y = y;
	}

}
