/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.StudentDAOImpl;
import ised.DAO.interfaces.StudentDAO;
import ised.model.Guardian;
import ised.model.SchoolYear;
import ised.model.Student;
import ised.service.interfaces.StudentService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class StudentServiceImpl implements StudentService {

    private StudentDAO dao;

    public StudentServiceImpl() {
        dao = new StudentDAOImpl();
    }

    public Student getStudent(int studentID) throws ExceptionHandler {
        return dao.getStudent(studentID);
    }

    public boolean isSafeToDelete(int studentID) throws ExceptionHandler {
        return dao.isSafeToDelete(studentID);
    }

    public void deleteStudent(int studentID) throws ExceptionHandler {
        dao.deleteStudent(studentID);
    }

    public Student addStudent(Student student) throws ExceptionHandler {
        return dao.addStudent(student);
    }

    public void editStudent(Student student) throws ExceptionHandler {
        dao.editStudent(student);
    }

    public boolean checkStudent(Student student) throws ExceptionHandler {
        return dao.checkStudent(student);
    }

    public List<SchoolYear> getSchoolYearsAttended(int studentID) throws ExceptionHandler {
        return dao.getSchoolYearsAttended(studentID);
    }

    public List<Guardian> getGuardianList() throws ExceptionHandler {
        return dao.getGuardianList();
    }

    public List<Guardian> getGuardianList(String search) throws ExceptionHandler {
        return dao.getGuardianList(search);
    }
}
