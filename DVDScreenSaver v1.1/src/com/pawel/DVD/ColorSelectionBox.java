package com.pawel.DVD;

import javax.swing.JComboBox;

public class ColorSelectionBox extends JComboBox {

	public ColorSelectionBox(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		addItem("BLACK");
		addItem("BLUE");
		addItem("GREEN");
		addItem("RED");
		
	}

}
