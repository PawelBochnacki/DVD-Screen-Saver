package com.pawel.DVD;

import java.awt.Font;

import javax.swing.JLabel;

public class SetSpeedLabel extends JLabel {

	public SetSpeedLabel(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setText("Set speed:");
		setFont(new Font("Arial", Font.PLAIN, 16));
	}

}
