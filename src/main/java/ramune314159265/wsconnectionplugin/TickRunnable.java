package ramune314159265.wsconnectionplugin;

import java.util.Date;

public class TickRunnable implements Runnable {
	public static float tps = 20;
	public static long lastTickTimestamp = new Date().getTime();
	public long lastTickTime = System.currentTimeMillis();

	public void run() {
		long lastTickElapsed = System.currentTimeMillis() - lastTickTime;
		TickRunnable.tps = 1000f / lastTickElapsed;
		lastTickTime = System.currentTimeMillis();
		TickRunnable.lastTickTimestamp = new Date().getTime();
	}
}