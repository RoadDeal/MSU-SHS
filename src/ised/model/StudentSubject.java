/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class StudentSubject {

    private int studentSubjectID;
    private Enrollment enrollment;
    private Subject subject;
    private double finalGrade;

    public StudentSubject(int studentSubjectID, Enrollment enrollment, Subject subject, double finalGrade) {
        this.studentSubjectID = studentSubjectID;
        this.enrollment = enrollment;
        this.subject = subject;
        this.finalGrade = finalGrade;
    }
    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public int getStudentSubjectID() {
        return studentSubjectID;
    }

    public void setStudentSubjectID(int studentSubjectID) {
        this.studentSubjectID = studentSubjectID;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    

}
