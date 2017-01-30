/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import ised.model.Enrollment;
import ised.model.Student;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface EnrollmentDAO {

    List<Student> getEnrolledStudentsList(int schoolYearID) throws ExceptionHandler;

    List<Student> getEnrolledStudentsList(int schoolYearID, String search, int yearLevel) throws ExceptionHandler;

    List<Student> getEnrolledStudentsList(int schoolYearID, int yearLevel) throws ExceptionHandler;

    List<Student> getNotAdmittedStudents(int schoolYearID) throws ExceptionHandler;

    List<Student> getNotAdmittedStudents(int schoolYearID, String search, int yearLevel) throws ExceptionHandler;

    List<Student> getNotAdmittedStudents(int schoolYearID, int yearLevel) throws ExceptionHandler;

    List<Student> getStudentsNotAssignedToSection(int schoolYearID, int yearLevel) throws ExceptionHandler;

    List<Student> getStudentsListBySection(int schoolYearID, int classID) throws ExceptionHandler;

    Enrollment getEnrollment(int studentID, int schoolYearID) throws ExceptionHandler;

    Enrollment getEnrollment(int enrollmentID) throws ExceptionHandler;

    void enrollStudent(int schoolYearID, int admissionID) throws ExceptionHandler;

    void cancelEnroll(int enrollmentID) throws ExceptionHandler;

    void assignToSection(int enrollmentID, int classID) throws ExceptionHandler;

    void removeAssign(int enrollmentID) throws ExceptionHandler;

    boolean isStudentEnrolled(int schoolYearID, int studentID) throws ExceptionHandler;

    boolean isAssignedToSection(int schoolYearID, int enrollmentID) throws ExceptionHandler;

    Enrollment getEnrollmentByYearLevel(int studentID, int yearLevel) throws ExceptionHandler;

}
