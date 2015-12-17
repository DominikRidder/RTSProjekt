package gameEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusikThread extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AudioPlayer player;
	private AudioStream audioStream;
	private boolean isOn = false;

	@Override
	public void run() {
		while (true) {
			try {
				File f = new File("data/Music/hauptmenue_v1.wav");
				InputStream in = new FileInputStream(f);
				audioStream = new AudioStream(in);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (isOn == false && Game.getSetting().getValueBool("cl_s_sound")) {
				player = AudioPlayer.player;
//				player.start(audioStream);
				isOn = true;
			}
			
			if(!Game.getSetting().getValueBool("cl_s_sound") && isOn)
			{
				player = null;
				isOn = false;
				
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
