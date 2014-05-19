package com.jinnova.smartpad.domain;

import java.io.Serializable;
import java.util.Calendar;

import com.jinnova.smartpad.partner.ISchedule;
import com.jinnova.smartpad.partner.IScheduleSequence;

public class Schedule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2955153867508728592L;

	private String desc;
	//private ScheduleSequence[] otherSequences;
	
	private final ScheduleSequence daily = new ScheduleSequence(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY);
	private final ScheduleSequence sar = new ScheduleSequence(Calendar.SATURDAY);
	private final ScheduleSequence sun = new ScheduleSequence(Calendar.SUNDAY);
	private final ScheduleSequence holiday = new ScheduleSequence(-1);
	
	public Schedule() {
	}

	public Schedule(ISchedule schedule) {
		this.desc = schedule.getDesc();
		if (schedule.getScheduleSequences() != null) {
			//IScheduleSequence[] sequences = new ScheduleSequence[schedule.getScheduleSequences().length];
			//int index = 0;
			for (IScheduleSequence ss : schedule.getScheduleSequences()) {
				//this.otherSequences[index++] = new ScheduleSequence(ss);
				ScheduleSequence ssToUpdate = null;
				if (daily.checkSameDays(ss.getDaysOfWeek())) {
					ssToUpdate = daily;
				} else if (sar.checkSameDays(ss.getDaysOfWeek())) {
					ssToUpdate = sar;
				} else if (sun.checkSameDays(ss.getDaysOfWeek())) {
					ssToUpdate = sun;
				} else if (holiday.checkSameDays(ss.getDaysOfWeek())) { // TODO check as more fields
					ssToUpdate = holiday;
				}
				if (ssToUpdate == null) {
					// TODO handle error
				} else {
					ssToUpdate.copyFromDB(ss);
				}
			}
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ScheduleSequence getDaily() {
		return daily;
	}

	public void setDaily(ScheduleSequence daily) {
		this.daily.setHours(daily.getHours());
		this.daily.setMinutes(daily.getMinutes());
	}

	public ScheduleSequence getSar() {
		return sar;
	}

	public void setSar(ScheduleSequence sar) {
		this.sar.setHours(sar.getHours());
		this.sar.setMinutes(sar.getMinutes());
	}

	public ScheduleSequence getSun() {
		return sun;
	}

	public void setSun(ScheduleSequence sun) {
		this.sun.setHours(sun.getHours());
		this.sun.setMinutes(sun.getMinutes());
	}

	public ScheduleSequence getHoliday() {
		return holiday;
	}

	public void setHoliday(ScheduleSequence holiday) {
		this.holiday.setHours(holiday.getHours());
		this.holiday.setMinutes(holiday.getMinutes());
	}

	/*public ScheduleSequence[] getOtherSequences() {
		return otherSequences;
	}

	public void setOtherSequences(ScheduleSequence[] otherSequences) {
		this.otherSequences = otherSequences;
	}*/

	public RangeValue getDailyHour() {
		return new RangeValue(daily.getHourFrom(), daily.getHourTo());
	}

	public void setDailyHour(RangeValue rv) throws InvalidParamerFromUserException {
		this.daily.updateHourFrom(rv.getFromValue());
		this.daily.updateHourTo(rv.getToValue());
	}

	public RangeValue getSarHour() {
		return new RangeValue(sar.getHourFrom(), sar.getHourTo());
	}

	public void setSarHour(RangeValue rv) throws InvalidParamerFromUserException {
		this.sar.updateHourFrom(rv.getFromValue());
		this.sar.updateHourTo(rv.getToValue());
	}

	public RangeValue getSunHour() {
		return new RangeValue(sun.getHourFrom(), sun.getHourTo());
	}

	public void setSunHour(RangeValue rv) throws InvalidParamerFromUserException {
		this.sun.updateHourFrom(rv.getFromValue());
		this.sun.updateHourTo(rv.getToValue());
	}

	public RangeValue getHolidayHour() {
		return new RangeValue(holiday.getHourFrom(), holiday.getHourTo());
	}

	public void setHolidayHour(RangeValue rv) throws InvalidParamerFromUserException {
		this.holiday.updateHourFrom(rv.getFromValue());
		this.holiday.updateHourTo(rv.getToValue());
	}

	public RangeValue getDailyMin() {
		return new RangeValue(daily.getMinFrom(), daily.getMinTo());
	}

	public void setDailyMin(RangeValue rv) throws InvalidParamerFromUserException {
		this.daily.updateMinFrom(rv.getFromValue());
		this.daily.updateMinTo(rv.getToValue());
	}

	public RangeValue getSarMin() {
		return new RangeValue(sar.getMinFrom(), sar.getMinTo());
	}

	public void setSarMin(RangeValue rv) throws InvalidParamerFromUserException {
		this.sar.updateMinFrom(rv.getFromValue());
		this.sar.updateMinTo(rv.getToValue());
	}

	public RangeValue getSunMin() {
		return new RangeValue(sun.getMinFrom(), sun.getMinTo());
	}

	public void setSunMin(RangeValue rv) throws InvalidParamerFromUserException {
		this.sun.updateMinFrom(rv.getFromValue());
		this.sun.updateMinTo(rv.getToValue());
	}

	public RangeValue getHolidayMin() {
		return new RangeValue(holiday.getMinFrom(), holiday.getMinTo());
	}

	public void setHolidayMin(RangeValue rv) throws InvalidParamerFromUserException {
		this.holiday.updateMinFrom(rv.getFromValue());
		this.holiday.updateMinTo(rv.getToValue());
	}

	/*public int getDayHourFrom() {
		return this.daily.getHourFrom();
	}
	
	public void setDayHourFrom(int h) throws InvalidParamerFromUserException {
		this.daily.setHourFrom(h);
	}

	public int getDayHourTo() {
		return this.daily.getHourTo();
	}
	
	public void setDayHourTo(int h) throws InvalidParamerFromUserException {
		this.daily.setHourTo(h);
	}

	public int getDayMinFrom() {
		return this.daily.getMinFrom();
	}
	
	public void setDayMinFrom(int h) throws InvalidParamerFromUserException {
		this.daily.setMinFrom(h);
	}

	public int getDayMinTo() {
		return this.daily.getMinTo();
	}
	
	public void setDayMinTo(int h) throws InvalidParamerFromUserException {
		this.daily.setMinTo(h);
	}
	
	public int getSarHourFrom() {
		return sar.getHourFrom();
	}
	
	public void setSarHourFrom(int h) throws InvalidParamerFromUserException {
		this.sar.setHourFrom(h);
	}

	public int getSarMinFrom() {
		return this.sar.getMinFrom();
	}
	
	public void setSarMinFrom(int h) throws InvalidParamerFromUserException {
		this.sar.setMinFrom(h);
	}
	
	public int getSarHourTo() {
		return sar.getHourTo();
	}
	
	public void setSarHourTo(int h) throws InvalidParamerFromUserException {
		this.sar.setHourTo(h);
	}

	public int getSarMinTo() {
		return this.sar.getMinTo();
	}
	
	public void setSarMinTo(int h) throws InvalidParamerFromUserException {
		this.sar.setMinTo(h);
	}
	
	public int getSunHourFrom() {
		return this.sun.getHourFrom();
	}
	
	public void setSunHourFrom(int h) throws InvalidParamerFromUserException {
		this.sun.setHourFrom(h);
	}

	public int getSunHourTo() {
		return this.sun.getHourTo();
	}
	
	public void setSunHourTo(int h) throws InvalidParamerFromUserException {
		this.sun.setHourTo(h);
	}

	public int getSunMinFrom() {
		return this.sun.getMinFrom();
	}
	
	public void setSunMinFrom(int h) throws InvalidParamerFromUserException {
		this.sun.setMinFrom(h);
	}
	
	public int getSunMinTo() {
		return this.sun.getMinTo();
	}
	
	public void setSunMinTo(int h) throws InvalidParamerFromUserException {
		this.sun.setMinTo(h);
	}
	
	public int getHolidayHourFrom() {
		return this.holiday.getHourFrom();
	}
	
	public void setHolidayHourFrom(int h) throws InvalidParamerFromUserException {
		this.holiday.setHourFrom(h);
	}

	public int getHolidayMinFrom() {
		return this.holiday.getMinFrom();
	}
	
	public void setHolidayMinFrom(int h) throws InvalidParamerFromUserException {
		this.holiday.setMinFrom(h);
	}
	
	public int getHolidayHourTo() {
		return this.holiday.getHourTo();
	}
	
	public void setHolidayHourTo(int h) throws InvalidParamerFromUserException {
		this.holiday.setHourTo(h);
	}

	public int getHolidayMinTo() {
		return this.holiday.getMinTo();
	}
	
	public void setHolidayMinTo(int h) throws InvalidParamerFromUserException {
		this.holiday.setMinTo(h);
	}*/
	
	public void updateToDB(ISchedule openHours) {
		if (openHours == null) {
			return;
		}
		openHours.setDesc(desc);
		IScheduleSequence[] scheduleSequencesDB = new IScheduleSequence[4];
		IScheduleSequence ssDaily = openHours.newScheduleSequenceInstance();
		daily.updateToDB(ssDaily);
		scheduleSequencesDB[0] = ssDaily;
		
		IScheduleSequence ssSar = openHours.newScheduleSequenceInstance();
		sar.updateToDB(ssSar);
		scheduleSequencesDB[1] = ssSar;
		
		IScheduleSequence ssSun = openHours.newScheduleSequenceInstance();
		sun.updateToDB(ssSun);
		scheduleSequencesDB[2] = ssSun;
		
		IScheduleSequence ho = openHours.newScheduleSequenceInstance();
		holiday.updateToDB(ho);
		scheduleSequencesDB[3] = ho;

		openHours.setScheduleSequences(scheduleSequencesDB);
	}

}