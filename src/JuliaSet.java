//Written by Akash Nayar 5/15/2018
//Julia Set Fractal Generator

import java.awt.Dimension;
import javax.swing.JFrame;

public class JuliaSet {
	public static void main(String[] args) {	 
		Window window = new Window();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setPreferredSize(new Dimension(1060, 784));
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
