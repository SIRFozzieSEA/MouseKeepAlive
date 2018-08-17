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
	
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
	}

	public static void main(String[] args) {

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