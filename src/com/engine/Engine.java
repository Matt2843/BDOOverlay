package com.engine;

import javax.swing.SwingUtilities;

import com.ui.BDOWindow;

public class Engine {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new BDOWindow();
            }
        });
		
		
	}

}
