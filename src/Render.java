//Written by Akash Nayar 5/15/2018 (Updated 10/21/2018)
//Julia Set Fractal Generator

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Render extends JComponent {
	private int minColor = 0, maxColor = 2000, width = 800, height = 800, zoomSmall = 250, xCoo, counter2, maxIterations;
	private String name = "File name";
	private float cX, cY, spacing;
	private Color RGB = new Color(0, 0 ,0);
	private BufferedImage gradientImage;
	private int[][] pixels, newPixels;
	

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle border = new Rectangle(0, 0, width, height);
		//Rectangle border = new Rectangle(400-(width/2), 400-(height/2), width, height);
		while(SmallRender.getGradient().equals(null)) {
		}
		gradientImage = SmallRender.getGradient();
		draw(g);
		g2.setColor(Color.BLACK);
		g2.draw(border);	
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setCX(float xC) {
		this.cX = xC;
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
	
	public void setMaxC(int maxColor) {
		this.maxColor = maxColor;
	}

	public void setMinC(int minColor) {
		this.minColor = minColor;
	}
	
	public void setName(String s) {
		name = s;
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

	public void save(){
    	BufferedImage  bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	Graphics2D g2 = bImg.createGraphics();
    	paintAll(g2);
    	if(valid(name)) {
    		try {
    			if (ImageIO.write(bImg, "png", new File("saved/"+name+".png"))){
    				System.out.println("Your file has been saved!");
    				Desktop.getDesktop().open(new File("saved"));
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	else 
    		System.out.println("Please make sure your file name consists of only letters and digits.");
	}

	public boolean valid(String name) {
		char[] chars = name.toCharArray();
		for (char c: chars){
		    if(!Character.isLetterOrDigit(c)&&!Character.isWhitespace(c))
		    	return false;
		}
		return true;
	}

	public boolean escaped(float x, float y, float cx, float cy) {
		float xtemp;
		counter2 = 0;
		float threshold = (float) 2.0;
		for(int counter = 0; counter < 255; counter++) {
			xtemp = (float)(Math.pow(x, 2)-Math.pow(y, 2));
			y = 2*x*y + cy;
			x = xtemp + cx;
			if(radius(x, y)>=threshold) {
				counter2=counter;
				return true;
			}
		}
		return false;
	}
	
	public void getRGB(int x, int y){
		maxColor = SmallRender.getMaxC();
		minColor = SmallRender.getMinC();
		RGB = new Color(gradientImage.getRGB(x, y));
	}

	public void getPixels() {
		pixels = new int[800][800];
		getMaxIterations();
		spacing = (maxColor-minColor)/(maxIterations);
		float xLoc, yLoc;
		for(int x = 0; x < 800; x++) {
			xLoc = getXLoc(x);
			for(int y = 0; y<800; y++) {
				yLoc = getYLoc(y);
				if(escaped(xLoc, yLoc, cX, cY)){
					if(minColor<maxColor)
						xCoo = maxColor-(int)(spacing*counter2)-1;
					else
						xCoo = minColor-(int)(spacing*counter2)-1;
					if(xCoo<0)
						xCoo = 0;
					if(xCoo>2400)
						xCoo = 2400;
					pixels[y][x] = xCoo;
				}
				else
					pixels[y][x] = -1;
			}
		}
	}
	
	public int getMaxIterations(){
		maxIterations = 0;
		float xLoc, yLoc;
		for(int x = 0; x < 800; x++) {
			xLoc = getXLoc(x);
			for(int y = 0; y < 800; y++) {
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
	
	public void smooth() {
		newPixels = new int[800][800];
		int total = 0, counter = 0;
		for(int y = 0; y<800;y++) {
			for(int x = 0; x<800;x++) {
				counter = 0;
				total = 0;
				for(int y1 = y-20; y1<=y+20; y1++) {
					if(y1>=800||y1<=0)
						continue;
					for(int x1 = x-20; x1<=x+20; x1++) {
						if(x1>=800||x1<=0)
							continue;
						if(Math.abs(pixels[y][x]-pixels[y1][x1])<200) {
							total+=pixels[y1][x1];
							counter++;
						}
					}
				}
				if(counter == 0) {
					newPixels[y][x] = pixels[y][x];
					continue;
				}
				newPixels[y][x] = total/counter;
			}
		}
	}
	
	public void draw(Graphics g) {
		getPixels();
		smooth();
		Graphics2D g2 = (Graphics2D) g;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
					if(pixels[y][x]==-1||newPixels[y][x]==-1) {
						g2.setColor(Color.BLACK);
						g2.drawLine(x, y, x, y);
						continue;
					}
					getRGB(newPixels[y][x], 100);
					Color color = RGB;
					g2.setColor(color);
					g2.drawLine(x, y, x, y);
			}
		}
	}
}