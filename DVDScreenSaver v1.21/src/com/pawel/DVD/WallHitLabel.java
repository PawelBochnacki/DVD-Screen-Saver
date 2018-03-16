package com.pawel.DVD;

import java.awt.Font;

import javax.swing.JLabel;

public class WallHitLabel extends JLabel {
	
	private int wallHitCount;
	
	public WallHitLabel(int x, int y, int width, int height) {
		setText("Wall hits: " + wallHitCount);
		setBounds(x, y, width, height);
		setFont(new Font("Arial", Font.PLAIN, 16));
		
	}
	
	void setWallHitCount(int wallHitCount) {
		this.wallHitCount = wallHitCount;
	}
	
	int getWallHitCount() {
		return wallHitCount;
	}
	
	void setLabelText(int wallHitCount) {
		setText("Wall hits: " + wallHitCount);
	}

}
