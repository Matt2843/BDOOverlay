package com.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ui.BDOContentPane;

public class Time {
	
	private DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String date = df.format(new Date()); 

	public boolean isStopWatch = false;
	public boolean isTime = true;
	
	private long startTime;
	
	public void startStopWatch() {
		isTime = false;
		isStopWatch = true;
		startTime = System.currentTimeMillis();
		BDOContentPane.time.setText("00:00:00:00");
		new Thread() {
			public void run() {
				int[] timedata; // 0 = hours, 1 = minutes, 2 = seconds, 3 = miliseconds
				String[] textdata = new String[4];
				while(isStopWatch) {
					timedata = getStopWatchTime();
					for(int i = 0; i < 4; i++) {
						if(timedata[i] < 10) {
							textdata[i] = "0" + timedata[i];
						} else {
							textdata[i] = String.valueOf(timedata[i]);
						}
					}
					BDOContentPane.time.setText(textdata[0] + ":" + textdata[1] + ":" + textdata[2] + ":" + textdata[3].substring(0,2));
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void stopStopWatch() {
		isStopWatch = false;
		updateTime();
	}
	
	public void updateTime() {
		isTime = true;
		new Thread() {
			public void run() {
				while(isTime) {
					date = df.format(new Date());
					date = date.substring(11,16);
					BDOContentPane.time.setText(date);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private int[] getStopWatchTime() {
        long milliTime = System.currentTimeMillis() - startTime;
        int[] out = new int[]{0, 0, 0, 0};
        out[0] = (int)(milliTime / 3600000      );
        out[1] = (int)(milliTime / 60000        ) % 60;
        out[2] = (int)(milliTime / 1000         ) % 60;
        out[3] = (int)(milliTime)                 % 1000;

        return out;
    }
	
	public String getDate() {
		return date;
	}
}
