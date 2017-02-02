package com.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.utility.AlphaContainer;
import com.utility.UI;

public class BDOBossWindow extends JFrame implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private BDOBossPane contentPane = new BDOBossPane();
	private int xOffset, yOffset;
	
	public BDOBossWindow() {
		setAttributes();
		pack();
	}

	private void setAttributes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setType(Type.UTILITY);
		setAlwaysOnTop(true);
		setBackground(UI.MOVEABLEBACKGROUND);
		setContentPane(new AlphaContainer(contentPane));
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);	
	}
	
	public void moveable() {
		setBackground(UI.MOVEABLEBACKGROUND);
		contentPane.setBorder(UI.BDOBORDER);
	}
	
	public void immobile() {
		setBackground(UI.MAINBACKGROUND);
		contentPane.setBorder(null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (BDOWindow.moveable) {
			setLocation(e.getXOnScreen() + xOffset, e.getYOnScreen() + yOffset);
		}		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		xOffset = getX() - e.getXOnScreen();
		yOffset = getY() - e.getYOnScreen();	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (getLocation().x < 0) {
			setLocation(0, getLocation().y);
		}
		if (getLocation().y < 0) {
			setLocation(getLocation().x, 0);
		}
		if (getLocation().x > UI.SCREENSIZE.width - getWidth()) {
			setLocation(UI.SCREENSIZE.width - getWidth(), getLocation().y);
		}
		if (getLocation().y > UI.SCREENSIZE.height - getHeight()) {
			setLocation(getLocation().x, UI.SCREENSIZE.height - getHeight());
		}		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
