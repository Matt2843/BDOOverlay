package com.ui;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

import com.utility.UI;

public class BDOBossLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	private JLabel[] labels; // 0 Boss Name, 1 Last Spawn, 2 Next Spawn, 3 Est Spawn
	
	public BDOBossLabel() {
		setAttributes();
		configureLabels();
		setVisible(true);
	}

	private void setAttributes() {
		setPreferredSize(UI.BOSSLABELDIMENSION);
		setBackground(null);
		setLayout(new GridLayout(2, 2));
	}

	private void configureLabels() {
		labels = new JLabel[4];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel("NaN");
			labels[i].setFont(UI.STDFONT);
			labels[i].setVisible(true);
			labels[i].setForeground(UI.FOREGROUND);
		}
		labels[0].setFont(UI.BOSSFONT);
	}

	public void updateLabel(String bossString) {
		String[] split = bossString.split("!"); // ! split delimiter.		
		labels[0].setText(split[0]);
		labels[1].setText("L: " + split[1]);
		labels[2].setText("N: " + split[2]);
		labels[3].setText("E: " + split[3]);
		for (int i = 0; i < split.length; i++) {;
			add(labels[i]);
		}
		
		testAvailability(split[2], split[1]);	
	}

	private void testAvailability(String nextSpawn, String lastSpawn) {
		SimpleDateFormat format = new SimpleDateFormat("E,HH:mm");
		Date currentDate = new Date();
		
		String currentTime = format.format(currentDate);
		String startWindow = nextSpawn.substring(0, 9);
		String endWindow = nextSpawn.substring(10, nextSpawn.length());

		if(isTimeInSpawnWindow(startWindow, endWindow, currentTime)) {			
			labels[2].setText("<Spawnable>");
			labels[2].setForeground(UI.SPAWNABLE);
		}	
		
		if(!lastSpawn.contains("Server")) {
			String[] s = lastSpawn.split(",");
			if(isTimeInSpawnWindow(lastSpawn.substring(0,9), s[0]+","+getTime(getSeconds(s[1])+6*60), currentTime)) {
				labels[2].setText("<Spawned>");
				labels[2].setForeground(UI.SPAWNED);
			}
		}
		
	}
	
	private boolean isTimeInSpawnWindow(String start, String end, String current) {
		System.out.println("Start: " + start + " Current: " + current + " End: " + end);
		String[] s = start.split(",");
		int s1 = getSeconds(s[1]);
		
		String[] e = end.split(",");
		int e1 = getSeconds(e[1]);
		
		String[] c = current.split(",");
		int c1 = getSeconds(c[1]);
		
		if((s[0].equals(c[0])) && (e[0].equals(c[0]))) {
			return ((s1 <= c1) && (e1 >= c1));
		} else if(s[0].equals(c[0])) {
			return (s1 <= c1);
		} else if(e[0].equals(c[0])) {
			return (e1 >= c1);
		}
		return false;
	}
	
	private String getTime(int seconds) {
		int minutes = seconds/60;
		int hours = Math.round(minutes/60);	
		return hours + ":" + (minutes - hours*60);
	}
	
	private int getSeconds(String time) {
		String[] tmp = time.split(":");
		return (int) (Integer.valueOf(tmp[0]) * Math.pow(60,2) + Integer.valueOf(tmp[1]) * 60);
	}
	
	public JLabel[] getLabels() {
		return labels;
	}

}
