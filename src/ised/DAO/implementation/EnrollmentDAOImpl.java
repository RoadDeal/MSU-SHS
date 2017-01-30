/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.EnrollmentDAO;
import ised.model.Admission;
import ised.model.Enrollment;
import ised.model.Student;
import ised.model.Class;
import ised.model.SchoolYear;
import ised.service.implementation.AdmissionServiceImpl;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.SchoolYearServiceImpl;
import ised.service.implementation.StudentServiceImpl;
import ised.service.interfaces.AdmissionService;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.SchoolYearService;
import ised.service.interfaces.StudentService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
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
public class EnrollmentDAOImpl implements EnrollmentDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<Student> getEnrolledStudentsList(int schoolYearID) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND classID IS NOT NULL"
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

    public List<Student> getEnrolledStudentsList(int schoolYearID, String search, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        if (yearLevel == 0) {
            sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                    + " WHERE schoolYearEnrolledID = " + schoolYearID
                    + " AND classID IS NOT NULL"
                    + " AND (studentID LIKE '%" + search + "%' OR lastName LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%')"
                    + " ORDER by lastName, firstName, middleName";
        } else {
            sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                    + " WHERE schoolYearEnrolledID = " + schoolYearID
                    + " AND yearLevelAdmitted = " + yearLevel
                    + " AND classID IS NOT NULL"
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

    public List<Student> getEnrolledStudentsList(int schoolYearID, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND yearLevelAdmitted = " + yearLevel
                + " AND classID IS NOT NULL"
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

    public List<Student> getNotAdmittedStudents(int schoolYearID) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND isAdmitted = '0'"
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

    public List<Student> getNotAdmittedStudents(int schoolYearID, String search, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND isAdmitted = '0'"
                + " AND classID IS NOT NULL"
                + " AND yearLevelAdmitted = " + yearLevel
                + " AND (studentID LIKE '%" + search + "%' OR lastName LIKE '%" + search + "%' OR firstName LIKE '%" + search + "%')"
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

    public List<Student> getNotAdmittedStudents(int schoolYearID, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND isAdmitted = '0'"
                + " AND classID IS NOT NULL"
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

    public List<Student> getStudentsNotAssignedToSection(int schoolYearID, int yearLevel) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND yearLevelAdmitted = " + yearLevel
                + " AND classID is NULL"
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

    public List<Student> getStudentsListBySection(int schoolYearID, int classID) throws ExceptionHandler {

        List<Student> list = new ArrayList<Student>();
        Student student = null;
        StudentService studentService = new StudentServiceImpl();

        sql = "SELECT studentID from enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE schoolYearEnrolledID = " + schoolYearID
                + " AND classID = " + classID
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

    public Enrollment getEnrollment(int studentID, int schoolYearID) throws ExceptionHandler {
        sql = "SELECT * FROM enrollment NATURAL JOIN admission WHERE studentID = ? AND schoolYearEnrolledID = ? ";
        Admission admission = null;
        Enrollment enrollment = null;
        SchoolYear schoolYear = null;
        Class section = null;
        AdmissionService admissionService = new AdmissionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        ClassService classService = new ClassServiceImpl();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, studentID);
            pStatement.setInt(2, schoolYearID);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                admission = admissionService.getAdmission(resultSet.getInt("studentID"), resultSet.getInt("schoolYearAdmittedID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearEnrolledID"));
                section = classService.getClass(resultSet.getInt("classID"));
                enrollment = new Enrollment(resultSet.getInt("enrollmentID"), admission, schoolYear, resultSet.getInt("isAdmitted"),
                        section, resultSet.getDouble("average"));
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return enrollment;
    }

    public Enrollment getEnrollment(int enrollmentID) throws ExceptionHandler {
        sql = "SELECT * FROM enrollment NATURAL JOIN admission WHERE enrollmentID = " + enrollmentID;
        Admission admission = null;
        Enrollment enrollment = null;
        SchoolYear schoolYear = null;
        Class section = null;
        AdmissionService admissionService = new AdmissionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        ClassService classService = new ClassServiceImpl();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                admission = admissionService.getAdmission(resultSet.getInt("studentID"), resultSet.getInt("schoolYearAdmittedID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearEnrolledID"));
                section = classService.getClass(resultSet.getInt("classID"));
                enrollment = new Enrollment(resultSet.getInt("enrollmentID"), admission, schoolYear, resultSet.getInt("isAdmitted"),
                        section, resultSet.getDouble("average"));
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return enrollment;
    }

    public void enrollStudent(int schoolYearID, int admissionID) throws ExceptionHandler {
        sql = "INSERT INTO enrollment(admissionID,schoolYearEnrolledID) VALUES (?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, admissionID);
            pStatement.setInt(2, schoolYearID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }

    }

    public void cancelEnroll(int enrollmentID) throws ExceptionHandler {
        sql = "DELETE FROM enrollment WHERE enrollmentID = " + enrollmentID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }

    }

    public void assignToSection(int enrollmentID, int classID) throws ExceptionHandler {
        sql = "UPDATE enrollment SET classID = ? WHERE enrollmentID = ?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, classID);
            pStatement.setInt(2, enrollmentID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void removeAssign(int enrollmentID) throws ExceptionHandler {
        sql = "UPDATE enrollment SET classID = NULL WHERE enrollmentID = ?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, enrollmentID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public boolean isStudentEnrolled(int schoolYearID, int studentID) throws ExceptionHandler {
        try {
            sql = "SELECT * FROM enrollment NATURAL JOIN admission WHERE studentID = ? AND schoolYearEnrolledID = ? ";
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, studentID);
            pStatement.setInt(2, schoolYearID);
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

    public boolean isAssignedToSection(int schoolYearID, int enrollmentID) throws ExceptionHandler {
        try {
            sql = "SELECT * FROM enrollment WHERE enrollmentID = ? AND schoolYearEnrolledID = ? AND classID IS NOT NULL";
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            pStatement.setInt(1, enrollmentID);
            pStatement.setInt(2, schoolYearID);
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

    public Enrollment getEnrollmentByYearLevel(int studentID, int yearLevel) throws ExceptionHandler {
        sql = "SELECT * FROM enrollment NATURAL JOIN admission WHERE studentID = " + studentID
                + " AND yearLevelAdmitted = " + yearLevel
                + " ORDER by schoolYearEnrolledID DESC LIMIT 1";

        Admission admission = null;
        Enrollment enrollment = null;
        SchoolYear schoolYear = null;
        Class section = null;
        AdmissionService admissionService = new AdmissionServiceImpl();
        SchoolYearService schoolYearService = new SchoolYearServiceImpl();
        ClassService classService = new ClassServiceImpl();
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                admission = admissionService.getAdmission(resultSet.getInt("studentID"), resultSet.getInt("schoolYearAdmittedID"));
                schoolYear = schoolYearService.getSchoolYear(resultSet.getInt("schoolYearEnrolledID"));
                section = classService.getClass(resultSet.getInt("classID"));
                enrollment = new Enrollment(resultSet.getInt("enrollmentID"), admission, schoolYear, resultSet.getInt("isAdmitted"),
                        section, resultSet.getDouble("average"));
            }
            return enrollment;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }
}
