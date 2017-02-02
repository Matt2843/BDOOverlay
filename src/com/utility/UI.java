package com.utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public final class UI {

	private UI() {
		// prevent instantiation.
	}
	
	// DIMENSIONS
	public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Dimension UISIZE = new Dimension(135, 75);
	public static final Dimension CAPTUREDEVICESIZE = new Dimension(75, 75);
	public static final Dimension BOSSLABELDIMENSION = new Dimension(275, 40);
	
	// FONTS
	public static Font BOSSFONT = new Font("Segoe Print", Font.PLAIN, 14);
	public static Font STDFONT = new Font("Segoe UI Symbol", Font.PLAIN, 12);
	public static Font TIMEFONT = new Font("Segoe UI Symbol", Font.PLAIN, 20);
	
	// COLORS
	public static Color SPAWNED = Color.PINK;
	public static Color SPAWNABLE = Color.decode("#EF5350");
	public static Color NOTSPAWNABLE = Color.decode("#FFEBEE");
	public static Color MAINBACKGROUND = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	public static Color MOVEABLEBACKGROUND = new Color(0.25f, 0.2f, 0.75f, 0.5f);
	public static Color CAPTUREDEVICEBACKGROUND = Color.WHITE;
	public static Color FOREGROUND = Color.decode("#FFF8D4"); // use if outline is possible
	
	// BORDERS
	public static Border BDOBORDER = BorderFactory.createLineBorder(Color.red);
}
