package com.utility;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BossTimers2 {
	
	public ArrayList<String> getTimers() {
		ArrayList<String> theTimers = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect("http://urzasarchives.com/bdo/wbtbdo/wbteu/").get();
			Elements bossNames = doc.getElementsByTag("strong"); // ignore index 0
			Elements tds = doc.getElementsByTag("td");
			for(Element thing : tds) {
				if(thing.toString().matches("<td> (\\w{3}, \\d{2}:\\d{2} \\w{3} - )?\\w{3}, \\d{2}:\\d{2} \\w{3} </td>") || thing.toString().contains("Server Maintenance")) {
					dates.add(thing.toString().replaceAll("<td>|</td>| ", ""));
				}
			}
			
			//dates.set(3, "Wed,23:54CET");
			//dates.set(1, "Wed,23:11CET-Thu,05:30CET"); // for testing
			
			int index = 0;
			for(int i = 1; i < bossNames.size()-1; i++) {
				String result = bossNames.get(i).toString().replaceAll("<strong>|</strong>", "");
				for(int j = 0; j < 3; j++) {
					result += "!" + dates.get(index++).replaceAll("CET", "");
				}
				theTimers.add(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		for(String i : theTimers) {
//			System.out.println(i);
//		}
		return theTimers;
	}
	
}
