package entity;

import gui.Button;
import gui.Monolog;

import java.awt.image.BufferedImage;

public class Option extends Button {
	
	private Monolog tooltip;
	
	public Option(BufferedImage img, boolean drawstring, boolean bghighligthing) {
		super(img, drawstring, bghighligthing);	
	}
	
	public void setToolTip(Monolog tooltip) {
		this.tooltip = tooltip;
	}
	
	public Monolog getToolTip() {
		return tooltip;
	}
	
}
