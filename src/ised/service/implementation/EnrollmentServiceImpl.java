/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.EnrollmentDAOImpl;
import ised.DAO.interfaces.EnrollmentDAO;
import ised.model.Enrollment;
import ised.model.Student;
import ised.service.interfaces.EnrollmentService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class EnrollmentServiceImpl implements EnrollmentService {

    public EnrollmentDAO dao;

    public EnrollmentServiceImpl() {
        dao = new EnrollmentDAOImpl();
    }

    public List<Student> getEnrolledStudentsList(int schoolYearID) throws ExceptionHandler {
        return dao.getEnrolledStudentsList(schoolYearID);
    }

    public List<Student> getEnrolledStudentsList(int schoolYearID, String search, int yearLevel) throws ExceptionHandler {
        return dao.getEnrolledStudentsList(schoolYearID, search, yearLevel);
    }

    public List<Student> getEnrolledStudentsList(int schoolYearID, int yearLevel) throws ExceptionHandler {
        return dao.getEnrolledStudentsList(schoolYearID, yearLevel);
    }

    public List<Student> getNotAdmittedStudents(int schoolYearID) throws ExceptionHandler {
        return dao.getNotAdmittedStudents(schoolYearID);
    }

    public List<Student> getNotAdmittedStudents(int schoolYearID, String search, int yearLevel) throws ExceptionHandler {
        return dao.getNotAdmittedStudents(schoolYearID, search, yearLevel);
    }

    public List<Student> getNotAdmittedStudents(int schoolYearID, int yearLevel) throws ExceptionHandler {
        return dao.getNotAdmittedStudents(schoolYearID, yearLevel);
    }

    public List<Student> getStudentsNotAssignedToSection(int schoolYearID, int yearLevel) throws ExceptionHandler {
        return dao.getStudentsNotAssignedToSection(schoolYearID, yearLevel);
    }

     public List<Student> getStudentsListBySection(int schoolYearID, int classID) throws ExceptionHandler {
         return dao.getStudentsListBySection(schoolYearID, classID);
     }

    public Enrollment getEnrollment(int studentID, int schoolYearID) throws ExceptionHandler {
        return dao.getEnrollment(studentID, schoolYearID);
    }

    public Enrollment getEnrollment(int enrollmentID) throws ExceptionHandler {
        return dao.getEnrollment(enrollmentID);
    }

    public void enrollStudent(int schoolYearID, int admissionID) throws ExceptionHandler {
        dao.enrollStudent(schoolYearID, admissionID);
    }

    public void cancelEnroll(int enrollmentID) throws ExceptionHandler {
        dao.cancelEnroll(enrollmentID);
    }

    public void assignToSection(int enrollmentID, int classID) throws ExceptionHandler {
        dao.assignToSection(enrollmentID, classID);
    }

    public void removeAssign(int enrollmentID) throws ExceptionHandler {
        dao.removeAssign(enrollmentID);
    }

    public boolean isStudentEnrolled(int schoolYearID, int studentID) throws ExceptionHandler {
        return dao.isStudentEnrolled(schoolYearID, studentID);
    }

    public boolean isAssignedToSection(int schoolYearID, int enrollmentID) throws ExceptionHandler{
        return dao.isAssignedToSection(schoolYearID, enrollmentID);
    }

    public Enrollment getEnrollmentByYearLevel(int studentID, int yearLevel) throws ExceptionHandler{
        return dao.getEnrollmentByYearLevel(studentID, yearLevel);
    }

}
