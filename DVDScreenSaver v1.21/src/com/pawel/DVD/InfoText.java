package com.pawel.DVD;

import java.awt.event.MouseEvent;

public class InfoText {
	//contains the text data for tooltips

	private Main main;
	public InfoText(Main main) {
		this.main = main;
	}

	public void setMouseListeners() {
		main.startButton.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(startButtonText());
			}
		});
		
		main.stopButton.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(stopButtonText());
			}
		});
		
		main.resetCounterButton.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(resetCounterButtonText());
			}
		});
		
		main.speedSelectionBox.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(speedSelectionBoxText());
			}
		});
		
		main.colorSelectionBox.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(colorSelectionBoxText());
			}
		});
		
		main.mlp.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(mlpText());
			}
		});
		
		main.wallHitLabel.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(wallHitLabelText());
			}
		});
		
		main.cornerHitLabel.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(cornerHitLabelText());
			}
		});
		
		main.infoLabel.addMouseListener(new MouseHoverListener(main.infoLabel) {
			@Override
			public void mouseEntered(MouseEvent e) {
				main.infoLabel.setText(infoLabelText());
			}
		});
		
	}
	
	String startButtonText() {
		return "<html><p><center>Starts the program</center></p></html>";
	}
	
	String stopButtonText() {
		return "<html><p><center>Stops the program</center></p></html>";
	}
	
	String resetCounterButtonText() {
		return "<html><p><center>Resets the counters</center></p></html>";
	}
	
	String speedSelectionBoxText() {
		return "<html><p><center>Sets the animation speed (restart the animation to apply)</center></p></html>";
	}
	
	String colorSelectionBoxText() {
		return "<html><p><center>Sets the color of the DVD logo</center></p></html>";
	}
	
	String mlpText() {
		return "<html><p><center>Window in which the DVD logo is being animated</center></p></html>";
	}
	
	String wallHitLabelText() {
		return "<html><p><center>Displays the number of wall hits</center></p></html>";
	}
	
	String cornerHitLabelText() {
		return "<html><p><center>Displays the number of corner hits</center></p></html>";
	}
	
	String infoLabelText() {
		return "<html><p><center>Displays the tooltip of various program components</center></p></html>";
	}

}
