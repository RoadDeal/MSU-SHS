/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.SubjectDAO;
import ised.model.Subject;
import ised.DAO.interfaces.DBManager;
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
public class SubjectDAOImpl implements SubjectDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public List<Subject> getSubjectList(int curriculumID, int yearLevel) throws ExceptionHandler {
        List<Subject> list = new ArrayList<Subject>();
        sql = "SELECT subject.* FROM curriculum_subjects NATURAL JOIN subject"
                + " WHERE curriculumID = " + curriculumID
                + " AND yearLevel = " + yearLevel
                + " ORDER by subjectCode";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getInt("subjectID"), resultSet.getString("subjectCode"), resultSet.getString("description"),
                        resultSet.getDouble("units"), resultSet.getInt("yearLevel"));
                list.add(subject);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

    }

    public List<Subject> getSubjectList(int curriculumID) throws ExceptionHandler {
        List<Subject> list = new ArrayList<Subject>();
        sql = "SELECT subject.* FROM curriculum_subjects NATURAL JOIN subject"
                + " WHERE curriculumID = " + curriculumID
                + " ORDER by subjectCode";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getInt("subjectID"), resultSet.getString("subjectCode"), resultSet.getString("description"),
                        resultSet.getDouble("units"), resultSet.getInt("yearLevel"));
                list.add(subject);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }

    }

    public List<Subject> getSubjectFromPrevCurrList(int curriculumID, int yearLevel) throws ExceptionHandler {
        List<Subject> list = new ArrayList<Subject>();

        sql = "SELECT subject.* FROM curriculum_subjects NATURAL JOIN subject"
                + " WHERE curriculumID < " + curriculumID
                + " AND yearLevel = " + yearLevel
                + " ORDER by subjectCode";

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Subject subject = new Subject(resultSet.getInt("subjectID"), resultSet.getString("subjectCode"), resultSet.getString("description"),
                        resultSet.getInt("units"), resultSet.getInt("yearLevel"));
                list.add(subject);
            }
            return list;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public Subject getSubject(int subjectID) throws ExceptionHandler {
        Subject subject = null;
        sql = "SELECT * FROM subject WHERE subjectID = " + subjectID;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                subject = new Subject(resultSet.getInt("subjectID"), resultSet.getString("subjectCode"), resultSet.getString("description"),
                        resultSet.getInt("units"), resultSet.getInt("yearLevel"));
            }
            return subject;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public Subject getSubject(String subjectCode) throws ExceptionHandler {
        Subject subject = null;
        sql = "SELECT * FROM subject WHERE subjectCode = '" + subjectCode + "'";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                subject = new Subject(resultSet.getInt("subjectID"), resultSet.getString("subjectCode"), resultSet.getString("description"),
                        resultSet.getDouble("units"), resultSet.getInt("yearLevel"));
            }
            return subject;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addSubject(Subject subject) throws ExceptionHandler {
        sql = " INSERT INTO subject(subjectCode, description, units, yearLevel) VALUES(?,?,?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setString(1, subject.getSubjectCode());
            pStatement.setString(2, subject.getDescription());
            pStatement.setDouble(3, subject.getUnits());
            pStatement.setInt(4, subject.getYearLevel());
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void editSubject(Subject subject) throws ExceptionHandler {
        sql = "UPDATE subject SET subjectCode=?, description=?, units=?, yearLevel=? WHERE subjectID=?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setString(1, subject.getSubjectCode());
            pStatement.setString(2, subject.getDescription());
            pStatement.setDouble(3, subject.getUnits());
            pStatement.setInt(4, subject.getYearLevel());
            pStatement.setInt(5, subject.getSubjectID());
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteSubject(int subjectID) throws ExceptionHandler {
        sql = "DELETE from subject WHERE subjectID = " + subjectID;
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

    public boolean checkSubject(String subjectCode) throws ExceptionHandler {
        sql = "SELECT * from subject WHERE subjectCode = '" + subjectCode + "'";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public boolean isSafeToDelete(int subjectID, int curriculumID) throws ExceptionHandler {
        sql = "SELECT * from curriculum_subjects WHERE curriculumID <> ? AND subjectID = ?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, curriculumID);
            pStatement.setInt(2, subjectID);
            pStatement.execute();
            resultSet = pStatement.getResultSet();
            if (resultSet.next()) {
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
