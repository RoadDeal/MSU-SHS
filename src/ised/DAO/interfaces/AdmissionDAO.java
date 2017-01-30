/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import ised.model.Admission;
import ised.model.Enrollment;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface AdmissionDAO {

    Admission getAdmission(int studentID, int schoolYearID) throws ExceptionHandler;

    List<Student> getAdmittedStudentsList(int schoolYearID) throws ExceptionHandler;

    List<Student> getAdmittedStudentsList(int schoolYearID, String search, int yearLevel) throws ExceptionHandler;

    List<Student> getAdmittedStudentsList(int schoolYearID, int yearLevel) throws ExceptionHandler;

    void admitStudent(int schoolYearID, int studentID, int yearLevelAdmitted, int enrollmentID) throws ExceptionHandler;

    void cancelAdmit(int schoolYearID, int studentID, int enrollmentID) throws ExceptionHandler;

    boolean isStudentEnrolled(int schoolYearID, int studentID) throws ExceptionHandler;

    boolean isStudentAdmitted(int schoolYearID, int studentID) throws ExceptionHandler;

    boolean hasCompleteGrade(int enrollmentID) throws ExceptionHandler;

    boolean hasFailingGrade(int enrollmentID) throws ExceptionHandler;

    boolean checkSummerGrades(int enrollmentID) throws ExceptionHandler;
}
