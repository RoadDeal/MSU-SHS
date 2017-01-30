/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import ised.model.Guardian;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface StudentDAO {

    Student getStudent(int studentID) throws ExceptionHandler;

    Student addStudent(Student student) throws ExceptionHandler;

    void editStudent(Student student) throws ExceptionHandler;

    boolean checkStudent(Student student) throws ExceptionHandler;

    boolean isSafeToDelete(int studentID) throws ExceptionHandler;

    void deleteStudent(int studentID) throws ExceptionHandler;

    List<SchoolYear> getSchoolYearsAttended(int studentID) throws ExceptionHandler;

    List<Guardian> getGuardianList() throws ExceptionHandler;

    List<Guardian> getGuardianList(String search) throws ExceptionHandler;
}
