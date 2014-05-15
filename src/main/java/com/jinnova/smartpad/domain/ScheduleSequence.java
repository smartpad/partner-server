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
	
}