/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.DAO.interfaces;

import ised.model.Enrollment;
import ised.model.SummerStudent;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface SummerStudentDAO {

    List<Enrollment> getSummerStudentsList(int schoolYearID) throws ExceptionHandler;

    List<Enrollment> getSummerStudentsList(int schoolYearID, int sectionID) throws ExceptionHandler;

    List<SummerStudent> getSubjectList(int enrollmentID) throws ExceptionHandler;

    void enterGrade(int studentSubjectID, double grade) throws ExceptionHandler;

    void addSummerStudent(int studentSubjectID) throws ExceptionHandler;

    void deleteSummerStudent(int summerStudentID) throws ExceptionHandler;

    boolean isGradesComplete(int enrollmentID) throws ExceptionHandler;
}
