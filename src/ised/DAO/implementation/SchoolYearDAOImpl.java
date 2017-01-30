/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.implementation;

import ised.DAO.interfaces.DBManager;
import ised.DAO.interfaces.SchoolYearDAO;
import ised.model.SchoolYear;
import ised.tools.DataDispatcher;
import ised.tools.ExceptionHandler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class SchoolYearDAOImpl implements SchoolYearDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public SchoolYear getSchoolYear(int schoolYearID) throws ExceptionHandler {
        sql = "SELECT * FROM school_year WHERE  schoolYearID = " + schoolYearID;
        SchoolYear schoolYear = null;

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                schoolYear = new SchoolYear(resultSet.getInt("schoolYearID"), resultSet.getInt("yearFrom"), resultSet.getInt("yearTo"),
                        resultSet.getDate("enrollmentBegin"), resultSet.getDate("enrollmentEnd"), resultSet.getDate("startOfClasses"),
                        resultSet.getString("schoolYearStatus"));
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
        return schoolYear;
    }

    public SchoolYear getCurrentSchoolYear() throws ExceptionHandler {
        sql = "SELECT * FROM school_year WHERE schoolYearStatus = 'Active'";
        SchoolYear schoolYear = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                schoolYear = new SchoolYear(resultSet.getInt("schoolYearID"), resultSet.getInt("yearFrom"), resultSet.getInt("yearTo"),
                        resultSet.getDate("enrollmentBegin"), resultSet.getDate("enrollmentEnd"), resultSet.getDate("startOfClasses"),
                        resultSet.getString("schoolYearStatus"));
            }
            return schoolYear;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public SchoolYear getPreviousSchoolYear(int schoolYearID) throws ExceptionHandler {
        sql = "SELECT * FROM school_year WHERE schoolYearID = "
                + " (SELECT MAX(schoolYearID) FROM school_year WHERE schoolYearID < " + schoolYearID + ")";
        SchoolYear schoolYear = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                schoolYear = new SchoolYear(resultSet.getInt("schoolYearID"), resultSet.getInt("yearFrom"), resultSet.getInt("yearTo"),
                        resultSet.getDate("enrollmentBegin"), resultSet.getDate("enrollmentEnd"), resultSet.getDate("startOfClasses"),
                        resultSet.getString("schoolYearStatus"));
            }
            return schoolYear;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public SchoolYear getNextSchoolYear(int schoolYearID) throws ExceptionHandler {
        sql = "SELECT * FROM school_year WHERE schoolYearID = "
                + " (SELECT MIN(schoolYearID) FROM school_year WHERE schoolYearID > " + schoolYearID + ")";
        SchoolYear schoolYear = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                schoolYear = new SchoolYear(resultSet.getInt("schoolYearID"), resultSet.getInt("yearFrom"), resultSet.getInt("yearTo"),
                        resultSet.getDate("enrollmentBegin"), resultSet.getDate("enrollmentEnd"), resultSet.getDate("startOfClasses"),
                        resultSet.getString("schoolYearStatus"));
            }
            return schoolYear;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public List<SchoolYear> getSchoolYearList() throws ExceptionHandler {
        sql = "SELECT * FROM school_year ORDER BY yearFrom ASC";
        List<SchoolYear> list = new ArrayList<SchoolYear>();

        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                SchoolYear schoolYear = new SchoolYear(resultSet.getInt("schoolYearID"),resultSet.getInt("yearFrom"), resultSet.getInt("yearTo"),
                        resultSet.getDate("enrollmentBegin"), resultSet.getDate("enrollmentEnd"),resultSet.getDate("startOfClasses"),
                        resultSet.getString("schoolYearStatus"));

                list.add(schoolYear);
            }
            return list;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public Calendar getServerCurrentDate() throws ExceptionHandler {
        sql = "SELECT NOW()";
        Calendar calendarNow = Calendar.getInstance();
        try {
            if (connectionDB == null) {
                connectionDB = DBManagerImpl.getInstance();
            }
            connection = connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Date currentDate = resultSet.getDate(1);
                Time currentTime = resultSet.getTime(1);
                calendarNow.set(currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDate(),
                        currentTime.getHours(), currentTime.getMinutes(), currentTime.getSeconds());
            }
            return calendarNow;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void addSchoolYear(SchoolYear schoolYear) throws ExceptionHandler {
        sql = "{CALL addSchoolYear (?,?,?,?,?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, schoolYear.getYearFrom());
            pStatement.setInt(2, schoolYear.getYearTo());
            pStatement.setDate(3, (java.sql.Date) schoolYear.getEnrollmentBegin());
            pStatement.setDate(4, (java.sql.Date) schoolYear.getEnrollmentEnd());
            pStatement.setDate(5, (java.sql.Date) schoolYear.getStartOfClasses());
            pStatement.setString(6, schoolYear.getSchoolYearStatus());
            pStatement.execute();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void editSchoolYear(SchoolYear schoolYear) throws ExceptionHandler {
        sql = "{CALL editSchoolYear (?,?,?,?,?)}";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setDate(1, (java.sql.Date) schoolYear.getEnrollmentBegin());
            pStatement.setDate(2, (java.sql.Date) schoolYear.getEnrollmentEnd());
            pStatement.setDate(3, (java.sql.Date)schoolYear.getStartOfClasses());
            pStatement.setString(4, schoolYear.getSchoolYearStatus());
            pStatement.setInt(5, schoolYear.getSchoolYearID());
            pStatement.execute();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } catch (Exception e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }
}
