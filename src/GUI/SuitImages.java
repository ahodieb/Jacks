package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public  class SuitImages {
	
	private static String path = "CardSuits" + File.separator;
	private static String ftype = ".png";
	
	private static boolean ready = false;
	
	private static BufferedImage spade;
	private static BufferedImage heart;
	private static BufferedImage diamond;
	private static BufferedImage tref;
	
	public static boolean isReady() {
		return ready;
	}
	
	public static BufferedImage getDiamond() {
		return diamond;
	}
	
	public static BufferedImage getHeart() {
		return heart;
	}
	
	public static BufferedImage getSpade() {
		return spade;
	}
	
	public static BufferedImage getTref() {
		return tref;
	}
	
	public  static void BufferImages() throws IOException
	{
		spade = ImageIO.read(new File  ( path + "spade" + ftype  ));
		heart = ImageIO.read(new File  ( path + "heart" + ftype  ));
		diamond = ImageIO.read(new File( path + "diamond" + ftype  ));
		tref = ImageIO.read(new File   ( path + "tref" + ftype  ));
		ready = true;
	}
	

}
