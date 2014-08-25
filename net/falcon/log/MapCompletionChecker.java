package net.falcon.log;

import java.util.List;

import net.falcon.maps.StatHandler;

/**
 * @see #LineChecker
 */
public class MapCompletionChecker extends LineChecker {

	public MapCompletionChecker() {
		
		super("Map Completion by " + StatHandler.instance().getCurrentUsername() + " [0-9][0-9]:[0-5][0-9]\\.[0-9][0-9] [\\+-][0-9][0-9]:[0-5][0-9]\\.[0-9][0-9] PR ([\\+-][0-9][0-9]:[0-5][0-9]\\.[0-9][0-9]|N/A)"
				, "Map Completion by " + StatHandler.instance().getCurrentUsername() + "|\\s|PR");
	}
	
	
	@Override
	protected void handleMatch(List<String> results) {
		String time = results.get(2);
		String vsWR = results.get(3);
		String vsPR = results.get(6);
		System.out.println("Map Completion: In " + time + ", WR " + vsWR + ", PR " + vsPR);
		StatHandler.instance().registerMapCompletion(time, vsWR, vsPR);
	}

}
