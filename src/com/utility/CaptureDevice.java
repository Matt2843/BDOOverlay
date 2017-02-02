package com.utility;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ui.BDOContentPane;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class CaptureDevice extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	public boolean calibrating = false;
	public boolean recording = false;
	
	private ExperienceCalculator expC = new ExperienceCalculator();
	
	private Rectangle captureLocation = null;
	private BufferedImage capturedImage = null;
	
	private JPanel device;
	
	private Otsu processing;
	private Robot jarvis;
	
	private int x,y;
	
	public CaptureDevice() {
		setAttributes();
		configureDevice();
		pack();
	}
	
	private void setAttributes() {
		setUndecorated(true);
		setType(Type.UTILITY);
		setAlwaysOnTop(true);
		setOpacity(0.5f);
		setPreferredSize(UI.CAPTUREDEVICESIZE);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private void configureDevice() {
		processing = new Otsu();
		try {
			jarvis = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		device = new JPanel();
		device.setBackground(UI.CAPTUREDEVICEBACKGROUND);
		device.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(device);
	}
	
	public void startCalibrating() {
		setVisible(true);
		calibrating = true;
		setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
	}
	
	public void stopCalibrating() {		
		calibrating = false;
	}
	
	private void setExpInfo(ArrayList<String> data) {
		if(data.size() == 3) {
			int interpretedLevel = Integer.parseInt(data.get(1).replaceAll("%", ""));
			if(interpretedLevel >= 1 && interpretedLevel <= 100) {
				expC.setLevel(interpretedLevel);
			}				
			double interpretedExp = Double.parseDouble(data.get(2).replaceAll("%", ""));
			if(data.get(2).matches("\\d{2}.\\d{3}%")){
				expC.setCurrentExp(interpretedExp);
				if(expC.getPreviousExp() == 0.0) {
					expC.setPreviousExp(interpretedExp);
				}		
			}
		}
	}
	
	public void startRecording() {
		recording = true;
		new Thread() {
			@Override
			public void run() {
				captureImage();
				ArrayList<String> data = imageToText();
				setExpInfo(data);
				while(recording) {
					long startTime = System.currentTimeMillis(); // Star stopwatch
					captureImage();
					data = imageToText();
					setExpInfo(data);			
					expC.pushMean();		
					BDOContentPane.setLabelInfo(expC.getLevel(), expC.calculateExpHour());
					long endTime = System.currentTimeMillis();
					long elapsedTime = endTime - startTime;
					try {
						Thread.sleep(5000 - elapsedTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}	
		}.start();
		captureImage();
	}
	
	private void captureImage() {	
		capturedImage = jarvis.createScreenCapture(captureLocation);
		capturedImage = processing.toGray(capturedImage);
		capturedImage = processing.binarize(capturedImage);
	}
	
	private ArrayList<String> imageToText() {
		String ocrRes = null;
		if(capturedImage != null) {
			final ITesseract instance = new Tesseract();			
			instance.setDatapath("Tess4J/tessdata");
			instance.setTessVariable("tessedit_char_whitelist", ".%0123456789");
			instance.setTessVariable("classify_bln_numeric_mode", "1");			
			try {
				ocrRes = instance.doOCR(capturedImage);
				ocrRes = ocrRes.replaceAll(" ", "");
				ocrRes = ocrRes.replaceAll("[\\n]+", "!");
				return new ArrayList<String>(Arrays.asList(ocrRes.split("!")));
			} catch (TesseractException e) {
				System.out.println("OCR FAILED");
			}
		}		
		return null;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(calibrating) {
			x = e.getXOnScreen();
			y = e.getYOnScreen();
			x = x - getWidth()/2;
			y = y - getHeight()/2;
			setLocation(x, y);
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(calibrating) {
			stopCalibrating();
		} else {
			startCalibrating();
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			captureLocation = new Rectangle(device.getLocationOnScreen().x, device.getLocationOnScreen().y, getWidth(), getHeight());
			stopCalibrating();
			setVisible(false);
		}
		if(e.getKeyChar() == '+') {
			setPreferredSize(new Dimension(getWidth()+5, getHeight()+5));
			setLocation(getLocation().x - 5, getLocation().y - 5);
		}
		if(e.getKeyChar() == '-') {
			setPreferredSize(new Dimension(getWidth()-5, getHeight()-5));
			setLocation(getLocation().x - 5, getLocation().y - 5);
		}
		
		if(e.getKeyCode() == 38) {
			setLocation(getLocation().x, getLocation().y - 2);
		}
		if(e.getKeyCode() == 40) {
			setLocation(getLocation().x, getLocation().y + 2);
		}				
		if(e.getKeyCode() == 37) {
			setLocation(getLocation().x - 2, getLocation().y);
		}		
		if(e.getKeyCode() == 39) {
			setLocation(getLocation().x + 2, getLocation().y);
		}
		pack();	
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
