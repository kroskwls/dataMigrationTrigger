package com.migration.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeUtil {
	public String removeTimezone(String date) {
		return date.split("\\+")[0];
	}

	public String millisToDate(long millis) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(millis));
	}
}
