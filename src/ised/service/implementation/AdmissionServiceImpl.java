/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.AdmissionDAOImpl;
import ised.DAO.interfaces.AdmissionDAO;
import ised.model.Admission;
import ised.model.Student;
import ised.service.interfaces.AdmissionService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class AdmissionServiceImpl implements AdmissionService {

    private AdmissionDAO dao;

    public AdmissionServiceImpl() {
        dao = new AdmissionDAOImpl();
    }

    public Admission getAdmission(int admissionID, int schoolYearID) throws ExceptionHandler {
        return dao.getAdmission(admissionID, schoolYearID);
    }

    public List<Student> getAdmittedStudentsList(int schoolYearID) throws ExceptionHandler {
        return dao.getAdmittedStudentsList(schoolYearID);
    }

    public List<Student> getAdmittedStudentsList(int schoolYearID, String search, int yearLevel) throws ExceptionHandler {
        return dao.getAdmittedStudentsList(schoolYearID, search, yearLevel);
    }

    public List<Student> getAdmittedStudentsList(int schoolYearID, int yearLevel) throws ExceptionHandler {
        return dao.getAdmittedStudentsList(schoolYearID, yearLevel);
    }

    public void admitStudent(int schoolYearID, int studentID, int yearLevelAdmitted, int enrollmentID) throws ExceptionHandler {
        dao.admitStudent(schoolYearID, studentID, yearLevelAdmitted, enrollmentID);
    }

    public void cancelAdmit(int schoolYearID, int studentID, int enrollmentID) throws ExceptionHandler {
        dao.cancelAdmit(schoolYearID, studentID, enrollmentID);
    }

    public boolean isStudentEnrolled(int schoolYearID, int studentID) throws ExceptionHandler{
        return dao.isStudentEnrolled(schoolYearID, studentID);
    }

    public boolean isStudentAdmitted(int schoolYearID, int studentID) throws ExceptionHandler{
        return dao.isStudentAdmitted(schoolYearID, studentID);
    }

    public boolean hasCompleteGrade(int enrollmentID) throws ExceptionHandler{
        return dao.hasCompleteGrade(enrollmentID);
    }

    public boolean hasFailingGrade(int enrollmentID) throws ExceptionHandler{
        return dao.hasFailingGrade(enrollmentID);
    }

    public boolean checkSummerGrades(int enrollmentID) throws ExceptionHandler{
        return dao.checkSummerGrades(enrollmentID);
    }
}
