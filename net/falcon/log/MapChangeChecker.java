package net.falcon.log;

import java.util.List;

import net.falcon.maps.StatHandler;

/**
 * @see #LineChecker
 */
public class MapChangeChecker extends LineChecker {

	public MapChangeChecker() {
		super("Map: surf_.*", "Map: surf_");
	}
	
	
	@Override
	protected void handleMatch(List<String> results) {
		System.out.println("Map Change: " + results.get(1));
		StatHandler.instance().registerMapChange(results.get(1));
	}

}
