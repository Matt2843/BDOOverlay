package com.utility;

import java.util.ArrayList;

public class ExperienceCalculator {
	
	private ArrayList<Double> expMeans;
	
	private int level = 1;
	private Double currentExp = 0.0, previousExp = 0.0, expDif = 0.0;
	
	public ExperienceCalculator() {
		expMeans = new ArrayList<Double>();
	}
	
	public String calculateExpHour() {
		Double res = 0.0;
		for(Double i : expMeans) {
			res += i;
		}
		res /= expMeans.size();
		res = res * 12 * 60;
		return String.format("%.5g%n", res) + "%";
	}
	
	public void pushMean() {
		if(currentExp != null && previousExp != null) {
			expDif = currentExp - previousExp;
		}
		previousExp = currentExp;
		
		if(expMeans.size() == 12 && expDif < 4.0 && expDif > 4.0) {
			expMeans.remove(0);
			expMeans.add(expDif);
		} else {
			expMeans.add(expDif);
		}
	}
	
	public String getLevel() {
		return String.valueOf(level);
	}
	public void setLevel(int interpretedLevel) {
		this.level = interpretedLevel;
	}
	public Double getCurrentExp() {
		return currentExp;
	}
	public void setCurrentExp(double currentExp) {
		this.currentExp = currentExp;
	}
	public Double getPreviousExp() {
		return previousExp;
	}
	public void setPreviousExp(double previousExp) {
		this.previousExp = previousExp;
	}

}
