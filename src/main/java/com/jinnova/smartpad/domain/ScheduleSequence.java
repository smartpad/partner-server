package com.jinnova.smartpad.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

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
	
	@JsonIgnore
	public int getHourFrom() {
		return minValue(hours);
	}
	
	@JsonIgnore
	public int getHourTo() {
		return maxValue(hours);
	}

	@JsonIgnore
	public void updateHourFrom(int fromH) throws InvalidParamerFromUserException {
		if (fromH < 1 || fromH > 24) {
			// TODO throw new InvalidParamerFromUserException("Hour from 1 to 24");
			return;
		}
		this.hours = createRangeFrom(fromH, hours, "From hour must smaller to hour");
	}
	
	@JsonIgnore
	public void updateHourTo(int toH) throws InvalidParamerFromUserException {
		if (toH < 1 || toH > 24) {
			// TODO throw new InvalidParamerFromUserException("Hour from 1 to 24");
			return;
		}
		this.hours = createRangeTo(toH, hours, "From hour must smaller to hour");
	}

	@JsonIgnore
	public int getMinFrom() {
		return minValue(minutes);
	}
	
	@JsonIgnore
	public int getMinTo() {
		return maxValue(minutes);
	}
	
	@JsonIgnore
	public void updateMinFrom(int fromM) throws InvalidParamerFromUserException {
		if (fromM < 1 || fromM > 60) {
			// TODO validate throw new InvalidParamerFromUserException("Minutes from 1 to 60");
			return;
		}
		this.minutes = createRangeFrom(fromM, minutes, "From minute must smaller to minute");
	}
	
	@JsonIgnore
	public void updateMinTo(int toM) throws InvalidParamerFromUserException  {
		if (toM < 1 || toM > 60) {
			// TODO throw new InvalidParamerFromUserException("Minutes from 1 to 60");
			return;
		}
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
	
	private static final int[] createRangeFrom(int from, int[] array, String minNotBelowMaxExMesg) throws InvalidParamerFromUserException {
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
				throw new InvalidParamerFromUserException(minNotBelowMaxExMesg);
			}
			array = new int[span + 1];
			for (int index = 0; index < span; index++, from++) {
				array[index] = from;
			}
		}
		return array;
	}
	
	private static final int[] createRangeTo(int to, int[] array, String maxNotLargerMinExMesg) throws InvalidParamerFromUserException {
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
				throw new InvalidParamerFromUserException(maxNotLargerMinExMesg);
			}
			array = new int[span + 1];
			for (int index = 0; index < array.length; index++, from++) {
				array[index] = from;
			}
		}
		return array;
	}
	
	public void updateToDB(IScheduleSequence ssDB) {
		if (ssDB == null) {
			return;
		}
		if (daysOfMonth != null) {
			ssDB.setDaysOfMonth(daysOfMonth);
		}
		if (daysOfWeek != null) {
			ssDB.setDaysOfWeek(daysOfWeek);
		}
		if (hours != null) {
			ssDB.setHours(hours);
		}
		if (minutes != null) {
			ssDB.setMinutes(minutes);
		}
		if (months != null) {
			ssDB.setMonths(months);
		}
		if (years != null) {
			ssDB.setYears(years);
		}
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