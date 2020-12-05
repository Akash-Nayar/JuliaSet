//Written by Akash Nayar 5/15/2018
//Julia Set Fractal Generator

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class SmallRender extends JComponent {
	private static int maxColor = 2000, minColor = 0, maxIterations = 0;
	private int width = 160, height = 160, xCoo, counter2, zoomSmall = 50;
	private static float spacing;
	private float cX, cY;
	private Color RGB = new Color(0, 0, 0);
	private final File GRADIENT_FILE = new File("resources/gradient.jpg");
	private static BufferedImage gradientImage;
  
	public void loadImage() {
	  	try {
	  		gradientImage = ImageIO.read(new File(GRADIENT_FILE.toURI()));
	  	} catch (IOException e) {
	  	}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle border = new Rectangle(0, 0, width, height);
		loadImage();
		drawJulia(g);
		g2.setColor(Color.BLACK);
		g2.draw(border);	
	}
	
	public static BufferedImage getGradient() {
		return gradientImage;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setCX(float cX) {
		this.cX = cX;
	}
	
	public void setCY(float cY) {
		this.cY = cY;
	}
	
	public float getCX() {
		return cX;
	}
	
	public float getCY() {
		return cY;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setZoomSmall(int zoomSmall) {
		this.zoomSmall = zoomSmall;
	}
	
	public void setMaxC(int m) {
		maxColor = m;
	}

	public void setMinC(int m) {
		minColor = m;
	}

	public static int getMax() {
		return maxIterations;
	}
	
	public static float getSpacing() {
		return spacing;
	}

	public static int getMaxC() {
		return maxColor;
	}

	public static int getMinC() {
		return minColor;
	}

	public float getXLoc(int x) {
		return (float)(x-(width/2))/(zoomSmall);
	}
	
	public float getYLoc(int y) {
		return -1*((float)(y-(height/2))/(zoomSmall));
	}
	
	public float radius(float x, float y) {
		return (float)(Math.sqrt((Math.pow(x, 2))+(Math.pow(y, 2))));
	}	

	public boolean escaped(float x, float y, float cx, float cy) {
		counter2 = 0;
		float xtemp;
		float threshold = (float) 2.0;
		for(int counter = 0; counter < 255; counter++) {
			xtemp = x*x-y*y;
			y = 2*x*y + cy;
			x = xtemp + cx;
			if(radius(x, y)>=threshold) {
				counter2=counter;
				return true;
			}
		}
		return false;
	}

	public int getMaxIterations(){
		maxIterations = 0;
		float xLoc, yLoc;
		for(int x = 0; x <= width; x++) {
			xLoc = getXLoc(x);
			for(int y = 0; y <= height; y++) {
				yLoc = getYLoc(y);
				if(escaped(xLoc, yLoc, cX, cY)){
					if(counter2>maxIterations) {
						maxIterations = counter2;
					}
				}
			}
		}
		return maxIterations;
	}
	
	public void getRGB(int x, int y) {
		RGB = new Color(gradientImage.getRGB(x, y));
	}
	 
	public void drawJulia(Graphics g) {
		maxIterations = getMaxIterations();
		if(maxIterations == 0)
			maxIterations++;
		spacing = (maxColor-minColor)/(maxIterations);
		float xLoc, yLoc;
		Graphics2D g2 = (Graphics2D) g;
		for(int x = 0; x <= width; x++) {
			xLoc = getXLoc(x);
			for(int y = 0; y <= height; y++) {
				yLoc = getYLoc(y);
				g2.setColor(Color.BLACK);
				if(escaped(xLoc, yLoc, cX, cY)){
					if(minColor<maxColor)
						xCoo = maxColor-(int)(spacing*(counter2))-1;
					else
						xCoo = minColor+(int)(spacing*(counter2))-1;
					if(xCoo<0)
						xCoo = 0;
					getRGB(xCoo, 100);
					Color gradient = RGB;
					g2.setColor(gradient);
				}
				g2.drawLine(x, y, x, y);
			}
		}
	}
}