package net.falcon.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import net.falcon.SurfAssist;
import net.falcon.maps.StatHandler;
import net.falcon.maps.SurfMap;

public class LogUpdater implements Runnable{


	public static StageCompletionChecker c = new StageCompletionChecker();
	public static MapCompletionChecker m = new MapCompletionChecker();
	public static MapChangeChecker mc = new MapChangeChecker();
	private static ArrayList<LineChecker> checks = new ArrayList<LineChecker>(); 

	public static ArrayList<String> logLines = new ArrayList<String>();
	public static String username = "Felon Falcon";

	@Override
	public void run() {

		//add all the regex checks that will be ran on each line
		checks.add(new StageCompletionChecker());
		checks.add(new MapCompletionChecker());
		checks.add(new MapChangeChecker());

		File log = new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Team Fortress 2\\tf\\console.log");
		//Every 5 seconds:
		while(true) {
			try {
				if(log.exists()) {
					BufferedReader br = new BufferedReader(new FileReader(log));
					String line;
					Integer lastLine = logLines.size();
					Integer count = 1;
					while ((line = br.readLine()) != null) {
						if(count > lastLine) {
							logLines.add(line);
							for(LineChecker c : checks) {
								c.checkLine(0, line);
							}
						}
						count++;
					}
					if(count < lastLine) {
						System.out.print("Log shrunk. Should not be possible.");
					}
					br.close(); //if the reader is not opened and closed every time it is read, TF2 will not be able to write to it

					//If we are viewing the map that was just updated, update the UI as well.
					SurfMap currentMap = StatHandler.instance().getCurrentMap();
					if(currentMap.name.equals(SurfAssist.controller.currentViewedMap)) {
						SurfAssist.controller.updateMapPane(currentMap);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



}
