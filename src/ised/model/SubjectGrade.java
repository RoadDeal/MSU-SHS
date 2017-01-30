/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

import java.util.Calendar;
/**
 *
 * @author ABDUL
 */
public class SubjectGrade {

    private int subjectGradeID;
    private StudentSubject studentSubject;
    private int grading;
    private double gradingGrade;
    private Calendar dateSubmitted;

    public SubjectGrade(int subjectGradeID, StudentSubject studentSubject, int grading, double gradingGrade, Calendar dateSubmitted) {
        this.subjectGradeID = subjectGradeID;
        this.studentSubject = studentSubject;
        this.grading = grading;
        this.gradingGrade = gradingGrade;
        this.dateSubmitted = dateSubmitted;
    }

    public int getGrading() {
        return grading;
    }

    public void setGrading(int grading) {
        this.grading = grading;
    }

    public double getGradingGrade() {
        return gradingGrade;
    }

    public void setGradingGrade(double gradingGrade) {
        this.gradingGrade = gradingGrade;
    }

    public Calendar getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Calendar dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public StudentSubject getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(StudentSubject studentSubject) {
        this.studentSubject = studentSubject;
    }

    public int getSubjectGradeID() {
        return subjectGradeID;
    }

    public void setSubjectGradeID(int subjectGradeID) {
        this.subjectGradeID = subjectGradeID;
    }

    
}
