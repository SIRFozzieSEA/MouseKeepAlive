package com.codef.mousekeepalive;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MouseKeepAlive {

	private static boolean isMouseRunnerInited = false;
	private static Thread mouseThread = null;
	private static int refreshIntervalInSeconds = 0;
	private static int maxRunningTimeInHours = 0;

	private static final Logger LOGGER = Logger.getLogger(MouseKeepAlive.class.getName());
	
	public static void main(String[] args) {
		
		LOGGER.info("Started");

		Properties defaultProps = new Properties();
		try {

			InputStream in = MouseKeepAlive.class.getClassLoader().getResourceAsStream("MouseKeepAlive.properties");
			defaultProps.load(in);
			in.close();

			refreshIntervalInSeconds = Integer.parseInt(defaultProps.get("refresh_interval_in_seconds").toString());
			maxRunningTimeInHours = Integer.parseInt(defaultProps.get("max_running_time_in_hours").toString());

			startMouseRunner(true);

		} catch (Exception e) {

			LOGGER.error(e.toString(), e);

		}

	}

	public static void initMouseRunner() {
		isMouseRunnerInited = true;
		mouseThread = new Thread(new MouseRunner(refreshIntervalInSeconds, maxRunningTimeInHours));
	}

	public static void startMouseRunner(boolean runMouseRunner) {
		if (runMouseRunner) {
			if (!isMouseRunnerInited) {
				initMouseRunner();
			}
			mouseThread.start();
		} else {
			mouseThread.interrupt();
			isMouseRunnerInited = false;
		}
	}

}