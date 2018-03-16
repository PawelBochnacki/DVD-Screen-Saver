package com.pawel.DVD;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ReadFiles extends JFrame{
	
	private AudioInputStream ais;
	private Clip clip;
	private BufferedImage[] DVDimgFiles;

	public ReadFiles() throws LineUnavailableException, IOException {
		
		//read the audio file
		try {
			ais = AudioSystem.getAudioInputStream(new File("data\\ding.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "Error, file 'ding.wav' not found in 'data' folder."
					+ " Make sure the file is placed in the proper folder and run the program again.");
			e1.printStackTrace();
			System.exit(0);
		}

		//read all the DVD logos
		DVDimgFiles = new BufferedImage[5];
		for (int j = 1; j < DVDimgFiles.length; j++) {			
			try {
				DVDimgFiles[j-1] = ImageIO.read(new File("data\\DVD_Logo" + j +".jpg"));	
				System.out.println(DVDimgFiles[j-1].toString());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error, one (or more) image files not found in 'data' folder."
						+ " Make sure the files are placed in the proper folder and run the program again.");
				e.printStackTrace();
				System.exit(0);
				
			}
		}
	}
	
	Clip getAudio() throws LineUnavailableException, IOException {
		return clip;
	}
	
	BufferedImage[] getDVDimage() {
		return  DVDimgFiles;
	}
	
	AudioInputStream getAudioInputStream() {
		return ais;
	}

}
