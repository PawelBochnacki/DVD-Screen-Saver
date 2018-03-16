package com.pawel.DVD;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InfoLabel extends JLabel {

	public InfoLabel(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setVerticalAlignment(SwingConstants.CENTER);
		setFont(new Font("Arial", Font.PLAIN, 16));
		setText("");
	}

}
