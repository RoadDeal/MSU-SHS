/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.model;

/**
 *
 * @author ABDUL
 */
public class SummerStudent {

    int summerStudentID;
    StudentSubject studentSubject;
    double completeGrade;

    public SummerStudent(int summerStudentID, StudentSubject studentSubject, double completeGrade) {
        this.summerStudentID = summerStudentID;
        this.studentSubject = studentSubject;
        this.completeGrade = completeGrade;
    }

    @Override
    public String toString(){
        return studentSubject.getEnrollment().getAdmission().getStudent().getFullName();
    }
    public double getCompleteGrade() {
        return completeGrade;
    }

    public void setCompleteGrade(double completeGrade) {
        this.completeGrade = completeGrade;
    }

    public StudentSubject getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(StudentSubject studentSubject) {
        this.studentSubject = studentSubject;
    }

    public int getSummerStudentID() {
        return summerStudentID;
    }

    public void setSummerStudentID(int summerStudentID) {
        this.summerStudentID = summerStudentID;
    }


}
