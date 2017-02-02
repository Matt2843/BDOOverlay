package com.ui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.utility.BossTimers2;
import com.utility.UI;

public class BDOBossPane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BossTimers2 bs2 = new BossTimers2();
	private ArrayList<String> bts = bs2.getTimers();
	private BDOBossLabel[] bossLabels;
	
	public BDOBossPane() {
		setAttributes();
		addBossLabels();
		updateLabels();
		setVisible(true);
	}

	private void setAttributes() {
		setLayout(new GridLayout(8,1));
		setBackground(null);
		setBorder(UI.BDOBORDER);
	}

	private void addBossLabels() {
		bossLabels = new BDOBossLabel[8];
		for(int i = 0; i < bossLabels.length; i++) {
			bossLabels[i] = new BDOBossLabel();
			bossLabels[i].updateLabel(bts.get(i));
		}
		sortGrid();
	}
	
	public void updateLabels() {
		new Thread(){
			@Override
			public void run() {
				while(true) {
					System.out.println("Updating info");
					try {
						Thread.sleep(45 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					bts = bs2.getTimers();
					for(int i = 0; i < bossLabels.length; i++) {
						bossLabels[i].updateLabel(bts.get(i));
					}
					sortGrid();
				}
			}					
		}.start();
	}
	
	public void sortGrid() {
		removeAll();
		setVisible(false);
		for(BDOBossLabel label : bossLabels) {
			if(label.getLabels()[2].getText().equals("<Spawned>")) {
				add(label);
			}
		}
		for(BDOBossLabel label : bossLabels) {
			if(label.getLabels()[2].getText().equals("<Spawnable>")) {
				add(label);
			}
		}
		for(BDOBossLabel label : bossLabels) {
			if(!label.getLabels()[2].getText().equals("<Spawnable>") && !label.getLabels()[2].getText().equals("<Spawned>")) {
				add(label);
			}
		}
		setVisible(true);
	}



}
