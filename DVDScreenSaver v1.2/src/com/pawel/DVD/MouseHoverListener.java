package com.pawel.DVD;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHoverListener implements MouseListener {
	//class implementing MouseListener interface to help shorten the code (as there is no need to redefine the methods)
	
	private InfoLabel infoLabel;

	public MouseHoverListener(InfoLabel infoLabel) {
		this.infoLabel = infoLabel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		infoLabel.setText("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	


}
