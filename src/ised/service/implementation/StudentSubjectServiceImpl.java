/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.service.implementation;

import ised.DAO.implementation.StudentSubjectDAOImpl;
import ised.DAO.interfaces.StudentSubjectDAO;
import ised.model.StudentSubject;
import ised.model.SubjectGrade;
import ised.service.interfaces.StudentSubjectService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class StudentSubjectServiceImpl implements StudentSubjectService {

    StudentSubjectDAO dao;

    public StudentSubjectServiceImpl(){
        dao = new StudentSubjectDAOImpl();
    }

    public StudentSubject getStudentSubject(int studentSubjectID) throws ExceptionHandler{
        return dao.getStudentSubject(studentSubjectID);
    }

    public void addStudentSubject(int enrollmentID, int subjectID) throws ExceptionHandler{
        dao.addStudentSubject(enrollmentID, subjectID);
    }

    public void deleteStudentSubjects(int enrollmnetID) throws ExceptionHandler{
        dao.deleteStudentSubjects(enrollmnetID);
    }

    public void enterStudentGrade(SubjectGrade subjectGrade) throws ExceptionHandler{
        dao.enterStudentGrade(subjectGrade);
    }

    public List<SubjectGrade> getStudentGradeList(int classID, int subjectID, int grading) throws ExceptionHandler{
        return dao.getStudentGradeList(classID, subjectID, grading);
    }

    public List<StudentSubject> getFailedStudentList(int schoolYearID) throws ExceptionHandler {
        return dao.getFailedStudentList(schoolYearID);
    }

    public void lockGrades(int studentSubjectID, int grading, int flag) throws ExceptionHandler {
        dao.lockGrades(studentSubjectID, grading, flag);
    }

    public double getFailedSubjectUnits(int enrollmentID) throws ExceptionHandler{
        return dao.getFailedSubjectUnits(enrollmentID);
    }

    public List<StudentSubject> getStudentSubjectList(int enrollmentID) throws ExceptionHandler{
        return dao.getStudentSubjectList(enrollmentID);
    }

     public List<SubjectGrade> getSubjectGradeList(int studentSubjectID) throws ExceptionHandler{
        return dao.getSubjectGradeList(studentSubjectID);
    }
}
