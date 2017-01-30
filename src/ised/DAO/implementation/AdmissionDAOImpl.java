/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.AdmissionDAO;
import ised.model.Admission;
import ised.model.Student;
import ised.model.SchoolYear;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.StudentServiceImpl;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.StudentService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class AdmissionDAOImpl implements AdmissionDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<Student> getAdmittedStudentsList(int schoolYearID) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from admission NATURAL JOIN student NATURAL JOIN guardian"
                + " WHERE schoolYearAdmittedID = " + schoolYearID
                + " ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                student = studentService.getStudent(resultSet.getInt("studentID"));
                list.add(student);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Student> getAdmittedStudentsList(int schoolYearID, String search, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        if (yearLevel > 0) {
            sql = "SELECT studentID from admission NATURAL JOIN student NATURAL JOIN guardian"
                    + " WHERE schoolYearAdmittedID = " + schoolYearID
                    + " AND yearLevelAdmitted = " + yearLevel
                    + " AND (studentID LIKE '%" + search + "%' OR lastName LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%')"
                    + " ORDER by lastName, firstName, middleName";
        } else {
            sql = "SELECT studentID from admission NATURAL JOIN student NATURAL JOIN guardian"
                    + " WHERE schoolYearAdmittedID = " + schoolYearID
                    + " AND (studentID LIKE '%" + search + "%' OR lastName LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%')"
                    + " ORDER by lastName, firstName, middleName";
        }

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                student = studentService.getStudent(resultSet.getInt("studentID"));
                list.add(student);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Student> getAdmittedStudentsList(int schoolYearID, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from admission NATURAL JOIN student NATURAL JOIN guardian"
                + " WHERE schoolYearAdmittedID = " + schoolYearID
                + " AND yearLevelAdmitted = " + yearLevel
                + " ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                student = studentService.getStudent(resultSet.getInt("studentID"));
                list.add(student);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public Admission getAdmission(int studentID, int schoolYearID) throws ExceptionHandler {
        sql = "SELECT * FROM admission WHERE studentID = " + studentID + " AND schoolYearAdmittedID = " + schoolYearID;
        Admission admission = null;
        Student student = null;
        SchoolYear schoolYear = null;
        StudentService studentService = new StudentServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                student = studentService.getStudent(resultSet.getInt("studentID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearAdmittedID"));
                admission = new Admission(resultSet.getInt("admissionID"), student,
                        resultSet.getInt("yearLevelAdmitted"), schoolYear, resultSet.getInt("isEnrolled"));
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return admission;
    }

    public void admitStudent(int schoolYearID, int yearLevelAdmitted, int studentID, int enrollmentID) throws ExceptionHandler {
        try {
            sql = "{CALL admitStudent(?,?,?,?)}";
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, studentID);
            cStatement.setInt(2, yearLevelAdmitted);
            cStatement.setInt(3, schoolYearID);
            cStatement.setInt(4, enrollmentID);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }

    }

    public void cancelAdmit(int schoolYearID, int studentID, int enrollmentID) throws ExceptionHandler {
        try {
            sql = "{CALL cancelAdmit(?,?,?)}";
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            cStatement = (CallableStatement) connection.prepareCall(sql);
            cStatement.setInt(1, studentID);
            cStatement.setInt(2, schoolYearID);
            cStatement.setInt(3, enrollmentID);
            cStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }

    }

    public boolean isStudentEnrolled(int schoolYearID, int studentID) throws ExceptionHandler {
        try {
            sql = "SELECT * FROM admission WHERE schoolYearAdmittedID = ? AND studentID = ? AND isEnrolled = 1";
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, schoolYearID);
            pStatement.setInt(2, studentID);
            pStatement.execute();
            resultSet = pStatement.getResultSet();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

     public boolean isStudentAdmitted(int schoolYearID, int studentID) throws ExceptionHandler {
        try {
            sql = "SELECT * FROM admission WHERE schoolYearAdmittedID = ? AND studentID = ?";
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, schoolYearID);
            pStatement.setInt(2, studentID);
            pStatement.execute();
            resultSet = pStatement.getResultSet();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public boolean hasCompleteGrade(int enrollmentID) throws ExceptionHandler{
        sql = "SELECT * FROM student_subject WHERE (finalGrade IS NULL OR finalGrade = 0) AND enrollmentID = " + enrollmentID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public boolean hasFailingGrade(int enrollmentID) throws ExceptionHandler{
        sql = "SELECT * FROM student_subject WHERE finalGrade > 3 AND enrollmentID = " + enrollmentID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public boolean checkSummerGrades(int enrollmentID) throws ExceptionHandler{
        sql = "SELECT * FROM summer_student NATURAL JOIN student_subject"
                + " WHERE enrollmentID = " + enrollmentID
                + " AND completeGrade = 0 OR completeGrade IS NULL OR completeGrade > 3";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

}
