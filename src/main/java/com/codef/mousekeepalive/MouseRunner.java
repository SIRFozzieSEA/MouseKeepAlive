package com.codef.mousekeepalive;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseRunner implements Runnable {

	private long sleepCount = 1;
	private int refreshInterval = 0;
	private long maxHoursRunning = 3600 * 9L;
	
	private static final Logger LOGGER = Logger.getLogger(MouseRunner.class.getName());

	public MouseRunner(int refreshInterval, int maxHoursRunning) {
		this.refreshInterval = refreshInterval;
		this.maxHoursRunning = 3600L * maxHoursRunning;
	}

	@Override
	public void run() {
		try {
			Robot robot = new Robot();
			while (true) {
				PointerInfo pointerInfo = MouseInfo.getPointerInfo();
				Point myPoint = pointerInfo.getLocation();
				int x = (int) myPoint.getX() + ThreadLocalRandom.current().nextInt(-3, 3);
				int y = (int) myPoint.getY() + ThreadLocalRandom.current().nextInt(-3, 3);
				robot.mouseMove(x, y);

				LOGGER.log(Level.INFO, () -> "Mouse move (" + sleepCount + ")");
				Thread.sleep(1000L * refreshInterval);

				sleepCount++;
				if (sleepCount > maxHoursRunning) {
					break;
				}
			}

		} catch (Exception e) {
			LOGGER.info("MouseRunner interrupted");
		}

	}

}
