/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.model;

/**
 *
 * @author ABDUL
 */
public class Enrollment {

    private int enrollmentID;
    private Admission admission;
    private SchoolYear schoolYear;
    private Class section;
    private int isAdmitted;
    private double average;
    public int YES = 1;
    public int NO = 0;

    public Enrollment(int enrollmentID, Admission admission, SchoolYear schoolYear, int isAdmitted, Class section, double average) {
        this.enrollmentID = enrollmentID;
        this.admission = admission;
        this.schoolYear = schoolYear;
        this.section = section;
        this.isAdmitted = isAdmitted;
        this.average = average;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(int enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public int getIsAdmitted() {
        return isAdmitted;
    }

    public void setIsAdmitted(int isAdmitted) {
        this.isAdmitted = isAdmitted;
    }

    public Class getSection() {
        return section;
    }

    public void setSection(Class section) {
        this.section = section;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }


}
