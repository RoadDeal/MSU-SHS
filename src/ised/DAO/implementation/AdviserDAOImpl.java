/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.DAO.implementation;

import ised.DAO.interfaces.AdviserDAO;
import ised.model.Adviser;
import ised.DAO.interfaces.DBManager;
import ised.model.Class;
import ised.model.Employee;
import ised.service.implementation.ClassServiceImpl;
import ised.service.implementation.EmployeeServiceImpl;
import ised.service.interfaces.ClassService;
import ised.service.interfaces.EmployeeService;
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
public class AdviserDAOImpl implements AdviserDAO {

    private DBManager connectionDB;
    private Connection connection;
    private PreparedStatement pStatement;
    private CallableStatement cStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public Adviser getAdvisse(int employeeID, int schoolYearID) throws ExceptionHandler {
        sql = "SELECT adviser.* FROM adviser NATURAL JOIN class"
                + " WHERE employeeID = " + employeeID
                + " AND schoolYearID = " + schoolYearID;

        ClassService classService = new ClassServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        Class section = null;
        Employee employee = null;
        Adviser adviser = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                section = classService.getClass(resultSet.getInt("classID"));
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                adviser = new Adviser(resultSet.getInt("adviserID"),section,employee);
            }
            return adviser;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public Adviser getAdviser(int classID) throws ExceptionHandler {
        sql = "SELECT adviser.* FROM adviser NATURAL JOIN class"
                + " WHERE classID = " + classID;

        ClassService classService = new ClassServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        Class section = null;
        Employee employee = null;
        Adviser adviser = null;
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                section = classService.getClass(resultSet.getInt("classID"));
                employee = employeeService.getEmployee(resultSet.getInt("employeeID"));
                adviser = new Adviser(resultSet.getInt("adviserID"),section,employee);
            }
            return adviser;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        } finally {
            DataDispatcher.dispatch(resultSet, pStatement, connection);
        }
    }

    public void addAdvisee(int classID, int employeeID) throws ExceptionHandler {
        sql = "INSERT INTO adviser(classID, employeeID) VALUES(?,?)";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, classID);
            pStatement.setInt(2, employeeID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void editAdvisee(int classID, int adviserID) throws ExceptionHandler {
        sql = "UPDATE adviser SET classID = ? WHERE adviserID = ?";
        try {
            connectionDB = DBManagerImpl.getInstance();
            connection = (Connection) connectionDB.getConnection();
            pStatement = connection.prepareCall(sql);
            pStatement.setInt(1, classID);
            pStatement.setInt(2, adviserID);
            pStatement.execute();
        } catch (SQLException ex) {
            throw new ExceptionHandler(ex);
        } finally {
            DataDispatcher.dispatch(resultSet, statement, connection);
        }
    }

    public void deleteAdvisee(int adviserID) throws ExceptionHandler {
        sql = "DELETE from adviser WHERE adviserID = " + adviserID;
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

}
