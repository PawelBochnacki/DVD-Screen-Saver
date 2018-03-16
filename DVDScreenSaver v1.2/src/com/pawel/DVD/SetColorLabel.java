package com.pawel.DVD;

import java.awt.Font;

import javax.swing.JLabel;

public class SetColorLabel extends JLabel {

	public SetColorLabel(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setText("Set color:");
		setFont(new Font("Arial", Font.PLAIN, 16));
	}

}
