package com.rates.demo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateUtil {

	public static String getNthLastDateAsString(int months) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -months);
		return format.format(c.getTime());
	}

	public static Date getNthLastDate(int months) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -months);
		return c.getTime();
	}

	public static Date getCurrentDate() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	public static String getCurrentDateAsString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		return format.format(c.getTime());
	}

	public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}
}
