/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.SummerStudentDAO;
import ised.DAO.interfaces.DBManager;
import ised.model.Enrollment;
import ised.model.StudentSubject;
import ised.model.SummerStudent;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.StudentSubjectServiceImpl;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.StudentSubjectService;
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
public class SummerStudentDAOImpl implements SummerStudentDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<Enrollment> getSummerStudentsList(int schoolYearID) throws ExceptionHandler {

        List<Enrollment> list = new ArrayList<Enrollment>();
        Enrollment enrollment = null;
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        sql = "SELECT DISTINCT(enrollmentID) FROM summer_student NATURAL JOIN student_subject NATURAL JOIN enrollment"
                + " WHERE schoolYearEnrolledID = " + schoolYearID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                list.add(enrollment);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<Enrollment> getSummerStudentsList(int schoolYearID, int classID) throws ExceptionHandler {

        List<Enrollment> list = new ArrayList<Enrollment>();
        Enrollment enrollment = null;
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        sql = "SELECT DISTINCT(enrollmentID) FROM summer_student NATURAL JOIN student_subject NATURAL JOIN enrollment"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND classID = " + classID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                list.add(enrollment);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<SummerStudent> getSubjectList(int enrollmentID) throws ExceptionHandler {
        List<SummerStudent> list = new ArrayList<SummerStudent>();
        StudentSubjectService studentSubjectService = new StudentSubjectServiceImpl();
        sql = "SELECT * FROM summer_student NATURAL JOIN student_subject WHERE enrollmentID = " + enrollmentID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                StudentSubject studentSubject = studentSubjectService.getStudentSubject(resultSet.getInt("studentSubjectID"));
                SummerStudent summerStudent = new SummerStudent(resultSet.getInt("summerStudentID"), studentSubject, resultSet.getDouble("completeGrade"));
                list.add(summerStudent);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public void enterGrade(int studentSubjectID, double grade) throws ExceptionHandler {

        sql = "UPDATE summer_student SET completeGrade = ? WHERE studentSubjectID = ?";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setDouble(1, grade);
            pStatement.setInt(2, studentSubjectID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteSummerStudent(int summerStudentID) throws ExceptionHandler {
        sql = "DELETE FROM summer_student WHERE summerStudentID = " + summerStudentID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            statement.execute(sql);

        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addSummerStudent(int studentSubjectID) throws ExceptionHandler {

        sql = "INSERT INTO summer_student (studentSubjectID) VALUES(?)";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, studentSubjectID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean isGradesComplete(int enrollmentID) throws ExceptionHandler {
         sql = "SELECT * from summer_student NATURAL JOIN student_subject WHERE enrollmentID = " + enrollmentID
                + " AND completeGrade IS NULL";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return false;
            }
            return true;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }
}
