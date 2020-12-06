# JuliaSet Fractal Generator

JuliaSet Fractal Generator is an app that allows for customized fractal wallpaper and image generation.

The fractal generation works roughly as follows: For every coordinate in the x-y plane within a certain window, do the following. You add some certain complex number C to that coordinate and you square the result. You repeat this process many times for each coordinate. Eventually, one of two things will happen: Either the resulting complex number goes off to infinite, or it stays bounded within a certain radius from where it began. If a specific coordinate remained bounded and didn't go off to infinty, even after squaring it and adding C many times, you color it in black. For the coordinates that went off to infinity, you could either just color them a different color. However, it looks far cooler if you color those escaping points based on how quickly they escaped. This creates an almost gradient effect, allowing one to see which points immediately went off to infinity and which ones took more time. Given a range of colors, just color the escaping points based on their relative escape speed and the corresponding color in that range. 

The GUI looks like this:
![GUI](https://github.com/Akash-Nayar/JuliaSet/blob/main/saved/gui.png)

The first two sliders on the right simply determine the x and y components of the complex number C, which essentially determines the nature of the fractal.
The two text-boxes directly under the "Generate" button are simply to view the values of the x and y variance sliders.

Next, the one can adjust the color range of the fractal image using the Maximum and Minimum Color sliders. This basically sets the rangle of colors the escaping coordinates should be painted, those which escaped quicker would be closer to the "Maximum" color and visa versa for the slower-escaping coordinates.

The zoom slider towards the bottom simply allows for zooming into the image to admire the fractals.

The two text boxes above the "Save" button allow for cropping the image.

Lastly, the "Save" button saves and image of the currently generated fractal which the file name specified in the ensuing text-box.

Here are some of the images produced with this program: 
![Example 1](https://github.com/Akash-Nayar/JuliaSet/blob/main/saved/WOAH.png)
![Example 2](https://github.com/Akash-Nayar/JuliaSet/blob/main/saved/epic.png)

I had hoped to at some point create an app with this that one could simply download and use given they had Java installed. I might do this in the future.
