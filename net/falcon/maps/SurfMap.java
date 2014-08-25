package net.falcon.maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.google.gson.Gson;

public class SurfMap {

	private static Gson gson = new Gson();
	public String name = "";
	public String comment = "";
	private final HashMap<Integer, SurfTime> stageTimes = new HashMap<Integer, SurfTime>();
	private final HashMap<Integer, SurfTime> bonusTimes = new HashMap<Integer, SurfTime>();
	public SurfTime overallTime = new SurfTime();

	public SurfMap(String name) {
		this.name = name;
	}

	public Set<Integer> getStages() {
		return stageTimes.keySet();
	}
	
	public void setNewPR(Integer stage, SurfMSM time) {
		getStage(stage).pr = time;
	}

	public void setNewWR(Integer stage, SurfMSM time) {
		getStage(stage).wr = time;
	}

	public void setNewPROverall(SurfMSM time) {
		overallTime.pr = time;
	}

	public void setNewWROverall(SurfMSM time) {
		overallTime.wr = time;
	}

	public SurfTime getStage(Integer stage) {
		SurfTime time = stageTimes.get(stage);
		if(time == null) {
			time = new SurfTime();
			stageTimes.put(stage, time);
		}
		return time;
	}

	public SurfTime getBonus(Integer bonus) {
		SurfTime time = bonusTimes.get(bonus);
		if(time == null) {
			time = new SurfTime();
			stageTimes.put(bonus, time);
		}
		return time;
	}

	public void writeToFile() {
		try {
			File file = new File("/sa-maps/" + name + ".sarec");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(gson.toJson(this));
			bw.close();
		} catch(IOException e) {
			System.out.println("Error writing " + name + ".sarec to file.");
			e.printStackTrace();
		}
	}

	/**
	 * Factory method for generating SurfMaps from a corresponding file.
	 * @return the resurrected SurfMap, or null if there was an error.
	 */
	public static SurfMap readFromFile(File f) {
		try {
			if(f.getName().contains(".sarec")) {
				BufferedReader br = new BufferedReader(new FileReader(f));
				SurfMap s = gson.fromJson(br.readLine(), SurfMap.class);
				br.close();
				return s;
			}
		} catch(IOException e) {
			System.out.println("Error reading " + f.getName());
			e.printStackTrace();
		}
		return null;
	}
}
