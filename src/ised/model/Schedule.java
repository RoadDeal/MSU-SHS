/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

import java.sql.Time;

/**
 *
 * @author ABDUL
 */
public class Schedule {

    private int scheduleID;
    private String days;
    private Time startTime;
    private Time endTime;

    public Schedule(int scheduleID, String days, Time startTime, Time endTime) {
        this.scheduleID = scheduleID;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule(String days, Time startTime, Time endTime) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

}
