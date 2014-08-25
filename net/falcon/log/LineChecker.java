package net.falcon.log;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Each LineChecker is basically a regex string looking for a certain log output. If the regex returns a match,
 * the matched string (list) is handled by {@link #handleMatch(List)}. 
 */
public class LineChecker {

	//TODO figure out how to get rid of nasty regex nightmares
	
	private final Pattern matchRegex;
	private final String splitRegex;
	
	
	/**
	 * @param matchRegex The regex that, if matched, will fire {@link #handleMatch(List)}.
	 * @param splitRegex The regex that, if matched, will return all unmatched character groups as a list to {@link #handleMatch(List)}.
	 */
	public LineChecker(String matchRegex, String splitRegex) {
		this.matchRegex = Pattern.compile(matchRegex);
		this.splitRegex = splitRegex;
	}
	
	public final void checkLine(Integer lineNumber, String line) {
		if(matchRegex.matcher(line).find()) {
			handleMatch(Arrays.asList(line.split(splitRegex)));
		}
	}
	
	/**
	 * Act based on the results of the matched log line.
	 * @param results the result of the split regex (usually contains important data like surf time).
	 */
	protected void handleMatch(List<String> results) {
		
	}
}
