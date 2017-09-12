package com.stta.utility;

import org.apache.log4j.Logger;

public class Log {

	private static final String DEBUG = "debug";
	private static final String INFO = "info";
	private static final String WARN = "warn";
	private static final String ERROR = "error";
	private static final String TRACE = "trace";
	
	public enum Level {
		DEBUG, INFO, WARN, ERROR, TRACE
	}
	
	public static void Report(Log.Level level, String Description) {
		final Logger report = Logger.getLogger(Log.class.getName());
		
		if(level.toString().equalsIgnoreCase(DEBUG)) {
			report.debug(Description);
		} else if(level.toString().equalsIgnoreCase(INFO)){
			report.info(Description);
		} else if(level.toString().equalsIgnoreCase(WARN)) {
			report.warn(Description);
		} else if(level.toString().equalsIgnoreCase(ERROR)) {
			report.error(Description);
		} else if (level.toString().equalsIgnoreCase(TRACE)) {
			report.trace(Description);
		}
	}
	
}
