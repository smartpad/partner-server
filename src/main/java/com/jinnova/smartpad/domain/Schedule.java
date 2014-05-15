package com.jinnova.smartpad.domain;

import java.io.Serializable;

import com.jinnova.smartpad.partner.ISchedule;
import com.jinnova.smartpad.partner.IScheduleSequence;

public class Schedule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2955153867508728592L;

	private String desc;
	private ScheduleSequence[] scheduleSequences;
	
	public Schedule() {
	}
	
	public Schedule(ISchedule schedule) {
		this.desc = schedule.getDesc();
		if (schedule.getScheduleSequences() != null) {
			this.scheduleSequences = new ScheduleSequence[schedule.getScheduleSequences().length];
			int index = 0;
			for (IScheduleSequence ss : schedule.getScheduleSequences()) {
				this.scheduleSequences[index++] = new ScheduleSequence(ss);
			}
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ScheduleSequence[] getScheduleSequences() {
		return scheduleSequences;
	}

	public void setScheduleSequences(ScheduleSequence[] scheduleSequences) {
		this.scheduleSequences = scheduleSequences;
	}

	public void updateToDB(ISchedule openHours) {
		if (openHours == null) {
			return;
		}
		openHours.setDesc(desc);
		if (this.scheduleSequences != null) {
			IScheduleSequence[] scheduleSequencesDB = new IScheduleSequence[scheduleSequences.length];
			int index = 1;
			for (ScheduleSequence ss : this.scheduleSequences) {
				IScheduleSequence ssDB = openHours.newScheduleSequenceInstance();
				ss.updateToDB(ssDB);
				scheduleSequencesDB[index++] = ssDB;
			}
			openHours.setScheduleSequences(scheduleSequencesDB);
		}
	}

}