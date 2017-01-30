/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.model;

/**
 *
 * @author ABDUL
 */
public class Admission {

    private int admissionID;
    private Student student;
    private int yearLevelAdmitted;
    private SchoolYear schoolYear;
    private int isEnrolled;
    public int YES = 1;
    public int NO = 0;

    public Admission(int admissionID, Student student, int yearLevelAdmitted, SchoolYear schoolYear, int isEnrolled) {
        this.admissionID = admissionID;
        this.student = student;
        this.yearLevelAdmitted = yearLevelAdmitted;
        this.schoolYear = schoolYear;
        this.isEnrolled = isEnrolled;
    }

    public int getAdmissionID() {
        return admissionID;
    }

    public void setAdmissionID(int admissionID) {
        this.admissionID = admissionID;
    }

    public int getYearLevelAdmitted() {
        return yearLevelAdmitted;
    }

    public void setYearLevelAdmitted(int yearLevelAdmitted) {
        this.yearLevelAdmitted = yearLevelAdmitted;
    }

    public int getIsEnrolled() {
        return isEnrolled;
    }

    public void setIsEnrolled(int isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
