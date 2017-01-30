/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.model;

import java.util.Date;

/**
 *
 * @author ABDUL
 */
public class SchoolYear {

    private int schoolYearID;
    private int yearFrom;
    private int yearTo;
    private Date enrollmentBegin;
    private Date enrollmentEnd;
    private Date startOfClasses;
    private String schoolYearStatus;

    public SchoolYear(){

    }

    public SchoolYear(int schoolYearID, int yearFrom, int yearTo, Date enrollmentBegin,
            Date enrollmentEnd,Date startOfClasses, String schoolYearStatus) {
        this.schoolYearID = schoolYearID;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.enrollmentBegin = enrollmentBegin;
        this.enrollmentEnd = enrollmentEnd;
        this.startOfClasses = startOfClasses;
        this.schoolYearStatus = schoolYearStatus;
    }

    @Override
    public String toString() {
        return this.yearFrom + " - " + this.yearTo;
    }
    public Date getEnrollmentBegin() {
        return enrollmentBegin;
    }

    public void setEnrollmentBegin(Date enrollmentBegin) {
        this.enrollmentBegin = enrollmentBegin;
    }

    public Date getEnrollmentEnd() {
        return enrollmentEnd;
    }

    public void setEnrollmentEnd(Date enrollmentEnd) {
        this.enrollmentEnd = enrollmentEnd;
    }
    
    public int getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(int schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    public String getSchoolYearStatus() {
        return schoolYearStatus;
    }

    public void setSchoolYearStatus(String schoolYearStatus) {
        this.schoolYearStatus = schoolYearStatus;
    }

    public Date getStartOfClasses() {
        return startOfClasses;
    }

    public void setStartOfClasses(Date startOfClasses) {
        this.startOfClasses = startOfClasses;
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int yearFrom) {
        this.yearFrom = yearFrom;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }
}
