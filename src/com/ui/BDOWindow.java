package com.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.utility.AlphaContainer;
import com.utility.CaptureDevice;
import com.utility.UI;

public class BDOWindow extends JFrame implements NativeKeyListener, MouseListener, MouseMotionListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	private BDOContentPane contentPane = new BDOContentPane();
	
	private CaptureDevice captureDevice = new CaptureDevice();
	private BDOBossWindow bossWindow = new BDOBossWindow();
	
	private int xOffset, yOffset;
	public static boolean moveable = true;
	
	public BDOWindow() {
		setAttributes();
		pack();
	}

	private void setAttributes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setType(Type.UTILITY);
		setAlwaysOnTop(true);
		setBackground(UI.MOVEABLEBACKGROUND);
		setPreferredSize(UI.UISIZE);
		setContentPane(new AlphaContainer(contentPane));
		addWindowListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}
	
	/*
	 * ONLY TO BE USED FOR GLOBAL HOTKEYS!
	 */
	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		//NativeKeyEvent.getModifiersText(e.getModifiers()).equals("Shift") && e.getKeyCode() == NativeKeyEvent.VC_SPACE
		if(e.getKeyCode() == NativeKeyEvent.VC_INSERT) {
			if(!BDOContentPane.timeUtil.isStopWatch) {
				BDOContentPane.timeUtil.startStopWatch();
			} else {
				BDOContentPane.timeUtil.stopStopWatch();
			}
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_F7) {
			captureDevice.startCalibrating();
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_F8) {
			captureDevice.startRecording();
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_F9) {
			
			int dialogResult = JOptionPane.showConfirmDialog(null, "Close Overlay?","Warning",JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_F10) {
			if(bossWindow.isVisible()) {
				bossWindow.setVisible(false);
			} else {
				bossWindow.setVisible(true);
			}
		}	
		if(e.getKeyCode() == NativeKeyEvent.VC_F11) {
			if(isVisible()) {
				setVisible(false);
			} else {
				setVisible(true);
			}
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_F12) {
			moveable = !moveable;
			if(moveable) {
				setBackground(UI.MOVEABLEBACKGROUND);
				contentPane.setBorder(UI.BDOBORDER);			
				bossWindow.moveable();			
			} else {
				setBackground(UI.MAINBACKGROUND);
				contentPane.setBorder(null);
				bossWindow.immobile();
			}
		}	
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (moveable) {
			setLocation(e.getXOnScreen() + xOffset, e.getYOnScreen() + yOffset);
		}		
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
	public void windowOpened(WindowEvent e) {
		try {
			GlobalScreen.registerNativeHook();			
			Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
		} catch (NativeHookException ex) {
			ex.printStackTrace();
		}	
		GlobalScreen.addNativeKeyListener(this);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e1) {
			e1.printStackTrace();
		}
        System.runFinalization();
        System.exit(0);	
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
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
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
