package com.pawel.DVD;

import javax.swing.JComboBox;

public class SpeedSelectionBox extends JComboBox {

	public SpeedSelectionBox(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		for (int i = 1; i < 11; i++) {
			addItem(i);
		}
		setSelectedIndex(4);
	}

}
