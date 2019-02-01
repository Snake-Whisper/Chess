package chessGame;

import java.time.Duration;

public class Timer {

	private Duration time;
	private long start;
	
	Timer() {
		 time = Duration.ofMinutes(20);
	}
	
	Timer(int minutes) {
		time = Duration.ofMinutes(minutes);
	}
	
	public void start() {
		start = System.currentTimeMillis();
	}
	
	public void stop() {
		time = time.minusMillis(System.currentTimeMillis() - start);
	}
	
	public long getSecondsLeft() {
		return time.getSeconds();
	}
}
