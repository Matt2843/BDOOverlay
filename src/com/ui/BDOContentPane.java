package com.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.utility.Time;
import com.utility.UI;

public class BDOContentPane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static Time timeUtil = new Time();
	
	public static JLabel time, exp, level;
	
	public BDOContentPane() {
		setAttributes();
		constructLabels();
		setVisible(true);
	}

	private void setAttributes() {
		setLayout(new GridLayout(3,1));
		setBackground(null);
		setBorder(UI.BDOBORDER);
	}
	
	private void constructLabels() {
		time = new JLabel(timeUtil.getDate());
		time.setForeground(UI.FOREGROUND);
		time.setFont(UI.TIMEFONT);
		
		timeUtil.updateTime();
		
		level = new JLabel("Lv. -");
		level.setForeground(UI.FOREGROUND);
		level.setFont(UI.STDFONT);
		
		exp = new JLabel("Exp/hr. -");
		exp.setForeground(UI.FOREGROUND);
		exp.setFont(UI.STDFONT);
		
		add(time);
		add(level);
		add(exp);
	}
	
	public static void setLabelInfo(String l, String e) {
		level.setText("Lv. " + l);
		exp.setText("Exp/hr. " + e);
	}
	
}
