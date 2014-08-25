package net.falcon.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.falcon.SurfAssist;

/**
 * This singleton class handles all registration and tracking
 * of maps that SurfAssist is aware of.
 *
 */
public class StatHandler {

	private static StatHandler INSTANCE;
	private SurfMap currentMap;
	private String currentUsername = "Felon Falcon";


	private final HashMap<String, SurfMap> mapCache = new HashMap<String, SurfMap>();

	/**
	 * Finds a SurfMap with the specified name, if it exists.
	 * @param name the SurfMap's supposed name.
	 * @return the SurfMap. Returns null if no map found.
	 */
	public SurfMap findMap(String name) {
		return mapCache.get(name);
	}

	public ArrayList<String> findMapNamesStartingWith(String name) {
		ArrayList<String> eligibleMaps = new ArrayList<String>();
		for(String s : mapCache.keySet()) {
			if(s.startsWith(name)) {
				eligibleMaps.add(s);
			}
		}
		return eligibleMaps;
	}

	public void registerMapChange(String mapName) {
		SurfMap newMap = findMap(mapName);
		if(newMap == null) {
			mapCache.put(mapName, new SurfMap(mapName));
			SurfAssist.controller.updateMapList();
		}
		currentMap = newMap;
	}


	public void registerMapCompletion(String time, String vsWR, String vsPR) {
		SurfMSM dPR;
		SurfMSM sTime;
		SurfMSM dWR;
		try {
			dPR = new SurfMSM(vsPR);
			sTime = new SurfMSM(time);
			dWR = new SurfMSM(vsWR);
		} catch(IllegalArgumentException e) {
			//Poorly formed log line?
			e.printStackTrace();
			return;
		}
		if(dPR.time<0) {
			//The new PR is faster than the old one -- negative time. record!
			currentMap.setNewPROverall(sTime);
		} else {
			//The map completion was not as fast as our PR, but maybe that fast PR hasn't been recorded, so 
			//subtract the PR offset from this most recent completion to find our fastest PR and record it
			currentMap.setNewPROverall(sTime.subtract(dPR));
		}
		//The world record for this map can be extrapolated by subtracting the WR offset from our current time
		currentMap.setNewWROverall(sTime.subtract(dWR));
	}

	public void registerStageCompletion(String stage, String time, String vsWR, String vsPR) {
		Integer sStage = Integer.parseInt(stage);
		SurfMSM dPR;
		SurfMSM sTime;
		SurfMSM dWR;
		try {
			dPR = new SurfMSM(vsPR);
			sTime = new SurfMSM(time);
			dWR = new SurfMSM(vsWR);
		} catch(IllegalArgumentException e) {
			//Poorly formed log line?
			e.printStackTrace();
			return;
		}
		if(dPR.time<0) {
			//new record!
			currentMap.setNewPR(sStage,sTime);
		} else {
			currentMap.setNewPR(sStage,sTime.subtract(dPR));
		}
		currentMap.setNewWR(sStage, sTime.subtract(dWR));
	}

	public SurfMap getCurrentMap() {
		return currentMap;
	}

	public String getCurrentUsername() {
		return currentUsername;
	}

	public void setCurrentUsername(String currentUsername) {
		this.currentUsername = currentUsername;
	}

	public void saveAllMaps() {
		for(SurfMap s : mapCache.values()) {
			s.writeToFile();
		}
	}

	/**
	 * Loads all the maps in the /sa-maps/ folder.
	 */
	public void loadMaps() {
		System.out.println("Loading Maps...");
		File f = new File("/sa-maps/");
		if(!f.exists()) {
			f.mkdir();
		}
		for(File sf : f.listFiles()) {
			SurfMap newMap = SurfMap.readFromFile(sf);
			if(newMap != null) {
				mapCache.put(newMap.name, newMap);
			}
		}
	}

	public static StatHandler instance() {
		if(INSTANCE == null) {
			INSTANCE = new StatHandler();
		}
		return INSTANCE;
	}

	private StatHandler() {
		//no instantiation please
	}
}
