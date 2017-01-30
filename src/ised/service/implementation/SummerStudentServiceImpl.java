/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.service.implementation;

import ised.DAO.implementation.SummerStudentDAOImpl;
import ised.DAO.interfaces.SummerStudentDAO;
import ised.model.Enrollment;
import ised.model.SummerStudent;
import ised.service.interfaces.SummerStudentService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class SummerStudentServiceImpl implements SummerStudentService {

    private SummerStudentDAO dao;

    public SummerStudentServiceImpl(){
        dao = new SummerStudentDAOImpl();
    }

    public List<Enrollment> getSummerStudentsList(int schoolYearID) throws ExceptionHandler{
        return dao.getSummerStudentsList(schoolYearID);
    }

    public List<Enrollment> getSummerStudentsList(int schoolYearID, int classID) throws ExceptionHandler{
        return dao.getSummerStudentsList(schoolYearID, classID);
    }

    public List<SummerStudent> getSubjectList(int enrollmentID) throws ExceptionHandler{
        return dao.getSubjectList(enrollmentID);
    }

    public void enterGrade(int studentSubjectID, double grade) throws ExceptionHandler{
        dao.enterGrade(studentSubjectID, grade);
    }

    public void addSummerStudent(int studentSubjectID) throws ExceptionHandler{
        dao.addSummerStudent(studentSubjectID);
    }

    public void deleteSummerStudent(int summerStudentID) throws ExceptionHandler{
        dao.deleteSummerStudent(summerStudentID);
    }

    public boolean isGradesComplete(int enrollmentID) throws ExceptionHandler{
        return dao.isGradesComplete(enrollmentID);
    }
}
