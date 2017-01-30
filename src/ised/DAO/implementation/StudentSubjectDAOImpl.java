/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.StudentSubjectDAO;
import ised.DAO.interfaces.SubjectDAO;
import ised.model.Subject;
import ised.DAO.interfaces.DBManager;
import ised.model.Enrollment;
import ised.model.StudentSubject;
import ised.model.SubjectGrade;
import ised.service.implementation.EnrollmentServiceImpl;
import ised.service.implementation.SubjectServiceImpl;
import ised.service.interfaces.EnrollmentService;
import ised.service.interfaces.SubjectService;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class StudentSubjectDAOImpl implements StudentSubjectDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public StudentSubject getStudentSubject(int studentSubjectID) throws ExceptionHandler {
        Enrollment enrollment = null;
        Subject subject = null;
        StudentSubject studentSubject = null;
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();
        SubjectService subjectService = new SubjectServiceImpl();
        sql = "SELECT * from student_subject WHERE studentSubjectID = " + studentSubjectID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                studentSubject = new StudentSubject(resultSet.getInt("studentSubjectID"), enrollment, subject, resultSet.getDouble("finalGrade"));
            }
            return studentSubject;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addStudentSubject(int enrollmentID, int subjectID) throws ExceptionHandler {
        sql = "INSERT INTO student_subject (enrollmentID, subjectID) VALUES (?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, enrollmentID);
            pStatement.setInt(2, subjectID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteStudentSubjects(int enrollmentID) throws ExceptionHandler {
        sql = "DELETE FROM student_subject WHERE enrollmentID = " + enrollmentID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public List<SubjectGrade> getStudentGradeList(int classID, int subjectID, int grading) throws ExceptionHandler {
        List<SubjectGrade> list = new ArrayList<SubjectGrade>();
        Subject subject = null;
        Enrollment enrollment = null;
        SubjectService subjectService = new SubjectServiceImpl();
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        sql = "SELECT * from subjects_grade NATURAL JOIN student_subject NATURAL JOIN enrollment NATURAL JOIN admission NATURAL JOIN student"
                + " WHERE subjectID = " + subjectID
                + " AND classID = " + classID
                + " AND grading = " + grading
                + " ORDER by lastName, firstName, middleName";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                StudentSubject studentSubject = new StudentSubject(resultSet.getInt("studentSubjectID"), enrollment, subject, resultSet.getDouble("finalGrade"));
                Date dateSubmitted = resultSet.getDate("dateSubmitted");
                Time timeSubmitted = resultSet.getTime("dateSubmitted");
                Calendar gradeSubmitted = Calendar.getInstance();
                if (dateSubmitted != null && timeSubmitted != null) {
                    gradeSubmitted.set(dateSubmitted.getYear() + 1900, dateSubmitted.getMonth(), dateSubmitted.getDate(),
                            timeSubmitted.getHours(), timeSubmitted.getMinutes(), timeSubmitted.getSeconds());
                }
                SubjectGrade subjectGrade = new SubjectGrade(resultSet.getInt("subjectGradeID"), studentSubject,
                        resultSet.getInt("grading"), resultSet.getDouble("grade"), gradeSubmitted);
                list.add(subjectGrade);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public List<StudentSubject> getFailedStudentList(int schoolYearID) throws ExceptionHandler {
        List<StudentSubject> list = new ArrayList<StudentSubject>();
        Subject subject = null;
        Enrollment enrollment = null;
        SubjectService subjectService = new SubjectServiceImpl();
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        sql = "SELECT * from student_subject s"
                + " WHERE (SELECT SUM(sub.units) FROM student_subject ss NATURAL JOIN subject sub"
                + " WHERE finalGrade > 3 AND s.studentSubjectID = ss.studentSubjectID) <=2";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                StudentSubject studentSubject = new StudentSubject(resultSet.getInt("studentSubjectID"), enrollment, subject, resultSet.getDouble("finalGrade"));
                list.add(studentSubject);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }

    public double getFailedSubjectUnits(int enrollmentID) throws ExceptionHandler {
        sql = "SELECT units FROM student_subject NATURAL JOIN subject WHERE finalGrade > 3 AND enrollmentID = " + enrollmentID;
        double totalUnits = 0;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                totalUnits += resultSet.getDouble("units");
            }
            return totalUnits;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void enterStudentGrade(SubjectGrade subjectGrade) throws ExceptionHandler {
        sql = "UPDATE subjects_grade SET grade = ?, dateSubmitted=NOW() WHERE studentSubjectID = ? AND grading = ?";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setDouble(1, subjectGrade.getGradingGrade());
            pStatement.setInt(2, subjectGrade.getStudentSubject().getStudentSubjectID());
            pStatement.setInt(3, subjectGrade.getGrading());
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void lockGrades(int studentSubjectID, int grading, int flag) throws ExceptionHandler {
        sql = "UPDATE subjects_grade SET isLocked = ? WHERE studentSubjectID = ? AND grading = ?";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = (PreparedStatement) connection.prepareCall(sql);
            pStatement.setInt(1, flag);
            pStatement.setInt(2, studentSubjectID);
            pStatement.setInt(3, grading);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public List<StudentSubject> getStudentSubjectList(int enrollmentID) throws ExceptionHandler {
        List<StudentSubject> list = new ArrayList<StudentSubject>();
        Subject subject = null;
        Enrollment enrollment = null;
        SubjectService subjectService = new SubjectServiceImpl();
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        sql = "SELECT * from student_subject WHERE enrollmentID = " + enrollmentID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                StudentSubject studentSubject = new StudentSubject(resultSet.getInt("studentSubjectID"), enrollment, subject, resultSet.getDouble("finalGrade"));
                list.add(studentSubject);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;

    }

    public List<SubjectGrade> getSubjectGradeList(int studentSubjectID) throws ExceptionHandler {
        List<SubjectGrade> list = new ArrayList<SubjectGrade>();
        Subject subject = null;
        Enrollment enrollment = null;
        SubjectService subjectService = new SubjectServiceImpl();
        EnrollmentService enrollmentService = new EnrollmentServiceImpl();

        sql = "SELECT * from subjects_grade NATURAL JOIN student_subject WHERE studentSubjectID = " + studentSubjectID;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                enrollment = enrollmentService.getEnrollment(resultSet.getInt("enrollmentID"));
                subject = subjectService.getSubject(resultSet.getInt("subjectID"));
                StudentSubject studentSubject = new StudentSubject(resultSet.getInt("studentSubjectID"), enrollment, subject, resultSet.getDouble("finalGrade"));
                Date dateSubmitted = resultSet.getDate("dateSubmitted");
                Time timeSubmitted = resultSet.getTime("dateSubmitted");
                Calendar gradeSubmitted = Calendar.getInstance();
                if (dateSubmitted != null && timeSubmitted != null) {
                    gradeSubmitted.set(dateSubmitted.getYear() + 1900, dateSubmitted.getMonth(), dateSubmitted.getDate(),
                            timeSubmitted.getHours(), timeSubmitted.getMinutes(), timeSubmitted.getSeconds());
                }
                SubjectGrade subjectGrade = new SubjectGrade(resultSet.getInt("subjectGradeID"), studentSubject,
                        resultSet.getInt("grading"), resultSet.getDouble("grade"), gradeSubmitted);
                list.add(subjectGrade);
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
        return list;
    }
}
