package com.pawel.DVD;

import java.awt.Font;

import javax.swing.JLabel;

public class CornerHitLabel extends JLabel{
	
	private int cornerHitCount;
	
	public CornerHitLabel(int x, int y, int width, int height) {
		setText("Corner hits: " + cornerHitCount);
		setBounds(x, y, width, height);
		setFont(new Font("Arial", Font.PLAIN, 16));
	}
	
	void setCornerHitCount(int cornerHitCount) {
		this.cornerHitCount = cornerHitCount;
	}
	
	int getCornerHitCount() {
		return cornerHitCount;
	}
	
	void setLabelText(int cornerHitCount) {
		setText("Corner hits: " + cornerHitCount);
	}

}
