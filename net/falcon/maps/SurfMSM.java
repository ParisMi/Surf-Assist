package net.falcon.maps;

/**
 * Short for "Surf Minutes Seconds Miliseconds," this class 
 * is the time it took to complete a run (stage or overall).
 * Contains support for adding and subtracting time, as well as reading
 * a timestamp from a TF2 log.
 */
public class SurfMSM {
	
	/**
	 * The time, in number of miliseconds. This number is rarely very large (surf maps are not longer than 3min usually)
	 */
	protected final Integer time;
	
	public SurfMSM(String atime) throws IllegalArgumentException {
		try {
			String[] timePrse = atime.split(":");
			Integer minutes = 0;
			if(!timePrse[0].equals("N/A")) {
				minutes = Integer.parseInt(timePrse[0]);
			} else {
				time = -1;
				return;
			}
			Boolean allNeg = minutes<0;
			String[] prse = timePrse[1].split("\\.");
			Integer seconds = Integer.parseInt(prse[0]);
			Integer milis = Integer.parseInt(prse[1]);
			if(allNeg) {
				seconds *= -1;
				milis *= -1;
			}
			System.out.println("M:" + minutes + " S:" + seconds + " M:" + milis);
			time = (minutes * 100 * 60) + milis + (seconds * 100);
		} catch(Exception e) {
			System.out.println("Error encountered when trying to create a new SurfMSM/Surf Time:");
			e.printStackTrace();
			throw new IllegalArgumentException("Is the time string properly formatted?");
		}
		
	}
	public SurfMSM(Integer mins, Integer secs, Integer mils) {
		time = (mins * 100) + mils + (secs * 60 * 100);
	}
	
	public SurfMSM(Integer time) {
		this.time = time;
	}
	
	public SurfMSM add(SurfMSM s) {
		return new SurfMSM(time + s.time);
	}
	
	@Override
	public String toString() {
		Integer tempTime = time.intValue();
		Integer minutes = 0;
		Integer seconds = 0;
		while(tempTime >= (60 * 100)) {
			minutes++;
			tempTime -= 60 * 100;
		}
		while(tempTime >= 100) {
			seconds++;
			tempTime -= 100;
		}
		return (minutes<10?"0"+minutes:minutes) + ":" + (seconds<10?"0"+seconds:seconds) + "." + (tempTime<10?"0"+tempTime:tempTime);
	}
	
	public SurfMSM subtract(SurfMSM s) {
		return new SurfMSM(time - s.time);
	}
}
