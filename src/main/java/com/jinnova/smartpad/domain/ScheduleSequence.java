package com.jinnova.smartpad.domain;

import java.io.Serializable;

import com.jinnova.smartpad.partner.IScheduleSequence;

public class ScheduleSequence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -509885286715259668L;

	private int[] daysOfMonth;
	private int[] daysOfWeek;
	private int[] hours;
	private int[] minutes;
	private int[] months;
	private int[] years;

	public ScheduleSequence() {
	}
	
	public ScheduleSequence(int... daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	
	public ScheduleSequence(IScheduleSequence scheduleSequence) {
		this.daysOfMonth = scheduleSequence.getDaysOfMonth();
		this.daysOfWeek = scheduleSequence.getDaysOfWeek();
		this.hours = scheduleSequence.getHours();
		this.minutes = scheduleSequence.getMinutes();
		this.months = scheduleSequence.getMonths();
		this.years = scheduleSequence.getYears();
	}

	public int[] getDaysOfMonth() {
		return daysOfMonth;
	}

	public void setDaysOfMonth(int[] daysOfMonth) {
		this.daysOfMonth = daysOfMonth;
	}

	public int[] getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(int[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public int[] getHours() {
		return hours;
	}

	public void setHours(int[] hours) {
		this.hours = hours;
	}

	public int[] getMinutes() {
		return minutes;
	}

	public void setMinutes(int[] minutes) {
		this.minutes = minutes;
	}

	public int[] getMonths() {
		return months;
	}

	public void setMonths(int[] months) {
		this.months = months;
	}

	public int[] getYears() {
		return years;
	}

	public void setYears(int[] years) {
		this.years = years;
	}
	
	public int getHourFrom() {
		return minValue(hours);
	}
	
	public int getHourTo() {
		return maxValue(hours);
	}

	public void setHourFrom(int fromH) {
		this.hours = createRangeFrom(fromH, hours, "From hour must smaller to hour");
	}
	
	public void setHourTo(int toH) {
		this.hours = createRangeTo(toH, hours, "From hour must smaller to hour");
	}

	public int getMinFrom() {
		return minValue(minutes);
	}
	
	public int getMinTo() {
		return maxValue(minutes);
	}
	
	public void setMinFrom(int fromM) {
		this.minutes = createRangeFrom(fromM, minutes, "From minute must smaller to minute");
	}
	
	public void setMinTo(int toM) {
		this.minutes = createRangeTo(toM, minutes, "From minute must smaller to minute");
	}
	
	private static final int minValue(int[] array) {
		if (array == null || array.length == 0) {
			return 0;
		}
		return array[0];
	}
	
	private static final int maxValue(int[] array) {
		if (array == null || array.length == 0) {
			return 0;
		}
		return array[array.length - 1];
	}
	
	private static final int[] createRangeFrom(int from, int[] array, String minNotBelowMaxExMesg) {
		if (array == null || array.length == 0) {
			array = new int[1];
			array[0] = from;
		} else {
			int to = maxValue(array);
			int span = to - from;
			if (span == 0) {
				array = new int[1];
				array[0] = from;
				return array;
			}
			if (span < 0) {
				throw new RuntimeException(minNotBelowMaxExMesg);
			}
			array = new int[span + 1];
			for (int index = 0; index < span; index++, from++) {
				array[index] = from;
			}
		}
		return array;
	}
	
	private static final int[] createRangeTo(int to, int[] array, String maxNotLargerMinExMesg) {
		if (array == null || array.length == 0) {
			array = new int[1];
			array[0] = to;
		} else {
			int from = minValue(array);
			int span = to - from;
			if (span == 0) {
				array = new int[1];
				array[0] = from;
				return array;
			}
			if (span < 0) {
				throw new RuntimeException(maxNotLargerMinExMesg);
			}
			array = new int[span + 1];
			for (int index = 0; index < span; index++, from++) {
				array[index] = from;
			}
		}
		return array;
	}
	
	public void updateToDB(IScheduleSequence ssDB) {
		if (ssDB == null) {
			return;
		}
		ssDB.setDaysOfMonth(daysOfMonth);
		ssDB.setDaysOfWeek(daysOfWeek);
		ssDB.setHours(hours);
		ssDB.setMinutes(minutes);
		ssDB.setMonths(months);
		ssDB.setYears(years);
	}

	public void copyFromDB(IScheduleSequence scheduleSequence) {
		if (scheduleSequence == null) {
			return;
		}
		this.daysOfMonth = scheduleSequence.getDaysOfMonth();
		this.daysOfWeek = scheduleSequence.getDaysOfWeek();
		this.hours = scheduleSequence.getHours();
		this.minutes = scheduleSequence.getMinutes();
		this.months = scheduleSequence.getMonths();
		this.years = scheduleSequence.getYears();
	}

	public boolean checkSameDays(int[] daysOfWeek) {
		return checkSameValues(daysOfWeek, this.daysOfWeek);
	}
	
	private static final boolean checkSameValues(int[] array1, int[] array2) {
		if (array1 == null && array2 == null) {
			return true;
		}
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int index = 0; index < array1.length; index++) {
			if (array1[index] != array2[index]) {
				return false;
			}
		}
		return true;
	}
}