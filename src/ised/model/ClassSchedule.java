/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class ClassSchedule {

    private int classScheduleID;
    private Class section;
    private Employee teacher;
    private Schedule schedule;
    private Subject subject;
    private String nonSubject;

    public ClassSchedule(int classScheduleID, Class section, Employee teacher, Schedule schedule, Subject subject, String nonSubject) {
        this.classScheduleID = classScheduleID;
        this.section = section;
        this.teacher = teacher;
        this.schedule = schedule;
        this.subject = subject;
        this.nonSubject = nonSubject;
    }

    public ClassSchedule(Class section, Schedule schedule, Subject subject) {
        this.section = section;
        this.schedule = schedule;
        this.subject = subject;
        this.nonSubject = null;
    }

    public ClassSchedule(Class section, Schedule schedule, String nonSubject) {
        this.section = section;
        this.schedule = schedule;
        this.subject = null;
        this.nonSubject = nonSubject;
    }

    @Override
    public String toString(){
        return this.subject.getSubjectCode() + "(" + this.section.getSection().getSectionName() + ")";
    }

    public int getClassScheduleID() {
        return classScheduleID;
    }

    public void setClassScheduleID(int classScheduleID) {
        this.classScheduleID = classScheduleID;
    }

    public String getNonSubject() {
        return nonSubject;
    }

    public void setNonSubject(String nonSubject) {
        this.nonSubject = nonSubject;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Class getSection() {
        return section;
    }

    public void setSection(Class section) {
        this.section = section;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Employee getTeacher() {
        return teacher;
    }

    public void setTeacher(Employee teacher) {
        this.teacher = teacher;
    }

    
}
