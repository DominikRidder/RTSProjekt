package gui;

import gameEngine.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Monolog extends GuiElement {

		private String text;
		private String[] words;
		private Color textcolor = Color.WHITE, backgroundcolor = Color.BLACK;
		private Font font;

		public Monolog(String text) {
			this.text = text;
			words = text.split("\\s+");
		}

		public void setText(String text) {
			this.text = text;
			words = text.split("\\s+");
		}

		public void setTextColor(Color c) {
			textcolor = c;
		}

		public void setBackgroundColor(Color c) {
			backgroundcolor = c;
		}
		
		public void setFont(Font font) {
			this.font = font;
		}

		public void onDraw(Graphics2D g2d) {
			int width = getWidth();
			int height = getHeight();
			Font swap = null;
			
				if (font != null) {
					swap = g2d.getFont();
					g2d.setFont(font);
				}
				int stringwidth = g2d.getFontMetrics().stringWidth(text);
				int stringheight = g2d.getFontMetrics().getHeight();
				int rows = height/(stringheight+5);
				int maxtextlength = rows*width;
				
				int x=0,y=stringheight;
				
				g2d.setColor(backgroundcolor);
				g2d.fill3DRect(getX(), getY(), width, height, true);
				g2d.setColor(textcolor);
				for (String word : words){
					int wordwidth = g2d.getFontMetrics().stringWidth(word);
					if (x+wordwidth+5>width){
						x=0;
						y+=stringheight+5;
					}
					g2d.drawString(word, getX() + x ,getY()+y);
					x+= wordwidth+5;
				}
				if (swap != null){
					g2d.setFont(swap);
			}
		}

		public void onUpdate(Screen screen) {
		}
}
