//Written by Akash Nayar 5/15/2018
//Julia Set Fractal Generator

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Window extends JFrame{
	private Render fullSizeRender;
	private SmallRender smallSizeRender;
	private JSlider xSlider, ySlider, maxSlider, minSlider, zoomSmallSlider;
	private JLabel xVariance, yVariance, maxColorL, minColorL, zoomSmall, spectrum;
	private Image image;
	private JTextField name, xPixels, yPixels, xVB, yVB;
	private File file = new File("resources/gradientSmall.jpg");
	
	public Window() {		
		super("Julia Set Fractal Generator");
		setLayout(null);
	    loadImage();
		
		//X Slider
		xSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		xSlider.setMajorTickSpacing(10);
		xSlider.setMinorTickSpacing(1);
		xSlider.setPaintTicks(true);
		xSlider.setBounds(850, 10, 160, 30);
		xSlider.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					fullSizeRender.setCX((float)xSlider.getValue()/100);
					smallSizeRender.setCX((float)xSlider.getValue()/100);
					xVB.setText(Integer.toString(xSlider.getValue()));
					smallSizeRender.repaint();
				}
			}
		);
		add(xSlider);

		//Y Slider
		ySlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		ySlider.setMajorTickSpacing(10);
		ySlider.setMinorTickSpacing(1);
		ySlider.setPaintTicks(true);
		ySlider.setBounds(850, 65, 160, 30);
		ySlider.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					fullSizeRender.setCY((float)ySlider.getValue()/100);
					smallSizeRender.setCY((float)ySlider.getValue()/100);
					yVB.setText(Integer.toString(ySlider.getValue()));
					smallSizeRender.repaint();
				}
			}
		);
		add(ySlider);
		
		//Min Slider
		minSlider = new JSlider(JSlider.HORIZONTAL, 0, 2399, 0);
		minSlider.setMajorTickSpacing(300);
		minSlider.setMinorTickSpacing(100);
		minSlider.setPaintTicks(true);
		minSlider.setBounds(850, 493, 160, 50);
		minSlider.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					smallSizeRender.setMinC(minSlider.getValue());
					smallSizeRender.repaint();
				}
			}
		);
		add(minSlider);
		
		//Max Slider
		maxSlider = new JSlider(JSlider.HORIZONTAL, 0, 2400, 2000);
		maxSlider.setMajorTickSpacing(300);
		maxSlider.setMinorTickSpacing(100);
		maxSlider.setPaintTicks(true);
		maxSlider.setBounds(850, 410, 160, 50);
		maxSlider.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					smallSizeRender.setMaxC(maxSlider.getValue());
					smallSizeRender.repaint();
				}
			}
		);
		add(maxSlider);
		
		//Zoom Slider
		zoomSmallSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 10);
		zoomSmallSlider.setMajorTickSpacing(50);
		zoomSmallSlider.setMinorTickSpacing(10);
		zoomSmallSlider.setPaintTicks(true);
		zoomSmallSlider.setBounds(850, 583, 160, 50);
		zoomSmallSlider.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					fullSizeRender.setZoomSmall(zoomSmallSlider.getValue()*25);
					smallSizeRender.setZoomSmall(zoomSmallSlider.getValue()*5);
					smallSizeRender.repaint();
				}
			}
		);
		add(zoomSmallSlider);
		
				
		JButton generate = new JButton("Generate");
		generate.setBounds(850, 120, 160, 60);
		generate.addActionListener(new Generate());
		add(generate);
		
		JButton save = new JButton("Save");
		save.setBounds(850, 680, 160, 60);
		save.addActionListener(new Save());
		add(save);

		fullSizeRender = new Render();
		fullSizeRender.setBackground(Color.WHITE);
		fullSizeRender.setBounds(0, 0, 800, 800);
		add(fullSizeRender);

		smallSizeRender = new SmallRender();
		smallSizeRender.setBackground(Color.WHITE);
		smallSizeRender.setBounds(850, 230, 160, 160);
		add(smallSizeRender);
		
		zoomSmall = new JLabel("Zoom");
		zoomSmall.setBounds(850, 569, 160 , 18);
		add(zoomSmall);
		
		xVariance = new JLabel("X Variance");
		xVariance.setBounds(850, 38, 160 , 18);
		add(xVariance);
		
		yVariance = new JLabel("Y Variance");
		yVariance.setBounds(850, 93, 160 , 18);
		add(yVariance);
		
		spectrum = new JLabel(new ImageIcon(image));
		spectrum.setVisible(true);
		spectrum.setBounds(857, 460, 147, 33);
		add(spectrum);
		
		maxColorL = new JLabel("Maximum Color");
		maxColorL.setBounds(850, 396, 160 , 18);
		add(maxColorL);
	
		minColorL = new JLabel("Minimum Color");
		minColorL.setBounds(850, 538, 160 , 18);
		add(minColorL);
		
		xPixels = new JTextField("800");
		xPixels.setBounds(850, 640, 75, 30);
		xPixels.addActionListener(new Pixels());
		add(xPixels);
		
		yPixels = new JTextField("800");
		yPixels.setBounds(935, 640, 75, 30);
		yPixels.addActionListener(new Pixels());
		add(yPixels);

		name = new JTextField("File name");
		name.setBounds(850, 750, 160, 30);
		add(name);

		xVB = new JTextField("0");
		xVB.setBounds(850, 190, 75, 30);
		add(xVB);
		
		yVB = new JTextField("0");
		yVB.setBounds(935, 190, 75, 30);
		add(yVB);
	}
	
	public class Generate implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			fullSizeRender.setWidth(Integer.parseInt(xPixels.getText()));
			smallSizeRender.setWidth(Integer.parseInt(xPixels.getText())/5);
			fullSizeRender.setHeight(Integer.parseInt(yPixels.getText()));
			smallSizeRender.setHeight(Integer.parseInt(yPixels.getText())/5);
			smallSizeRender.setBounds(850+((160-smallSizeRender.getWidth())/2), 230+((160-smallSizeRender.getHeight())/2), 160, 160);
			fullSizeRender.setBounds((800-fullSizeRender.getWidth())/2, ((800-fullSizeRender.getHeight())/2), 800, 800);
			fullSizeRender.setHeight(Integer.parseInt(yPixels.getText()));
			smallSizeRender.setHeight(Integer.parseInt(yPixels.getText())/5);
			fullSizeRender.setWidth(Integer.parseInt(xPixels.getText()));
			smallSizeRender.setWidth(Integer.parseInt(xPixels.getText())/5);
			smallSizeRender.setBounds(850+((160-smallSizeRender.getWidth())/2), 230+((160-smallSizeRender.getHeight())/2), 160, 160);
			fullSizeRender.setBounds((800-fullSizeRender.getWidth())/2, ((800-fullSizeRender.getHeight())/2), 800, 800);
			fullSizeRender.setCX(Float.parseFloat(xVB.getText())/100);
			smallSizeRender.setCX(Float.parseFloat(xVB.getText())/100);
			fullSizeRender.setCY(Float.parseFloat(yVB.getText())/100);
			smallSizeRender.setCY(Float.parseFloat(yVB.getText())/100);
			fullSizeRender.repaint();	
		}	
	}
	
	public class Save implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			fullSizeRender.setName(name.getText());
			fullSizeRender.save();	
		}
	}

	public class Pixels implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(isInt(xPixels.getText())&&(Integer.parseInt(xPixels.getText())<=800)&&(Integer.parseInt(xPixels.getText())>0)) {
				fullSizeRender.setWidth(Integer.parseInt(xPixels.getText()));
				smallSizeRender.setWidth(Integer.parseInt(xPixels.getText())/5);
				fullSizeRender.setHeight(Integer.parseInt(yPixels.getText()));
				smallSizeRender.setHeight(Integer.parseInt(yPixels.getText())/5);
				smallSizeRender.setBounds(850+((160-smallSizeRender.getWidth())/2), 230+((160-smallSizeRender.getHeight())/2), 160, 160);
				fullSizeRender.setBounds(((800-fullSizeRender.getWidth())/2), ((800-fullSizeRender.getHeight())/2), 800, 800);
				smallSizeRender.repaint();
			}
		}
	}

	public boolean isInt(String pixels) {
		try{
			Integer.parseInt(pixels);
		    return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
		
	public void loadImage() {
		try {
			image = ImageIO.read(new File(file.toURI()));
		} catch (IOException e) {
		}
	}
}