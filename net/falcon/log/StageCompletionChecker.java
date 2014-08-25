package net.falcon.log;

import java.util.List;

import net.falcon.maps.StatHandler;

public class StageCompletionChecker extends LineChecker {

	public StageCompletionChecker() {
		super("Completed Stage . [0-9][0-9]:[0-5][0-9]\\.[0-9][0-9] "
				+ "[\\+-][0-9][0-9]:[0-5][0-9]\\.[0-9][0-9] "
				+ "PR ([\\+-][0-9][0-9]:[0-5][0-9]\\.[0-9][0-9]|N/A)"
				,"Completed Stage|\\s|PR");
	}

	
	@Override
	protected void handleMatch(List<String> results) {
		String stage = results.get(2);
		String time = results.get(3);
		String vsWR = results.get(4);
		String vsPR = results.get(7);
		System.out.println("Stage Beat: " + "Stage " + stage + ", beaten in " + time + ", WR " + vsWR + ", PR " + vsPR + ".");
		StatHandler.instance().registerStageCompletion(stage, time, vsWR, vsPR);
	}
	
}
