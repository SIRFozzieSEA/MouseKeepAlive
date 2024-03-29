package com.codef.mousekeepalive;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MouseRunner implements Runnable {

	private long sleepCount = 1;
	private int refreshInterval = 0;
	private long maxHoursRunning = 3600 * 9L;
	
	private static final Logger LOGGER = LogManager.getLogger(MouseRunner.class.getName());

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

				LOGGER.info("Mouse move ({})", sleepCount);
				Thread.sleep(1000L * refreshInterval);

				sleepCount++;
				if (sleepCount > maxHoursRunning) {
					break;
				}
			}

	    } catch (InterruptedException e) {
	        LOGGER.info("MouseRunner interrupted");
	        Thread.currentThread().interrupt(); // Rethrow InterruptedException
	    } catch (Exception e) {
	        LOGGER.error("An error occurred", e);
	    }

	}

}
