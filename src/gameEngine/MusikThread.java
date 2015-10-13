package gameEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusikThread extends JPanel implements Runnable {

	private AudioPlayer player = AudioPlayer.player;
	private AudioStream audioStream;
	private boolean isOn = false;

	@Override
	public void run() {
		while (true) {
			try {
				File f = new File("hauptmenue_v1.wav");
				InputStream in = new FileInputStream(f);
				audioStream = new AudioStream(in);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (isOn == false) {
				player.start(audioStream);
				isOn = true;
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
