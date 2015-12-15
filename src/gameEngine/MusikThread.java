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
	private Thread t;
	
	public MusikThread()
	{
		start();
	}
	
	void stop()
	{
		t = null;
		player = null;
		if(audioStream != null)
		{
			try {
				audioStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			audioStream = null;
		}
		isOn = false;
	}
	
	void start()
	{
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while (t != null) {
			if (isOn == false) {
				File f = new File("data/Music/hauptmenue_v1.wav");
				InputStream in;
				try {
					in = new FileInputStream(f);
					audioStream = new AudioStream(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				player = AudioPlayer.player;
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
