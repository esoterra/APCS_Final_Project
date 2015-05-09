package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LightImage {
	private BufferedImage image;
	public WritableRaster raster;
	private int[] pixelsI;
	public int width, height;
	
//	public static final int TRANSPARENT=16711900;	//the int code for R:255 G:0 B:220, pink
	public static final int TRANSPARENT=16777215;
	
	public static LightImage newLightImage() {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(LightImage.class.getClass().getResource("/Resources/canvas.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new LightImage(image);
	}
	
	public LightImage(BufferedImage i) {
		width = i.getWidth();
		height = i.getHeight();
		
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		g.drawImage(i, 0, 0, null);
		g.dispose();
		g = null;
		
		raster = image.getRaster();
		pixelsI = ((DataBufferInt) raster.getDataBuffer()).getData();
	}

	
	public void drawOn(LightImage i, int xS, int yS) {
		int color;
		
		for		(int x=0; 	(x<width) && (x+xS < i.width); 	x++)
			for	(int y=0; 	(y<height) && (y+yS < i.height);	y++){
				color = this.getColor(x, y);
				if(y+yS >= 0 && x+xS >= 0)
//					if(color != LightImage.TRANSPARENT)
						i.setColor(x+xS,y+yS,color);
			}
					
	}
	
	public void drawOnNoAlpha(LightImage i, int xS, int yS) {
		for		(int x=0; 	(x<width) && (x+xS < i.width); 	x++)
			for	(int y=0; 	(y<height) && (y+yS < i.height);	y++)
				if(y+yS >= 0 && x+xS >= 0)
					i.setColor(x+xS,y+yS,this.getColor(x, y));
	}
	
	public void drawOn(LightImage i) {
		drawOn(i,0,0);
	}
	
	public LightImage subImage(int x, int y, int width, int height) {
		BufferedImage i = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		LightImage lI = new LightImage(i);
		
		drawOnNoAlpha(lI,-x,-y);
		
		return lI;
	}
	
	public int getColor(int x, int y) {
		return pixelsI[y*width+x];
	}
	
	public void setColor(int x, int y, int color) {
		pixelsI[y*width+x] = color;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}